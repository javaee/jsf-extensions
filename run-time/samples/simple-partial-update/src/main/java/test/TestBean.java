/*
 *
 * Created on 14 luglio 2006, 17.51
 */

package test;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
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
    
   
    
}
