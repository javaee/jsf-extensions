/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package gms.demo.service.presentation.rest;

import gms.demo.model.MemberInfo;
import gms.demo.model.GroupId;
import gms.demo.service.boundary.DiscoveryService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/*
 * The REST resource representing the master of a gms group.
 *
 * I've made this class a stateless entity so injection of the discovery
 * service works. The examples all work this way, but I thought it wasn't
 * supposed to be needed any more. So instances of this class will be
 * pooled, but each will be called from only one thread at a time.
 *
 * This class calls into DiscoveryService for the actual work.
 */
@Stateless
@Path("/master/{group}/{namespace}")
public class MasterResource {

    private static final CacheControl NO_CACHE_CONTROL = new CacheControl();

    static {
        NO_CACHE_CONTROL.setNoCache(true);
    }

    // IDEA says this is an error but it's working. Could be issue
    // with using the no-interface EJB view. Will figure it out
    // later -- until then am suppressing the warning.
    @SuppressWarnings({"EjbEnvironmentInspection"})
    @EJB
    DiscoveryService service;

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.TEXT_XML)
    public Response getMaster(@PathParam("group") String groupName,
        @PathParam("namespace") String nameSpace) {

        GroupId groupId = new GroupId(groupName, nameSpace);
        MemberInfo retVal = service.getMaster(groupId);
        if (retVal == null) {
            System.err.println("No such member info. Returning ");
            return Response.noContent().build();
        }

        return Response.ok(retVal).cacheControl(NO_CACHE_CONTROL).build();
    }

    @PUT
    @Consumes(MediaType.TEXT_XML)
    public Response putMaster(MemberInfo memberInfo) {
        Response resp;
        if (memberInfo != null) {
            // null returned means there was not a previous master
            if (service.setMaster(memberInfo) == null) {
                URI uri = uriInfo.getAbsolutePath();
                resp = Response.created(uri).build();
            } else {
                resp = Response.noContent().build();
            }
        } else {
            resp = Response.status(Response.Status.BAD_REQUEST).build();
        }
        return resp;
    }

    /*
     * For this method, we are only comparing the name of the
     * member passed in and not the location (the group is implicit).
     * So we use the Location from the original master, if any, to
     * create a new one to pass into the deleteMaster call.
     */
    @DELETE
    public Response deleteMaster(@PathParam("group") String groupName,
        @PathParam("namespace") String nameSpace) {

        GroupId groupId = new GroupId(groupName, nameSpace);
        String memberName = uriInfo.getQueryParameters().getFirst("name");
        MemberInfo original = service.getMaster(groupId);
        Response resp = Response.noContent().build();
        if (memberName == null || memberName.isEmpty()) {
            // we may want to handle this case as well and remove
            // no matter what
            System.err.println("Query param 'name' must be passed into delete" +
                " master method");
        } else if (original != null) {
            MemberInfo victim = new MemberInfo(groupId,
                original.getLocation(), memberName, true);
            if (service.deleteMaster(victim)) {
                resp = Response.ok().build();
            }
        }
        return resp;
    }

}
