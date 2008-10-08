/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.faces.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that identifies a field or method as belonging to a JSF component
 * property. A property is a logical entity that consists of three source artifacts:
 * <ul>
 * <li>a public "getter" method for reading the property value
 * <li>an optional public "setter" method for writing the property value
 * <li>an optional private or protected instance variable for storing the property
 * value
 * </ul>
 * Any of these source artifacts may be annotated, however, it is preferable to
 * annotate the property instance variable, if present, otherwise, the "getter" method.
 * The "setter" method should be annotated only if it is used to override a method
 * in a super class, and the "getter" method is not overridden. The annotation for
 * a given property should occur only once per component.
 *
 * <p>A property may be annotated in a component class, a non-component class, or
 * an interface. It is generally a good idea to define common properties once, in
 * an abstract base class or interface. Inherited property metadata may be overridden,
 * by providing an annotation on the overriding getter or setter method. Annotation
 * element values from the super class will be inherited unless overridden by
 * annotation element values in the child class.
 *
 * <p>A property is uniquely identified by its {@link #name}. If no name is provided,
 * one will be supplied. If an instance variable is annotated, the instance name
 * is used as the default property name. If a method is annotated, and the method
 * name follows Beans naming conventions, a name will be deduced. The following
 * annotations are equivalent, all resulting in a property with the name {@code myProperty}:
 * <pre>
 *    &#64;Property()
 *    private String myProperty;
 *
 *    &#64;Property()
 *    public String getMyProperty();
 *
 *    &#64;Property(name="myProperty")
 * </pre>
 *
 * <p>Property read and write method names need be supplied only if the method names
 * do not follow standard Java Beans conventions. The following example uses standard
 * naming conventions:
 * <pre>
 *    &#64;Property()
 *    private String myProperty;
 *
 *    public String getMyProperty() {
 *        return this.myProperty;
 *    }
 *
 *    public void setMyProperty(String myProperty) {
 *        this.myProperty = myProperty;
 *    }
 * </pre>
 * Whereas this example does not:
 * <pre>
 *    &#64;Property(
 *        readMethodName="getMyPropertyPrettyPlease",
 *        writeMethodName="setMyPropertyPrettyPlease")
 *    private String myProperty;
 *
 *    public String getMyPropertyPrettyPlease() {
 *        return this.myProperty;
 *    }
 *
 *    public void setMyPropertyPrettyPlease(String myProperty) {
 *        this.myProperty = myProperty;
 *    }
 * </pre>
 *
 * <p>A property may or may not correspond to a JSP tag attribute. By default,
 * properties are associated with tag attributes of the same name. For example,
 * the annotation
 * <pre>
 *    &#64;Property(name="myProperty")
 * </pre>
 * is assumed to correspond to a tag attribute also called {@code myProperty}.
 * All of the following annotations are equivalent:
 * <pre>
 *    &#64;Property(name="myProperty")
 *    &#64;Property(name="myProperty",isAttribute=true)
 *    &#64;Property(name="myProperty",attribute=&#64;Attribute(name="myProperty"))
 *    &#64;Property(name="myProperty",isAttribute=true,attribute=&#64;Attribute(name="myProperty"))
 * </pre>
 * See the {@link Attribute} annotation for more information about attribute
 * metadata. If a property does not correspond to an attribute, this information must
 * be stated explicitly:
 * <pre>
 *    &#64;Property(name="myProperty",isAttribute=false)
 * </pre>
 * If a property corresponds to an attribute with a name different from the property
 * name, an attribute name must be provided explicitly:
 * <pre>
 *    &#64;Property(name="myProperty",attribute=&#64;Attribute(name="my-property"))
 * </pre>
 *
 * <p>The javadoc-formatted comment found with the annotated property declaration
 * (field, getter method, or setter method) is also considered part of this property's
 * metadata. Typically, the first sentence of the comment will be used as the
 * property's default short description.
 *
 * <p>Some property elements may be used to generated localizable values. Such
 * elements are themselves annotated with the {@link Localizable} annotation.
 *
 * @author gjmurphy
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Property {
    
    /**
     * This property's unique name. The name must be unique within the scope of
     * the property's class. If no value is provided, the following logic is used
     * to supply a default name: if a field is annotated, use the field instance
     * name; if a "getter" method is annotated, and the method name begins with
     * "get", use the remainder of the name, with the fist character translated from
     * upper to lower case; if a "setter" method is annotage, and the method name
     * begins with "set", use the remainder of the name, with the fist character
     * translated from upper to lower case. If none of these condiditions are met,
     * an error will be reported.
     */
    public String name() default "";
    
    /**
     * The display name, typically used at design-time. If no value is provided, the
     * {@link #name} will be used.
     */
    //@Localizable
    public String displayName() default "";
    
    /**
     * An optional short description of this property, typically used as a tool
     * tip at design-time. If no value is provided, the first sentence of the
     * javadoc-formatted comment for this property will be used.
     */
    //@Localizable
    public String shortDescription() default "";
    
    /**
     * The name of the "getter" method for this property. A value need be specified
     * only if the annotation is placed elsewhere than the "getter" method, and if
     * the method name does not follow the standard convention.
     */
    public String readMethodName() default "";
    
    /**
     * The name of the "setter" method for this property. A value need be specified
     * only if the annotation is placed elsewhere than the "setter" method, and if
     * the method name does not follow the standard convention.
     */
    public String writeMethodName() default "";
    
    /**
     * The name of a property category descriptor, asserting that this property
     * belongs to the named design-time category. The value must refer to an annotated
     * category descriptor of the given name, found in the current compilation
     * unit. See {@link PropertyCategory}.
     */
    public String category() default "";
    
    /**
     * Indicates whether this property is the default property for its component.
     * Only one property may be declared to be the default property for a component.
     * If a component does not declare a default property and does not inherit a
     * default property, and implements {@link javax.faces.component.ValueHolder},
     * the {@code value} property will be made the default.
     */
    public boolean isDefault() default false;
    
    /**
     * Indicates whether this property should be hidden at design-time.
     */
    public boolean isHidden() default false;
    
    /**
     * The fully qualified name of a property editor class, to be instantiated by the
     * IDE for editing this property's values. An IDE will provide a reasonable default
     * editor based on the property type.
     */
    public String editorClassName() default "";
    
    /**
     * Indicates whether this property corresponds to a JSP tag attribute.
     */
    public boolean isAttribute() default true;
    
    /**
     * Metadata about the JSP tag attribute corresponding to this property. This
     * element must not be specified if {@link #isAttribute} is set to false.
     */
    public Attribute attribute() default @Attribute;
    
    
    /**
     * Annotation that provides additional information about properties of type
     * {@link javax.el.MethodExpression}, which are used to invoke a method at run-time.
     * All properties of type {@code MethodExpression} must be thus annotated, and the
     * annotation must specify either the signature of the method to which method
     * expressions are to be bound, or, a component event to which this property
     * corresponds. At least one, but not both, must be specified.
     *
     * <p>Properties that refer to an event are typically used to invoke a handler method
     * on a backing bean which does not implement the event's listener interface, a common
     * Faces design pattern. For example, to respond to a change in the value of input
     * component, one can register an instance of a class that implements
     * {@link javax.faces.event.ValueChangeListener}, or, one can set a method expression
     * bound to a method whose signature matches {@code void valueChange(ValueChangeEvent)}.
     *
     * <p>If a reference to an event is given, the event's listener class or interface
     * must contain only a single method.
     *
     * <p>The JSF {@code actionExpression} property of {@link javax.faces.component.UICommand}
     * might be annotated thusly:
     *
     * <pre>
     *    &#64;Property(name="action")
     *    &#64;PropertyMethod(signature="java.lang.String action()")
     *    public MethodExpression getActionExpression() {...}
     * </pre>
     *
     * <p>The JSF {@code valueChange} event of {@link javax.faces.component.UIInput}
     * might be annotated thusly:
     *
     * <pre>
     *    &#64;Property(name="valueChange")
     *    &#64;PropertyMethod(event="valueChange")
     *    public MethodExpression getValueChangeExpression() {...}
     *
     *    &#64;Event(name="valueChange")
     *    public void addValueChangeListener(ValueChangeListener listener) {...}
     *    public void removeValueChangeListener(ValueChangeListener listener) {...}
     *    public ValueChangeListener[] getValueChangeListeners() {...}
     * </pre>
     *
     * Note that in the above example, since the "remove" and "get" method names follow
     * standard naming conventions, they need not be specified explicitly.
     *
     * @author gjmurphy
     */
    //TODO Need a way for this information to be introspected from property descriptors
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.METHOD})
    public @interface Method {
        
        /**
         * The complete signature of the method to which the annotated method
         * expression should be bound.
         */
        String signature() default "";
        
        /**
         * Name of the component event to which this property corresponds. The named
         * event must exist within the scope of this property's component.
         */
        String event() default "";
    }
    
}
