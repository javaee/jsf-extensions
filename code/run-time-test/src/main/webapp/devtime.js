/*
 * $Id: devtime.js,v 1.5 2006/01/13 16:05:28 edburns Exp $
 */

/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * https://javaserverfaces.dev.java.net/CDDL.html or
 * legal/CDDLv1.0.txt.
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at legal/CDDLv1.0.txt.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * [Name of File] [ver.__] [Date]
 *
 * Copyright 2005 Sun Microsystems Inc. All Rights Reserved
 */

function extractParams(ajaxZone, element, originalScript, outProps, 
		       invocation) {

  var allHandlerStatements = null;
  var prunedHandlerStatements = null;
  var expI = 0, i = 0, j = 0;
  var pattern = null;
  var curStatement = null;
  var name = null, value = null;

  // Remove any form submit statements
  allHandlerStatements = originalScript.split(";");
  if (0 >= allHandlerStatements.length) {
    return;
  }
  prunedHandlerStatements = new Array();
  for (i = 0; i < allHandlerStatements.length; i++) {
    // If the current statement does not contain the submit...
    if (-1 == allHandlerStatements[i].search(".*submit[ ]*\([ ]*\)")) {
      // copy it to the prunedHandlerStatements.
      prunedHandlerStatements[j++] = allHandlerStatements[i];
    }
  }

  // Copy any name/value pair statements to the outProps associative array
  if (null == prunedHandlerStatements) {
    return;
  }
  for (i = 0; i < prunedHandlerStatements.length; i++) {
    // Hack: assume we're using the [''] syntax.  A more general
    // solution would discover this dynamically.
    if (-1 != (expI = prunedHandlerStatements[i].lastIndexOf("[\'"))) {
      curStatement = prunedHandlerStatements[i].substring(expI + 2);
      name = null;
      value = null;
      // Extract the parameter name.
      if (-1 != (expI = curStatement.indexOf("\']"))) {
        name = curStatement.substring(0, expI);
        // Extract the parameter value
        if (-1 != (expI = 
            prunedHandlerStatements[i].lastIndexOf("\.value"))){
          if (-1 != (expI = 
            prunedHandlerStatements[i].indexOf("=", expI))) {
            value = prunedHandlerStatements[i].substring(expI + 1);
	    value = dojo.string.trim(value);
            // strip off the leading and trailing ' if necessary
            if (null != value && "\'" == value.charAt(0)) {
              if ("\'" == value.charAt(value.length - 1)) {
                  value = value.substring(1, value.length - 1);
              }
            }
            // strip off the leading and trailing " if necessary
            if (null != value && '\"' == value.charAt(0)) {
              if ('\"' == value.charAt(value.length - 1)) {
                  value = value.substring(1, value.length - 1);
              }
            }

          }
        }
        if (null != name && null != value) {
          outProps[name] = value;
        }
      }
    }
  }    
}
