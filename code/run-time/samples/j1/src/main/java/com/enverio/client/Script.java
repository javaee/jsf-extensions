package com.enverio.client;

import java.io.IOException;

public interface Script {
    public void write(String variable, ClientWriter writer) throws IOException;
}
