<p>Browse through a data set, using the jMaki scriptaculous editor
component to edit the customer name via AJAX.</p>

	<ol>

	  <li><p>Use the <a
	  href="http://bpcatalog.dev.java.net">Blueprints Solutions
	  Catalog Scroller Component</a> (formerly delivered in the Sun
	  JSF Implementation bundle) to scroll through the dataset via
	  AJAX.</p>  

          <p>Note how the page refreshes without the browser refreshing
          the whole page.</p></li>

	  <li><p>Click on any one of the <code>Customer Name</code>
	  cells.  The cell will change into an in-place editor.  Change
	  the value and press OK.  The value is submitted via AJAX.
	  This is a great example of context sensitive AJAX, something
	  that is only possible when you combine the power of JSF with
	  AJAX.  Think about it.  The instance of the JSF DataTable
	  component in this page really only has four children, one for
	  each column.  When rendering, the values are "stamped out" for
	  each row.  Therefore, the indivual cells only have a value
	  when the DataTable is controlling the rendering process.  This
	  example makes use of the <code>invokeOnComponent</code>
	  method, new in JSF 1.2, which is over-ridden by
	  <code>UIData</code> to enable taking action on a single
	  component within the table, while preserving the context in
	  which that component is expecting to exist.</p></li>

	</ol>

