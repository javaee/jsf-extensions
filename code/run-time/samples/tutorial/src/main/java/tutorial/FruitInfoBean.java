/*
 * Copyright 2007 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */


/*
 * FruitInfoBean.java
 *
 * Created on January 16, 2007, 10:21 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package tutorial;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.event.ValueChangeEvent;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.faces.event.ActionEvent;


/**
 *
 * @author Jennifer Ball
 */
public class FruitInfoBean {
    protected ArrayList<SelectItem> fruits = null;
    protected ArrayList<SelectItem> varieties = null;
    private ResourceBundle fruitMessages = null;

    /** Creates a new instance of FruitInfoBean */
    private ResourceBundle fruitNames = null;
    private String fruit = null;

    /* The following code is used with the installDeferredAjaxTransaction use case */
    private String fruitQuiz = null;

    /* The following code is used with the inspectElement use case */
    private String fruitSaleValue = "";
    private String variety = null;
    private String varietyInfo = null;

    // 
    // Constructors
    //
    public FruitInfoBean() {
        this.init();
    }

    private void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        fruit = "Pear";
        variety = "Aurora";

        // This bundle holds the names of the varieties for each fruit, keyed by the fruit name.
        fruitNames = ResourceBundle.getBundle(
                    "tutorial.FruitNames",
                    context.getViewRoot().getLocale());
        // This bundle holds the information for each variety, keyed by the variety name.
        fruitMessages = ResourceBundle.getBundle(
                    "tutorial.FruitMessages",
                    context.getViewRoot().getLocale());
        varietyInfo = fruitMessages.getString(variety);
    }

    /*
     * Handler method called when user selects a variety from the menu.  This
     * method updates the variety component model value and sets the
     * varietyInfo model value to the appropriate message.
     */
    public String updateVariety(ValueChangeEvent e) {
        String newVariety = (String) e.getNewValue();
        String currentFruit = getFruit();
        setVarietyInfo(fruitMessages.getString(newVariety));
        setFruitSaleValue("");

        return null;
    }

    public String getFruit() {
        return this.fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public ArrayList<SelectItem> getFruits() {
        fruits = new ArrayList<SelectItem>();
        fruits.add(new SelectItem("Pear", "Pear"));
        fruits.add(new SelectItem("Apple", "Apple"));
        fruits.add(new SelectItem("Orange", "Orange"));
        fruits.add(new SelectItem("Peach", "Peach"));

        return fruits;
    }

    public String getVariety() {
        return this.variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public ArrayList<SelectItem> getVarieties() {
        varieties = new ArrayList<SelectItem>();

        String[] fruitVarieties = getFruitVarieties(this.fruit);

        for (int i = 0; i < fruitVarieties.length; i++) {
            varieties.add(new SelectItem(fruitVarieties[i], fruitVarieties[i]));
        }

        return varieties;
    }

    /*
     * Handler method for the event of selecting a fruit.  This method sets the
     * model value for the variety component and sets the varietyInfo model value
     * to the appropriate message.
     */
    public void changeFruit(ValueChangeEvent e) {
        String fruitValue = (String) e.getNewValue();
        String[] varieties = getFruitVarieties(fruitValue);
        setVarietyInfo(fruitMessages.getString(varieties[0]));
        setVariety(varieties[0]);
        setFruitSaleValue("");
    }

    public String getVarietyInfo() {
        return this.varietyInfo;
    }

    public void setVarietyInfo(String varietyInfo) {
        this.varietyInfo = varietyInfo;
    }

    /*
     * Gets the list of varieties for a particular fruit, saves them in an array and returns the array.
     */
    private String[] getFruitVarieties(String key) {
        try {
            return fruitNames.getString(key)
                             .split(",");
        } catch (Exception e) {
            return null;
        }
    }

    public String getFruitQuiz() {
        return this.fruitQuiz;
    }

    public void setFruitQuiz(String fruit) {
        this.fruitQuiz = fruit;
    }

    public void gradeFruitQuiz(ValueChangeEvent e) {
        System.out.print("fruitquiz " + getFruitQuiz());

        String answer = (String) e.getNewValue();
        System.out.println("answer " + answer);

        String answerMessage = null;

        if (answer.equals("peaches")) {
            answerMessage = "You are correct.  Get 20% off Peaches at Duke's Fruit Stand. Use coupon number GTH056.";
        }

        System.out.println("answerMessage " + answerMessage);
        FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequestMap()
                    .put("answerMessage", answerMessage);
        System.out.println(
                "req scope answer message "
                + FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(
                        "answerMessage"));
    }

    public String getFruitSaleValue() {
        return fruitSaleValue;
    }

    public void setFruitSaleValue(String fruitSaleValue) {
        this.fruitSaleValue = fruitSaleValue;
    }

    public void checkSpecials(ValueChangeEvent e) {
        boolean selected = ((Boolean) e.getNewValue()).booleanValue();

        if (selected == true) {
            setFruitSaleValue(
                    fruitMessages.getString(getVariety().concat("Sale")));
        } else {
            setFruitSaleValue("");
        }
    }

    public void specialsSubmit(ActionEvent e) {
        setFruitSaleValue(fruitMessages.getString(getVariety().concat("Sale")));
    }
}
