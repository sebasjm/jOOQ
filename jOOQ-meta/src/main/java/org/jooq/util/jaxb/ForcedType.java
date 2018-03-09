







package org.jooq.util.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.jooq.util.jaxb.tools.StringAdapter;


/**
 * A forced type declaration
 *
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ForcedType", propOrder = {

})
@SuppressWarnings({
    "all"
})
public class ForcedType implements Serializable
{

    private final static long serialVersionUID = 31100L;
    @XmlJavaTypeAdapter(StringAdapter.class)
    protected String name;
    @XmlJavaTypeAdapter(StringAdapter.class)
    protected String userType;
    @XmlJavaTypeAdapter(StringAdapter.class)
    protected String converter;
    protected Boolean enumConverter;
    @XmlJavaTypeAdapter(StringAdapter.class)
    protected String binding;
    @XmlJavaTypeAdapter(StringAdapter.class)
    protected String expression;
    @XmlJavaTypeAdapter(StringAdapter.class)
    protected String expressions;
    @XmlJavaTypeAdapter(StringAdapter.class)
    protected String types;
    @XmlElement(defaultValue = "ALL")
    @XmlSchemaType(name = "string")
    protected Nullability nullability = Nullability.ALL;

    /**
     * The name (in {@link org.jooq.impl.SQLDataType}) to force any matches to
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * The type of the user type - e.g. java.time.LocalDateTime.
     * <p>
     * If provided, {@link #getName()} will be ignored, and either {@link #getConverter()}
     * or {@link #getBinding()} is required
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the value of the userType property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUserType(String value) {
        this.userType = value;
    }

    /**
     * A converter implementation for the {@link #getUserType()}.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getConverter() {
        return converter;
    }

    /**
     * Sets the value of the converter property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setConverter(String value) {
        this.converter = value;
    }

    /**
     * Whether the converter is an {@link org.jooq.impl.EnumConverter}.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isEnumConverter() {
        return enumConverter;
    }

    /**
     * Sets the value of the enumConverter property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setEnumConverter(Boolean value) {
        this.enumConverter = value;
    }

    /**
     * A {@link org.jooq.Binding} implementation for the custom type.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBinding() {
        return binding;
    }

    /**
     * Sets the value of the binding property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBinding(String value) {
        this.binding = value;
    }

    /**
     * A Java regular expression matching columns, parameters, attributes,
     * etc to be forced to have this type. If provided, both "expressions" and
     * "types" must match.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Sets the value of the expression property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setExpression(String value) {
        this.expression = value;
    }

    /**
     * The same as expression. This is kept for backwards compatibility reasons.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getExpressions() {
        return expressions;
    }

    /**
     * Sets the value of the expressions property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setExpressions(String value) {
        this.expressions = value;
    }

    /**
     * A Java regular expression matching data types to be forced to have this
     * type. If provided, both "expression" and "types" must match.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTypes() {
        return types;
    }

    /**
     * Sets the value of the types property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTypes(String value) {
        this.types = value;
    }

    /**
     * Whether this forced type should apply to nullable / non-nullable / all columns
     *
     * @return
     *     possible object is
     *     {@link Nullability }
     *
     */
    public Nullability getNullability() {
        return nullability;
    }

    /**
     * Sets the value of the nullability property.
     *
     * @param value
     *     allowed object is
     *     {@link Nullability }
     *
     */
    public void setNullability(Nullability value) {
        this.nullability = value;
    }

    public ForcedType withName(String value) {
        setName(value);
        return this;
    }

    public ForcedType withUserType(String value) {
        setUserType(value);
        return this;
    }

    public ForcedType withConverter(String value) {
        setConverter(value);
        return this;
    }

    public ForcedType withEnumConverter(Boolean value) {
        setEnumConverter(value);
        return this;
    }

    public ForcedType withBinding(String value) {
        setBinding(value);
        return this;
    }

    public ForcedType withExpression(String value) {
        setExpression(value);
        return this;
    }

    public ForcedType withExpressions(String value) {
        setExpressions(value);
        return this;
    }

    public ForcedType withTypes(String value) {
        setTypes(value);
        return this;
    }

    public ForcedType withNullability(Nullability value) {
        setNullability(value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (name!= null) {
            sb.append("<name>");
            sb.append(name);
            sb.append("</name>");
        }
        if (userType!= null) {
            sb.append("<userType>");
            sb.append(userType);
            sb.append("</userType>");
        }
        if (converter!= null) {
            sb.append("<converter>");
            sb.append(converter);
            sb.append("</converter>");
        }
        if (enumConverter!= null) {
            sb.append("<enumConverter>");
            sb.append(enumConverter);
            sb.append("</enumConverter>");
        }
        if (binding!= null) {
            sb.append("<binding>");
            sb.append(binding);
            sb.append("</binding>");
        }
        if (expression!= null) {
            sb.append("<expression>");
            sb.append(expression);
            sb.append("</expression>");
        }
        if (expressions!= null) {
            sb.append("<expressions>");
            sb.append(expressions);
            sb.append("</expressions>");
        }
        if (types!= null) {
            sb.append("<types>");
            sb.append(types);
            sb.append("</types>");
        }
        if (nullability!= null) {
            sb.append("<nullability>");
            sb.append(nullability);
            sb.append("</nullability>");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass()!= that.getClass()) {
            return false;
        }
        ForcedType other = ((ForcedType) that);
        if (name == null) {
            if (other.name!= null) {
                return false;
            }
        } else {
            if (!name.equals(other.name)) {
                return false;
            }
        }
        if (userType == null) {
            if (other.userType!= null) {
                return false;
            }
        } else {
            if (!userType.equals(other.userType)) {
                return false;
            }
        }
        if (converter == null) {
            if (other.converter!= null) {
                return false;
            }
        } else {
            if (!converter.equals(other.converter)) {
                return false;
            }
        }
        if (enumConverter == null) {
            if (other.enumConverter!= null) {
                return false;
            }
        } else {
            if (!enumConverter.equals(other.enumConverter)) {
                return false;
            }
        }
        if (binding == null) {
            if (other.binding!= null) {
                return false;
            }
        } else {
            if (!binding.equals(other.binding)) {
                return false;
            }
        }
        if (expression == null) {
            if (other.expression!= null) {
                return false;
            }
        } else {
            if (!expression.equals(other.expression)) {
                return false;
            }
        }
        if (expressions == null) {
            if (other.expressions!= null) {
                return false;
            }
        } else {
            if (!expressions.equals(other.expressions)) {
                return false;
            }
        }
        if (types == null) {
            if (other.types!= null) {
                return false;
            }
        } else {
            if (!types.equals(other.types)) {
                return false;
            }
        }
        if (nullability == null) {
            if (other.nullability!= null) {
                return false;
            }
        } else {
            if (!nullability.equals(other.nullability)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = ((prime*result)+((name == null)? 0 :name.hashCode()));
        result = ((prime*result)+((userType == null)? 0 :userType.hashCode()));
        result = ((prime*result)+((converter == null)? 0 :converter.hashCode()));
        result = ((prime*result)+((enumConverter == null)? 0 :enumConverter.hashCode()));
        result = ((prime*result)+((binding == null)? 0 :binding.hashCode()));
        result = ((prime*result)+((expression == null)? 0 :expression.hashCode()));
        result = ((prime*result)+((expressions == null)? 0 :expressions.hashCode()));
        result = ((prime*result)+((types == null)? 0 :types.hashCode()));
        result = ((prime*result)+((nullability == null)? 0 :nullability.hashCode()));
        return result;
    }

}
