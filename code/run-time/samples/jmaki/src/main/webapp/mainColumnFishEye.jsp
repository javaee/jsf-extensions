<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/blueprints/ui-non-ajax" prefix="d" %>
<%@ taglib prefix="a" uri="http://java.sun.com/jmaki-jsf" %>
<%@taglib prefix="jsfExt" uri="http://java.sun.com/jsf/extensions/dynafaces"%> 

<jsfExt:scripts/>

<h:form id="form" prependId="false">


<table bgcolor="#eeeeee" border="0" cellpadding="2" cellspacing="0" width="100%" class="vtop">
<colgroup>
  <col width="100%" />
</colgroup>

<tr>
<td class="headerbar3" colspan="2">
<div class="headerpadding2"><span style="color:#fff;">JSF, Dynamic Faces, and the Dojo FishEye widget.</span></div>

</td>
</tr>

<tr>

<td colspan="2">

    <a:ajax name="dojo.fisheye"
            value="#{fishEyeBean.selectedIndex}"
            valueChangeListener="#{fishEyeBean.valueChanged}"
            args="{items:[
                  {iconSrc:'images/3_Galbraith_small.jpg',caption:'Ben',index:0},
                  {iconSrc:'images/2_Almaer_small.jpg',caption:'Dion',index:1},
                  {iconSrc:'images/1255_Raskin_small.jpg',caption:'Aza',index:2},
                  {iconSrc:'images/1254_Lewis_Ship_small.jpg',caption:'Howard',index:3}
            ]}"

    />


</td>
</tr>

<tr>

<td colspan="2">

<p>This example shows how the jMaki dojo FishEye component is fully
integrated with JSF.  The page has two message areas that are updated
via Ajax in response to the selection of one of the items in the
fishEye.  The jMaki FishEye widget has a value binding to an
<code>int</code> property on a request scoped managed bean.  It also has
a <code>valueChangeListener</code> method binding pointing to a method
on that same managed bean.  Choosing one of the items in the list both
causes the current value of the component to be updated, with type
conversion to <code>int</code>, and the <code>valueChangeListener</code>
to be called, which stores a message in request scope which is output by
an <code>outputText</code> as well as making it so only the two
<code>outputText</code> components are rendered.  The selected message
strings are all pulled from the managed bean.</p>

</td>

</tr>


<tr>

<td>Selected Person <h:outputText id="personMessage" value="#{fishEyeBean.personMessage}" />
</td>

<td>Message from ValueChangeListener <h:outputText id="fishEyeMessage" value="#{requestScope.fishEyeMessage}" />

</td>

</tr>

</table>

 </h:form>
