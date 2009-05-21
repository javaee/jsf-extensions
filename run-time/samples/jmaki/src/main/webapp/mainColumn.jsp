<%@ taglib uri="http://java.sun.com/blueprints/ui" prefix="d" %>
<%@ taglib prefix="a" uri="http://jmaki/v1.0/jsf" %>

<h:form id="form">

<table bgcolor="#eeeeee" border="0" cellpadding="2" cellspacing="0" width="100%" class="vtop">
<colgroup>
  <col width="100%" />
</colgroup>

<tr>
<td class="headerbar3">
<div class="headerpadding2"><span style="color:#fff;">JSF, The Blueprints Scroller, and jMaki</span></div>

</td>
</tr>

<tr>

<td>

  <h:dataTable columnClasses="list-column-center,list-column-center,
                               list-column-center, list-column-center"
                  headerClass="list-header"
                   rowClasses="list-row-even,list-row-odd"
                   styleClass="list-background"
                           id="table"
                         rows="10"
                      binding="#{ResultSetBean.data}"
                        value="#{ResultSetBean.list}"
                          var="customer">

    <h:column>
      <f:facet           name="header">
        <h:outputText  value="Account Id"/>
      </f:facet>
      <h:outputText        id="accountId"
                     value="#{customer.accountId}"/>
    </h:column>

    <h:column>
      <f:facet           name="header">
        <h:outputText  value="Customer Name"/>
      </f:facet>
      <a:widget name="scriptaculous.inplace" value="#{customer.name}"/>
    </h:column>

    <h:column>
      <f:facet           name="header">
        <h:outputText  value="Symbol"/>
      </f:facet>
      <h:outputText        id="symbol"
                     value="#{customer.symbol}"/>
    </h:column>

    <h:column>
      <f:facet           name="header">
        <h:outputText  value="Total Sales"/>
      </f:facet>
      <h:outputText       id="totalSales"
                     value="#{customer.totalSales}"/>
    </h:column>

  </h:dataTable>

<h:panelGroup id="subview2">
  <d:scroller id="scroller" navFacetOrientation="NORTH" for="table" 
          actionListener="#{ResultSetBean.processScrollEvent}">
      <f:facet name="header">
        <h:panelGroup>
          <h:outputText value="Account Id"/>
          <h:outputText value="Customer Name"/>
          <h:outputText value="Symbol"/>
          <h:outputText value="Total Sales"/>
        </h:panelGroup>
      </f:facet>

      <f:facet name="next">
        <h:panelGroup>
          <h:outputText value="Next"/>
          <h:graphicImage url="/images/arrow-right.gif" />
        </h:panelGroup>
      </f:facet>

      <f:facet name="previous">
        <h:panelGroup>
          <h:outputText value="Previous"/>
          <h:graphicImage url="/images/arrow-left.gif" />
        </h:panelGroup>
      </f:facet>

      <f:facet name="number">
         <!-- You can put a panel here if you like -->
      </f:facet>

      <f:facet name="current">
        <h:panelGroup>
          <h:graphicImage url="/images/duke.gif" />
        </h:panelGroup>
      </f:facet>
  </d:scroller>
  <script type='text/javascript'>
    document.forms[0].submit = function() {}; 
    var a = $('form:subview2').getElementsByTagName('a'); 
    $A(a).each(function(e) { 
      DynaFaces.installDeferredAjaxTransaction(e, 'mousedown', { postReplace: 'postReplace', render: 'form:table,form:subview2' }); 
    }); 
  </script>
  </h:panelGroup>

</td>


</tr>


</table>

 </h:form>
