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
