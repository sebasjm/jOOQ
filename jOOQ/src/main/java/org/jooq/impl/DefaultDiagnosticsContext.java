/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: http://www.jooq.org/licenses
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package org.jooq.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jooq.DiagnosticsContext;

/**
 * @author Lukas Eder
 */
final class DefaultDiagnosticsContext implements DiagnosticsContext {

    ResultSet          resultSet;
    boolean            resultSetClosing;
    int                resultSetFetchedColumns;
    int                resultSetActualColumns;
    int                resultSetFetchedRows;
    int                resultSetActualRows;
    final String       actualStatement;
    final String       normalisedStatement;
    final Set<String>  duplicateStatements;
    final List<String> repeatedStatements;
    boolean            resultSetUnnecessaryWasNullCall;
    boolean            resultSetMissingWasNullCall;
    int                resultSetColumnIndex;

    DefaultDiagnosticsContext(String actualStatement) {
        this(actualStatement, actualStatement, Collections.singleton(actualStatement), Collections.singletonList(actualStatement));
    }

    DefaultDiagnosticsContext(String actualStatement, String normalisedStatement, Set<String> duplicateStatements, List<String> repeatedStatements) {
        this.actualStatement = actualStatement;
        this.normalisedStatement = normalisedStatement;
        this.duplicateStatements = duplicateStatements == null ? Collections.<String>emptySet() : duplicateStatements;
        this.repeatedStatements = repeatedStatements == null ? Collections.<String>emptyList() : repeatedStatements;
    }

    @Override
    public final ResultSet resultSet() {
        return resultSet;
    }

    @Override
    public final int resultSetFetchedRows() {
        return resultSet == null ? -1 : resultSetFetchedRows;
    }

    @Override
    public final int resultSetActualRows() {
        if (resultSet == null)
            return -1;

        try {
            if (resultSetClosing || resultSet.getType() != ResultSet.TYPE_FORWARD_ONLY) {
                while (resultSet.next())
                    resultSetActualRows++;

                resultSet.absolute(resultSetFetchedRows);
            }
        }
        catch (SQLException ignore) {}

        return resultSetActualRows;
    }

    @Override
    public final int resultSetFetchedColumns() {
        return resultSet == null ? -1 : resultSetFetchedColumns;
    }

    @Override
    public final int resultSetActualColumns() {
        return resultSet == null ? -1 : resultSetActualColumns;
    }

    @Override
    public final boolean resultSetUnnecessaryWasNullCall() {
        return resultSet == null ? false : resultSetUnnecessaryWasNullCall;
    }

    @Override
    public final boolean resultSetMissingWasNullCall() {
        return resultSet == null ? false : resultSetMissingWasNullCall;
    }

    @Override
    public final int resultSetColumnIndex() {
        return resultSet == null ? 0 : resultSetColumnIndex;
    }

    @Override
    public final String actualStatement() {
        return actualStatement;
    }

    @Override
    public final String normalisedStatement() {
        return normalisedStatement;
    }

    @Override
    public final Set<String> duplicateStatements() {
        return Collections.unmodifiableSet(duplicateStatements);
    }

    @Override
    public final List<String> repeatedStatements() {
        return Collections.unmodifiableList(repeatedStatements);
    }
}
