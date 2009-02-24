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
/**
 * Provides annotations necessary to generate run-time and design-time source
 * to support JSF component development. There are annotations for identifying
 * JSF renderers, JSF components, and component properties. A property may be
 * annotated in any class or interface, not just in a component class.
 *
 * <p>An simple annotated component class might look something like this:</p>
 * <pre>
 *   &#64;Component(
 *       name="myComponent",
 *       family="org.example.mycomponent",
 *       displayName="My Component",
 *       tagName="my-component"
 *   )
 *   public class MyComponent extends UIComponent {
 *
 *       &#64;Property (
 *           name="myProperty",
 *           displayName="My Property",
 *           attribute=&#64;Attribute(name="my-property",isRequired=false)
 *       )
 *       private String myProperty;
 *
 *       public String getMyProperty() {
 *           return this.myProperty;
 *       }
 *
 *       public void setMyProperty(String myProperty) {
 *           this.myProperty = mhyProperty;
 *       }
 *   
 *   }
 * </pre>
 */
package com.sun.faces.annotation;