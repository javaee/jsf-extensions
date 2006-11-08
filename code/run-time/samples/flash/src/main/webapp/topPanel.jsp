  <p>This series of pages illustrates the usage of the flash concept
  taken from <a target="_"
  href="http://api.rubyonrails.com/classes/ActionController/Flash.html">Ruby
  On Rails</a>.</p>

  <p>In JSF, the flash is exposed naturally via the new <a
  href="http://java.sun.com/products/jsp/reference/techart/unifiedEL.html">Unified
  Expression Language in Java EE 5</a>.  It is implemented via a custom
  <code>ELResolver</code> that introduces a new implicit object called
  "flash".  I considered calling it "dhhIsMyHero" but opted for the
  simpler "flash" instead.</p>

  <p>Using the flash is simple, and semantically identical to the way it
  works in Rails.  It's a Map.  Stuff you put in the Map will be
  accessible on the "next" view shown to user.  The Map will be cleared
  when the user has been shown the "next" view.</p>
