  <h:form id="form1">

  <h:panelGrid columns="2" border="1" width="600">
      
    Put <code>fooValue</code> in the flash under key <code>foo</code>
    using <code>jsfExt:set</code>.  Note that things stored in the flash
    during <b>this</b> request are only retrievable on the <b>next</b>
    request.  If you want to store something on this request and see it
    on this one as well, use either <code>&#35;{flash.now.foo}</code> or
    <code>&#35;{requestScope.foo}</code>.  The former is simply an alias
    for the latter.

    <jsfExt:set var="#{flash.foo}" value="fooValue" />

    <f:verbatim>
      &lt;jsfExt:set var="&#35;{flash.foo}" value="fooValue" /&gt;
    </f:verbatim>

    Value of <code>&#35;{flash.foo}</code>, should be <code>null</code>.

    <h:outputText value="#{flash.foo}" />

    <h:commandButton value="reload" />

    <h:commandButton value="next" action="next" />

   </h:panelGrid>

  </h:form>

