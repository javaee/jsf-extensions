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
 * Annotation that identifies a class as being a JSF renderer. The annotated type
 * type must be a public class.
 *
 * <p>A renderer annotation should contain one or more rendering declarations, 
 * using the {@link Renderer.Renders} annotation. Each such entry corresponds to one renderer
 * registration in the Faces application.
 *
 * <p>In the simplest case, a renderer is used for just one component family, and
 * the renderer type is defauled to the component family name:
 * <pre>
 *    &#64;Renderer(&#64;Renders(componentFamily="org.example.input"))
 * </pre>
 * A renderer annotation may be used to declare more than one rendering type and
 * component family combination.
 * <pre>
 *    &#64;Renderer({
 *        &#64;Renders(componentFamily="org.example.input"),
 *        &#64;Renders(componentFamily="org.example.output")
 *    })
 * </pre>
 * If a single rendering type is used with more than one component family, a value
 * for the renderer type must be supplied explicitly:
 * <pre>
 *    &#64;Renderer(&#64;Renders(
 *            renderType="org.example.renderAll",
 *            componentFamily={"org.example.input", "org.example.output"})
 *    )
 * </pre>
 *
 * @author gjmurphy
 * @see Renderer.Renders
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Renderer {
    
    /**
     * Zero or more rendering declarations. Each declaration corresponds to an
     * entry in the Faces application.
     */
    public Renders[] value() default {};
    
    
    /**
     * Annotation that identifies a single component-renderer combination. The value
     * of {@code componentFamily} must corresponds to a component family declared
     * in the current compilation unit (see {@link Component#family}). 
     *
     * <p>
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.ANNOTATION_TYPE)
    public @interface Renders {
        
        public String rendererClass() default "";

        /**
         * The renderer type for this component and renderer combination. If
         * this annotation contains a single component family, and a renderer type
         * is not provided, it will default to the component family.
         */
        public String rendererType() default "";
        
        /**
         * The component family or families to which this rendering type applies.
         * The values given must correspond to component families declared in the
         * current compilation unit (see {@link Component#family}).
         */
        public String[] componentFamily();
    }
    
}
