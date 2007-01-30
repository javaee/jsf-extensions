package com.enverio.jsf;

import javax.el.MethodExpression;
import javax.faces.convert.Converter;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.jsf.ConvertHandler;
import com.sun.facelets.tag.jsf.ConverterConfig;

public class LookupHandler extends ConvertHandler {
    
    private final TagAttribute lookup;

    public LookupHandler(ConverterConfig config) {
        super(config);
        lookup = this.getRequiredAttribute("lookup");
    }

    protected Converter createConverter(FaceletContext ctx) {
        MethodExpression me = this.lookup.getMethodExpression(ctx, Object.class, new Class[] { String.class });
        return new LookupConverter(me);
    }

}
