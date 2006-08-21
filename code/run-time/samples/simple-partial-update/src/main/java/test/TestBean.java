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
    
    
    private String text = "initial value";
    
    
    
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
        
        context.getViewRoot().invokeOnComponent(context, "immediateButton", new ContextCallback() {
            public void invokeContextCallback(FacesContext facesContext, UIComponent comp) {
                result[0] = ((ActionSource)comp).isImmediate();
            }
        });

        return result[0];
    }

    /**
     * Holds value of property requiredText.
     */
    private String requiredText;

    /**
     * Getter for property requiredText.
     * @return Value of property requiredText.
     */
    public String getRequiredText() {
        return this.requiredText;
    }

    /**
     * Setter for property requiredText.
     * @param requiredText New value of property requiredText.
     */
    public void setRequiredText(String requiredText) {
        this.requiredText = requiredText;
    }

    /**
     * Holds value of property requiredImmediateText.
     */
    private String requiredImmediateText;

    /**
     * Getter for property requiredImmediateText.
     * @return Value of property requiredImmediateText.
     */
    public String getRequiredImmediateText() {
        return this.requiredImmediateText;
    }

    /**
     * Setter for property requiredImmediateText.
     * @param requiredImmediateText New value of property requiredImmediateText.
     */
    public void setRequiredImmediateText(String requiredImmediateText) {
        this.requiredImmediateText = requiredImmediateText;
    }
    
   
    
}
