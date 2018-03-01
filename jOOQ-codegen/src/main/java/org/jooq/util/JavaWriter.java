package org.jooq.util;

import static org.jooq.util.GenerationUtil.PLAIN_GENERIC_TYPE_PATTERN;
import static org.jooq.util.GenerationUtil.TYPE_REFERENCE_PATTERN;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jooq.tools.StringUtils;

/**
 * A wrapper for a {@link PrintWriter}
 * <p>
 * This wrapper adds Java printing features to the general
 * {@link GeneratorWriter}
 *
 * @author Lukas Eder
 */
public class JavaWriter extends GeneratorWriter<JavaWriter> {

    private static final String       SERIAL_STATEMENT = "__SERIAL_STATEMENT__";
    private static final String       IMPORT_STATEMENT = "__IMPORT_STATEMENT__";

    private final Pattern             fullyQualifiedTypes;
    private final boolean             javadoc;
    private final Set<String>         qualifiedTypes   = new TreeSet<String>();
    private final Map<String, String> unqualifiedTypes = new TreeMap<String, String>();
    private final String              className;
    private final boolean             isJava;
    private final boolean             isScala;

    public JavaWriter(File file, String fullyQualifiedTypes) {
        this(file, fullyQualifiedTypes, null);
    }

    public JavaWriter(File file, String fullyQualifiedTypes, String encoding) {
        this(file, fullyQualifiedTypes, encoding, true);
    }

    public JavaWriter(File file, String fullyQualifiedTypes, String encoding, boolean javadoc) {
        super(file, encoding);

        this.className = file.getName().replaceAll("\\.(java|scala)$", "");
        this.isJava = file.getName().endsWith(".java");
        this.isScala = file.getName().endsWith(".scala");
        this.fullyQualifiedTypes = fullyQualifiedTypes == null ? null : Pattern.compile(fullyQualifiedTypes);
        this.javadoc = javadoc;

        if (isJava)
            tabString("    ");
        else if (isScala)
            tabString("  ");
    }

    public JavaWriter print(Class<?> clazz) {
        printClass(clazz.getCanonicalName());
        return this;
    }

    public JavaWriter printClass(String clazz) {
        print(ref(clazz));
        return this;
    }

    public JavaWriter javadoc(String string, Object... args) {
        int t = tab();
        tab(t).println();

        if (javadoc) {

            // [#3450] [#4575] Must not print */ inside Javadoc
            String escaped = escapeJavadoc(string);
            Object[] escapedArgs = Arrays.copyOf(args, args.length);
            for (int i = 0; i < escapedArgs.length; i++)
                if (escapedArgs[i] instanceof String)
                    escapedArgs[i] = escapeJavadoc((String) escapedArgs[i]);

            tab(t).println("/**");
            tab(t).println(" * " + escaped, escapedArgs);
            tab(t).println(" */");
        }

        return this;
    }

    private String escapeJavadoc(String string) {

        // [#3450] [#4880] Must not print */ inside Javadoc
        return string.replace("/*", "/ *").replace("*/", "* /");
    }

    public JavaWriter header(String header, Object... args) {
        int t = tab();

        tab(t).println();
        tab(t).println("// -------------------------------------------------------------------------");
        tab(t).println("// " + header, args);
        tab(t).println("// -------------------------------------------------------------------------");

        return this;
    }

    public JavaWriter override() {
        println("@Override");
        return this;
    }

    public JavaWriter overrideIf(boolean override) {
        if (override) {
            println("@Override");
        }

        return this;
    }

    public JavaWriter overrideInherit() {
        final int t = tab();

        tab(t).javadoc("{@inheritDoc}");
        tab(t).override();

        return this;
    }

    public JavaWriter overrideInheritIf(boolean override) {
        final int t = tab();

        if (override) {
            tab(t).javadoc("{@inheritDoc}");
            tab(t).override();
        }
        else {
            tab(t).println();
        }

        return this;
    }

    public void printSerial() {
        if (isJava) {
            println();
            println("\tprivate static final long serialVersionUID = %s;", SERIAL_STATEMENT);
        }
    }

    public void printImports() {
        println(IMPORT_STATEMENT);
    }

    @Override
    protected String beforeClose(String string) {
        StringBuilder importString = new StringBuilder();
        String pkg = "";

        Matcher m = Pattern.compile("(?s:^.*?[\\r\\n]+package\\s+(.*?);?[\\r\\n]+.*?$)").matcher(string);
        if (m.find())
            pkg = m.group(1);

        Pattern samePackagePattern = Pattern.compile(pkg + "\\.[^\\.]+");

        String previous = "";
        for (String imp : qualifiedTypes) {

            // [#4021] For Scala interoperability, we better also import
            // java.lang types
            if (isJava && imp.startsWith("java.lang."))
                continue;

            // Don't import the class itself
            if (imp.endsWith("." + className))
                continue;

            // [#4229] [#4531] Avoid warnings due to unnecessary same-package imports
            if (pkg.length() > 0 && samePackagePattern.matcher(imp).matches())
                continue;

            String topLevelPackage = imp.split("\\.")[0];

            if (!topLevelPackage.equals(previous))
                importString.append("\n");

            importString.append("import ")
                        .append(imp)
                        .append(isScala ? "\n" : ";\n");

            previous = topLevelPackage;
        }

        string = string.replaceAll(IMPORT_STATEMENT, Matcher.quoteReplacement(importString.toString()));
        string = string.replaceAll(SERIAL_STATEMENT, Matcher.quoteReplacement(String.valueOf(string.hashCode())));
        return string;
    }

    @Override
    protected List<String> ref(List<String> clazz, int keepSegments) {
        List<String> result = new ArrayList<String>();

        if (clazz != null) {
            for (String c : clazz) {

                // Skip unqualified and primitive types
                if (c.contains(".")) {

                    // com.example.Table.TABLE.COLUMN (with keepSegments = 3)
                    if (fullyQualifiedTypes == null || !fullyQualifiedTypes.matcher(c).matches()) {
                        Matcher m = TYPE_REFERENCE_PATTERN.matcher(c);

                        if (m.find()) {

                            // [com, example, Table, TABLE, COLUMN]
                            List<String> split = Arrays.asList(m.group(1).split("\\."));

                            // com.example.Table
                            String qualifiedType = StringUtils.join(split.subList(0, split.size() - keepSegments + 1).toArray(), ".");

                            // Table
                            String unqualifiedType = split.get(split.size() - keepSegments);

                            // Table.TABLE.COLUMN
                            String remainder = StringUtils.join(split.subList(split.size() - keepSegments, split.size()).toArray(), ".");

                            if (!className.equals(unqualifiedType) &&
                               (!unqualifiedTypes.containsKey(unqualifiedType) || qualifiedType.equals(unqualifiedTypes.get(unqualifiedType)))) {

                                unqualifiedTypes.put(unqualifiedType, qualifiedType);
                                qualifiedTypes.add(qualifiedType);
                                String generic = m.group(2);

                                // Consider importing generic type arguments, recursively
                                c = remainder
                                  + (PLAIN_GENERIC_TYPE_PATTERN.matcher(generic).matches()
                                  ?  generic.substring(0, 1) + ref(generic.substring(1, generic.length() - 1)) + generic.substring(generic.length() - 1)
                                  :  generic);
                            }
                        }
                    }
                }

                // If any of the above tests fail, c will remain the unchanged,
                // fully qualified type name.
                result.add(c);
            }
        }

        return result;
    }
}
