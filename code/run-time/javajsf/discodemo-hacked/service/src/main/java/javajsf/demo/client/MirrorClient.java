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

package javajsf.demo.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import gms.demo.model.GroupId;
import gms.demo.model.MemberInfo;

import javax.ws.rs.core.MediaType;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * REST client for the service to mirror information to another instance.
 */
public class MirrorClient {

    // the jax-rs client resource
    private final Client client;
    private final WebResource baseResource;

    // master/group/namespace
    private static final String MASTER_TEMPLATE = "master/{0}/{1}";

    // query param expected by server for DELETE call
    private static final String MEMBER_NAME_PARAM = "name";

    /**
     * Creates the client used to access discovery registries.
     *
     * @param baseUri The base URI for the mirror client
     * @throws IllegalArgumentException if the passed in string is
     * empty or null.
     */
    public MirrorClient(String baseUri) {
        if (baseUri == null || baseUri.trim().isEmpty()) {
            throw new IllegalArgumentException("MirrorClient " +
                "constructor called with invalid uris string: " +
                baseUri);
        }
        System.out.println("Discovery regsistry URI: " + baseUri);
        client = Client.create(new DefaultClientConfig());
        client.setConnectTimeout(10 * 1000); // param in ms
        baseResource = client.resource(baseUri);
    }

    /*
     * Returns the base uri of the web resource.
     */
    public String getBaseUri() {
        return baseResource.getURI().toString();
    }

    // we may not need this method. mirror client is only used right now for
    // putting and deleting a master
//    public MemberInfo getMaster(GroupId gid) {
//        /*
//         * Implementation detail. As a reminder to self:
//         *
//         * UniformInterfaceException indicates that there was a repsonse,
//         * but it wasn't what we expected.
//         *
//         * ClientHandlerException indicates there was some problem
//         * getting a response from the server.
//         */
//        WebResource resource = baseResource.path(createResourcePath(gid));
//        try {
//            MemberInfo retVal =
//                resource.accept(MediaType.TEXT_XML).get(MemberInfo.class);
//            if (retVal != null) {
//                System.out.println(String.format(
//                    "GET call with %s returned %s",
//                    gid, retVal));
//                return retVal;
//            } else {
//                // just here for learning purposes
//                System.err.println(
//                    "If you see this message, tell Bobby. 01");
//            }
//        } catch (UniformInterfaceException uie) {
//            ClientResponse resp = uie.getResponse();
//
//            // this will be the case when there is no master for the group
//            if (Status.NO_CONTENT == resp.getClientResponseStatus()) {
//                System.out.println(String.format(
//                    "Registry returned NO_CONTENT for group %s", gid));
//                return null;
//            }
//            System.err.println(String.format(
//                "MirrorClient encountered an error with GET call: %s",
//                uie.toString()));
//        } catch (ClientHandlerException che) {
//            System.err.println(String.format(
//                "Client handler error with GET call: %s", che.getMessage()));
//        }
//
//        /*
//         * Should only get here if no service returned usable data (either
//         * a master or NO_CONTENT).
//         */
//        throw new RuntimeException("Could not connect to any service.");
//    }

    /*
     * This could throw ClientHandlerException or UniformInterfaceException,
     * both runtime exceptions. We could declare that this throws Exception,
     * but the calling code can let any exception abort making changes in
     * memory.
     */
    public boolean putMaster(MemberInfo mi) {
        WebResource resource = baseResource.path(
            createResourcePath(mi.getGroupId()));
        ClientResponse resp = resource.type(MediaType.TEXT_XML).put(
            ClientResponse.class, mi);
        final Status status = resp.getClientResponseStatus();
        System.out.println(String.format(
            "PUT call returned %s status for %s", status, mi));
        if (Status.CREATED == status) {
            mi.setMaster(true);
            return true;
        }
        mi.setMaster(false);
        return false;
    }

    /*
     * This could throw ClientHandlerException or UniformInterfaceException,
     * both runtime exceptions. We could declare that this throws Exception,
     * but the calling code can let any exception abort making changes in
     * memory.
     */
    public boolean deleteMaster(MemberInfo mi) {
        WebResource resource = baseResource.path(
            createResourcePath(mi.getGroupId()));
        ClientResponse resp = resource.queryParam(MEMBER_NAME_PARAM,
            mi.getMemberName()).delete(ClientResponse.class);
        final Status status = resp.getClientResponseStatus();
        System.out.println(String.format(
            "DELETE call returned %s status for %s", status, mi));
        if (Status.OK == status) {
            mi.setMaster(false);
            return true;
        }
        return false;
    }

    /**
     * Don't forget this when done.
     */
    public void close() {
        client.destroy();
    }

    /*
     * We do our integrity checking here. Throws clause is to remind
     * calling methods to document the exception.
     */
    private String createResourcePath(GroupId gId)
        throws IllegalArgumentException {

        if (gId == null) {
            throw new IllegalArgumentException(
                "GroupId parameter cannot be null.");
        } else if (gId.getGroupName() == null || gId.getNameSpace() == null) {
            throw new IllegalArgumentException(String.format(
                "GroupId %s contains non fields", gId));
        }
        return MessageFormat.format(MASTER_TEMPLATE, gId.getGroupName(),
            gId.getNameSpace());
    }

}
