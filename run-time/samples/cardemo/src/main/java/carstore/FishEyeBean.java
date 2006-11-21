/* Copyright 2005 Sun Microsystems, Inc. All rights reserved. You may not modify, use, reproduce, or distribute this software except in compliance with the terms of the License at: http://developer.sun.com/berkeley_license.html
 */

package carstore;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import carstore.CarStore;

/**
 *
 * @author edburns
 */
public class FishEyeBean {
    
    /** Creates a new instance of FishEyeBean */
    public FishEyeBean() {
    }
    
    public void valueChanged(ValueChangeEvent e) {
        // Request DynaFaces to re-render only the fishEyeMessage.
        AsyncResponse async = AsyncResponse.getInstance();
        List<String> renderSubtrees = async.getRenderSubtrees();
	  updateCurrentModel();
        renderSubtrees.clear();
	  renderSubtrees.add("cardetails");
	  renderSubtrees.add("pricing");
	  renderSubtrees.add("options");
	  renderSubtrees.add("otheroptions");
    }

    /**
     * Holds value of property selectedIndex.
     */
    private int selectedIndex;

    /**
     * Getter for property selectedIndex.
     * @return Value of property selectedIndex.
     */
    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    /**
     * Setter for property selectedIndex.
     * @param selectedIndex New value of property selectedIndex.
     */
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
    
    
    public void updateCurrentModel(){
	    int index = getSelectedIndex();
	    System.out.println("FEB: index " + index);
	    CarStore carstore = new CarStore();
	    carstore = (CarStore)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("carstore");
	    switch(index){
		    case 0: carstore.storeFrontJalopyPressed(); break;
		    case 1: carstore.storeFrontLuxuryPressed(); break;
		    case 2: carstore.storeFrontRoadsterPressed(); break;
		    case 3: carstore.storeFrontSUVPressed(); break;
		    default: break;
	    }
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("carstore", carstore);
	    CarBean carBean = new CarBean();
	    carBean = carstore.getCurrentModel();
	    Integer integer = carBean.getCurrentPrice();
	    int price = integer.intValue();
	    System.out.println("FEB: new price " + price);
	 
    }
        
}
