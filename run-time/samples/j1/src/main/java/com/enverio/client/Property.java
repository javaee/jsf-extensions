/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.enverio.client;

import java.io.IOException;

public class Property {

    private static class PropertyScript implements Script {
        private final String name;

        private final Object value;

        public PropertyScript(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public void write(String variable, ClientWriter writer)
                throws IOException {
            StringBuffer sb = new StringBuffer(32);
            sb.append(variable).append('.').append(this.name).append('=');
            if (this.value == null) {
                sb.append("''");
            } else if (this.value instanceof Boolean) {
                sb.append(((Boolean) this.value).toString());
            } else if (this.value instanceof Number) {
                sb.append(this.value.toString());
            } else {
                sb.append('\'').append(this.value.toString()).append('\'');
            }
            sb.append(';');
            writer.write(sb.toString());
        }
    }

    private Property() {
        super();
    }

    public static Script set(String property, Object value) {
        if (property == null)
            return null;
        return new PropertyScript(property, value);

    }

}
