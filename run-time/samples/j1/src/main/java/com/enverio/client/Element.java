package com.enverio.client;

import java.io.IOException;

public class Element {

    private final static Script Hide = new Script() {
        public void write(String variable, ClientWriter writer)
                throws IOException {
            writer.write(variable);
            writer.write(".style.display='none';");
        }
    };

    private final static Script Show = new Script() {
        public void write(String variable, ClientWriter writer)
                throws IOException {
            writer.write(variable);
            writer.write(".style.display='';");
        }
    };

    private final static Script Toggle = new Script() {
        public void write(String variable, ClientWriter writer)
                throws IOException {
            StringBuffer sb = new StringBuffer(64);
            sb.append(variable).append(".style.display=((").append(variable)
                    .append(".style.display=='none')?'':'none');");
            writer.write(sb.toString());
        }
    };

    private Element() {
        super();
    }

    public static Script hide() {
        return Hide;
    }

    public static Script show() {
        return Show;
    }

    public static Script toggle(boolean show) {
        return show ? Show : Hide;
    }

    public static Script toggle() {
        return Toggle;
    }

}
