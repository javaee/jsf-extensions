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
