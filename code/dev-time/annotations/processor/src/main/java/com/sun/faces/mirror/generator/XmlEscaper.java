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

package com.sun.faces.mirror.generator;

/**
 * An escaper for XML file types.
 */
public class XmlEscaper implements Escaper {
    
  /**
   * Escape the text specified and return it.
   */
  public String escape(String text) {
    if (text == null)
      return null;
    StringBuffer buffer = new StringBuffer();
    for (char c : text.toCharArray()) {
      if (c == '>')
        buffer.append("&gt;");
      else if (c == '<')
        buffer.append("&lt;");
      else if (c == '&')
        buffer.append("&amp;");
      else if (c == '"')
        buffer.append("&quot;");
      else
        buffer.append(c);
    }
    return buffer.toString();
  }
    
}
