/*
 *
 * Created on 13 novembre 2006, 13.47
 */

package org.asyncfaces;

import java.util.Map;


/**
 *
 * @author agori
 */
public class Parser {
    
    private static final char STRING_SEPARATOR = '$';
    private static final char PARAM_SEPARATOR = ';';
    private static final char PARAM_NAME_SEPARATOR = ':';
    private static final char COMMA_ALIAS = '!';
    
    private Target target = new Target();
    private String text;
    private String extra;
    private int index = 0;
    private boolean parsingParamValue;
    private String currentParamName;
    int paramIndex = 0;
    
    public Parser(String text) {
        this.text = text;
    }
    
    public static void main(String[] args) {
        String language = "text.org.asyncfaces.encoder.SetClassName($red!Class$; prop2: $f\\!oo$)";
        
        Target target = new Parser(language).parse();
        
        target.print();
        
        System.out.println(Parser.compile(target));
    }
    
    public Target parse() {
        int idIndex = text.indexOf(".");
        target.setClientId(text.substring(0, idIndex).trim());
        
        int braceIndex = text.indexOf("(");
        target.setServerHandler(text.substring(idIndex + 1, braceIndex).trim());
        
        extra = text.substring(braceIndex + 1).trim();
        int from = 0;
        
        while (isIndexInBound()) {
            char c = extra.charAt(index);
            switch(c) {
                case PARAM_NAME_SEPARATOR:
                    if (!parsingParamValue) {
                        parseParamName(from);
                    }
                    break;
                case STRING_SEPARATOR:
                    if (!parsingParamValue) {
                        parsingParamValue = true;
                        parseParamValue();
                        from = index;
                    }
                    break;
            }
            ++index;
        }
        
        return target;
    }
    
    private void parseParamName(int from) {
        currentParamName = extra.substring(from, index);
    }
    
    private void parseParamValue() {
        index++;
        int start = index;
        while(isIndexInBound()) {
            char c = extra.charAt(index);
            char prev = extra.charAt(index - 1);
            if ((c == STRING_SEPARATOR) && (prev != '\\')) {
                break;
            }
            index++;
        }
        String value = extra.substring(start, index);
        if (null == currentParamName) {
            currentParamName = "" + paramIndex++;
        }
        value = adjustValue(value);
        target.getParameters().put(currentParamName, value);
        parsingParamValue = false;
        currentParamName = null;
        index++;
        skipChars(new char[] {PARAM_SEPARATOR, ' ', '\t'});
    }
    
    private String adjustValue(String value) {
        value = value.replaceAll("\\\\\\" + STRING_SEPARATOR, "\\" + STRING_SEPARATOR);
        value = value.replaceAll("\\\\" + COMMA_ALIAS, ",");
        return value;
    }
    
    private void skipChars(char[] seps) {
        while(isIndexInBound()) {
            boolean any = false;
            for (char c : seps) {
                if (extra.charAt(index) == c) {
                    index++;
                    any = true;
                    continue;
                }
            }
            if (!any) {
                index--;
                break;
            }
            index++;
        }
    }
    
    private boolean isIndexInBound() {
        return index < extra.length();
    }
    
    
    public static String compile(Target target) {
        StringBuffer sb = new StringBuffer();
        sb.append(target.getClientId());
        sb.append(".");
        sb.append(target.getServerHandler());
        sb.append("(");
        for (Map.Entry entry : target.getParameters().entrySet()) {
            String name = (String) entry.getKey();
            String value = (String) entry.getValue();
            value = value.replaceAll("\\" + STRING_SEPARATOR, "\\\\\\" + STRING_SEPARATOR);
            value = value.replaceAll(",", "\\\\" + COMMA_ALIAS);
            value = STRING_SEPARATOR + value + STRING_SEPARATOR;
            sb.append(name);
            sb.append(":");
            sb.append(value);
            sb.append(";");
        }
        sb.append(")");
        return sb.toString();
    }
}
