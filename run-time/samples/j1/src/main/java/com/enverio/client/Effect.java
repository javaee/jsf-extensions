package com.enverio.client;

import java.io.IOException;

public class Effect {
    
    private final static Script Highlight = new Script() {
        public void write(String variable, ClientWriter writer) throws IOException {
            writer.write("new Effect.Highlight(");
            writer.write(variable);
            writer.write(");");
        }
    };
    
    private final static Script Pulsate = new Script() {
    	public void write(String variable, ClientWriter writer) throws IOException {
            writer.write("new Effect.Pulsate(");
            writer.write(variable);
            writer.write(");");
        } 	
    };

    public Effect() {
        super();
    }
    
    public static Script highlight() {
        return Highlight;
    }
    
    public static Script pulsate() {
    	return Pulsate;
    }

}
