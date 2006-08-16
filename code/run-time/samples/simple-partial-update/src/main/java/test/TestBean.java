/*
 *
 * Created on 14 luglio 2006, 17.51
 */

package test;

import javax.faces.component.ActionSource;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author agori
 */
public class TestBean {
    
    
    private String text;
    
    
    
    public TestBean() {
      
    }
    
    public String update() {
        return null;
    }

    public void changeText(ActionEvent e) {
	setText("Received ActionEvent at: currentTimeMillis " + System.currentTimeMillis() +
                " phase for event: " + e.getPhaseId().toString() + 
                " source of event: " + e.getComponent().getId());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getter for property immediateButtonImmediate.
     * @return Value of property immediateButtonImmediate.
     */
    public boolean isImmediateButtonIsImmediate() {
        FacesContext context = FacesContext.getCurrentInstance();
        final boolean [] result = new boolean[1];
        result[0] = false;
        
        context.getViewRoot().invokeOnComponent(context, "immediate", new ContextCallback() {
            public void invokeContextCallback(FacesContext facesContext, UIComponent comp) {
                result[0] = ((ActionSource)comp).isImmediate();
            }
        });

        return result[0];
    }
    
   
    
}
