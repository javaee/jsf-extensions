package com.enverio.client;

import java.io.IOException;

public class Insertion {

    private static class Replace extends Markup {
        public void write(String variable, ClientWriter writer)
                throws IOException {
            StringBuffer sb = new StringBuffer(16);
            sb.append(variable).append(".innerHTML='");
            writer.write(sb.toString());
            super.write(variable, writer);
            writer.write("';");
        }
    }
    
    private static class Insert extends Markup {
        private final String location;
        
        public Insert(String loc) {
            this.location = loc;
        }
        
        public void write(String variable, ClientWriter writer)
                throws IOException {
            StringBuffer sb = new StringBuffer(64);
            sb.append("new Insertion.").append(this.location).append('(').append(variable).append(",'");
            writer.write(sb.toString());
            super.write(variable, writer);
            writer.write("');");
        }
    };

    private Insertion() {
    }

    public static Markup replace() {
        return new Replace();
    }
    
    public static Markup before() {
        return new Insert("Before");
    }
    
    public static Markup after() {
        return new Insert("After");
    }
    
    public static Markup top() {
        return new Insert("Top");
    }
    
    public static Markup bottom() {
        return new Insert("Bottom");
    }

}
