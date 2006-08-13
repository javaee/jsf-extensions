package com.enverio.client;

public class ClientUtils {

    public ClientUtils() {
        super();
    }
    
    public static String toArray(Iterable itr) {
        StringBuffer sb = new StringBuffer(32);
        sb.append('[');
        for (Object obj : itr) {
            if (obj instanceof String) {
                sb.append('\'').append(obj).append("',");
            }
        }
        if (sb.length() > 1) sb.setLength(sb.length()-1);
        sb.append(']');
        return sb.toString();
    }

}
