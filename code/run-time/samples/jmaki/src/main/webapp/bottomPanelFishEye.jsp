<p>Click any of the pictures and notice the messages change in the
page.</p>

	<ol>

	  <li><p>Click on the first image from the left.
	  </p></li>

	  <li><p>In the "Message from ValueChangeListener" area of the
	  page, notice that a message appears showing the old and new
	  value of the selected index.</p></li>

	  <li><p>In the "Selected Person" message, notice that the
	  caption for the person is updated as well.</p></li>

	</ol>

<p>If you examine the source for <code>mainColumnFishEye.jsp</code>, you
will see that the jMaki widget has a <code>valueChangeListener</code>
attribute installed on it.  This is a JSF <code>MethodExpression</code>
that takes the following action.</p>

	<ol>

	  <li><p>Construct an informational message containing the old
	  and new values of the component.</p></li>

	  <li><p>Store that message in request scope.
	  </p></li>

	  <li><p>Clear the set of partial ids to be rendered.
	  </p></li>

	  <li><p>Add to the set of partial ids to be rendered the,
	  <code>fishEyeMessage</code>, the <code>personMessage</code>,
	  and the fisheye component itself.</p></li>

	</ol>

<p>When the view is partially re-rendered using Dynamic Faces, only the
components requested by the <code>ValueChangeListener</code> are
rendered.</p>


