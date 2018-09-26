<%--

    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

    Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.

    The contents of this file are subject to the terms of either the GNU
    General Public License Version 2 only ("GPL") or the Common Development
    and Distribution License("CDDL") (collectively, the "License").  You
    may not use this file except in compliance with the License.  You can
    obtain a copy of the License at
    https://oss.oracle.com/licenses/CDDL+GPL-1.1
    or LICENSE.txt.  See the License for the specific
    language governing permissions and limitations under the License.

    When distributing the software, include this License Header Notice in each
    file and include the License file at LICENSE.txt.

    GPL Classpath Exception:
    Oracle designates this particular file as subject to the "Classpath"
    exception as provided by Oracle in the GPL Version 2 section of the License
    file that accompanied this code.

    Modifications:
    If applicable, add the following below the License Header, with the fields
    enclosed by brackets [] replaced by your own identifying information:
    "Portions Copyright [year] [name of copyright owner]"

    Contributor(s):
    If you wish your version of this file to be governed by only the CDDL or
    only the GPL Version 2, indicate your decision by adding "[Contributor]
    elects to include this software in this distribution under the [CDDL or GPL
    Version 2] license."  If you don't indicate a single choice of license, a
    recipient has the option to distribute your version of this file under
    either the CDDL, the GPL Version 2 or to extend the choice of license to
    its licensees as provided above.  However, if you add GPL Version 2 code
    and therefore, elected the GPL Version 2 license, then the option applies
    only if the new code is made subject to such option by the copyright
    holder.

--%>

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
