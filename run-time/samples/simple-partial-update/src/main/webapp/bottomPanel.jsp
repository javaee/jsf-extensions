
<p><code>TestBean</code> is a request scoped managed bean.  The buttons
below have an <code>actionListener</code> that changes the value of this
property to show a time stamp, the lifecycle phase in which the event
was delivered, and the id of the element that is the source of the
event.  Recall from core JSF behavior that an input field with
<code>immediate == true</code> will have its validations performed
during the <code>APPLY_REQUEST VALUES</code> phase.  An input field
without <code>immediate</code> set will have its validations performed
during the <code>PROCESS_VALIDATIONS</code> phase.  A button with
<code>immediate == true</code> will bypass validations entirely, whereas
a button without <code>immediate</code> set will have the validations
performed as normal.
</p>

	<ol>

	  <li><p>Click the <b>submit</b> button.  The page should
	  refresh via Ajax and re-render with a validation message in
	  the <code>Messages</code> area.</p></li>

	  <li><p>Click the <b>submit immediate</b> button.  The page
	  should refresh via Ajax and re-render with an informational
	  value in the first row of the table.  Note that the message
	  indicates the event was delivered during the <code>APPLY
	  REQUEST VALUES</code> lifecycle phase.  The validation message
	  should disappear, because this button is marked as immediate
	  via Ajax.</p></li>

	  <li><p>Enter values in both text fields and again click
	  <b>submit</b>.  Note the event is delivered during the
	  <code>INVOKE APPLICATION</code> lifecycle phase and no
	  validation message appears.</p></li>

	  <li><p>Again click the <b>submit immediate</b> value.  Note
	  again that the event is delivered during <code>APPLY REQUEST
	  VALUES</code> and that the values are cleared. </p></li>

	</ol>

