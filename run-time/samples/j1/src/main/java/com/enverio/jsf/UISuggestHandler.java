package com.enverio.jsf;

import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.MethodRule;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;

public class UISuggestHandler extends ComponentHandler {

    public UISuggestHandler(ComponentConfig config) {
        super(config);
        this.getRequiredAttribute("from");
    }

    protected MetaRuleset createMetaRuleset(Class type) {
        return super.createMetaRuleset(type).addRule(
                new MethodRule("from", Object.class,
                        new Class[] { String.class }));
    }

}
