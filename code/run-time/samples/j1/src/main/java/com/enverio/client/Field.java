package com.enverio.client;

import java.io.IOException;

public class Field {

	private final static Script Clear = new Script() {
		public void write(String variable, ClientWriter writer) throws IOException {
			writer.write(variable);
			writer.write(".value='';");
		}
	};
	
	public static Script clear() {
		return Clear;
	}
	
}
