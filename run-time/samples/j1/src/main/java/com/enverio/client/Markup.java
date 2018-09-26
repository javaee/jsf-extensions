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
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

public class Markup implements Script {

    private static class ElementStart implements Script {

        private final String name;

        public ElementStart(String name) {
            this.name = name;
        }

        public void write(String variable, ClientWriter writer)
                throws IOException {
            writer.startElement(this.name, null);
        }
    }

    private static class Attribute implements Script {
        private final String name;

        private final Object value;

        public Attribute(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public void write(String variable, ClientWriter writer)
                throws IOException {
            writer.writeAttribute(this.name, this.value, null);
        }
    }

    private static class URIAttribute implements Script {
        private final String name;

        private final Object value;

        public URIAttribute(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public void write(String variable, ClientWriter writer)
                throws IOException {
            writer.writeURIAttribute(this.name, this.value, null);
        }
    }

    private static class ElementEnd implements Script {

        private final String name;

        public ElementEnd(String name) {
            this.name = name;
        }

        public void write(String variable, ClientWriter writer)
                throws IOException {
            writer.endElement(this.name);
        }
    }

    private static class Text implements Script {

        private final Object value;

        public Text(Object value) {
            this.value = value;
        }

        public void write(String variable, ClientWriter writer)
                throws IOException {
            writer.writeText(this.value, null);
        }
    }

    private static class Write implements Script {

        private final Object value;

        public Write(Object value) {
            this.value = value;
        }

        public void write(String variable, ClientWriter writer)
                throws IOException {
            writer.write((this.value != null) ? this.value.toString() : "");
        }
    }

    private static class Event implements Script {
        private final String name;

        private final Script[] s;

        public Event(String name, Script... s) {
            this.name = name;
            this.s = s;
        }

        public void write(String variable, ClientWriter writer)
                throws IOException {
            StringWriter sw = new StringWriter(32);
            ClientWriter sww = (ClientWriter) writer.cloneWithWriter(sw);
            for (Script i : this.s) {
                i.write("this", sww);
            }
            sw.close();
            writer.writeAttribute("on"+this.name, sw.toString(), null);
        }
    }

    private final List<Script> events = new ArrayList<Script>(3);

    // TODO implement element stacking

    public Markup() {
        super();
    }

    public Markup start(String name) {
        this.events.add(new ElementStart(name));
        return this;
    }

    public Markup end(String name) {
        this.events.add(new ElementEnd(name));
        return this;
    }

    public Markup attr(String name, Object value) {
        this.events.add(new Attribute(name, value));
        return this;
    }

    public Markup uri(String name, Object value) {
        this.events.add(new URIAttribute(name, value));
        return this;
    }

    public Markup text(Object value) {
        this.events.add(new Text(value));
        return this;
    }

    public Markup write(Object value) {
        this.events.add(new Write(value));
        return this;
    }

    public Markup event(String name, Script... s) {
        if (s == null)
            return this;
        this.events.add(new Event(name, s));
        return this;
    }

    public void write(String variable, ClientWriter writer) throws IOException {
        for (Script s : events) {
            s.write(variable, writer);
        }
    }
}
