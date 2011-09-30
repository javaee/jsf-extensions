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

package javajsf.demo.boundary;

import gms.demo.model.GroupId;
import gms.demo.model.Location;
import gms.demo.model.MemberInfo;
import javajsf.demo.client.MirrorClient;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the service to access information
 * about the current members' locations. For demonstration
 * purposes, this stores the state in memory. For the put
 * and delete master methods, this class acts like a ConcurrentMap.
 *
 * By putting this logic in a separate class from MasterResource,
 * we can access it in arbitrary ways from the presentation layer.
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER) // the default
@Startup
@Singleton
public class DiscoveryService {

    /*
     * Impl details: am using container-managed concurrency, but we can
     * easily use bean-managed and use a concurrent map implementation.
     *
     * It's a little redundant here to use a list since a MemberInfo
     * contains a GroupId, but it's useful.
     * 
     * Info: http://download.oracle.com/javaee/6/tutorial/doc/gipvi.html
     */
    private Map<GroupId, MemberInfo> map;

    /*
     * Service used to replicate information.
     */
    MirrorClient client;

    /*
     * This is really only here in case we want to do any
     * mirroring or loading from a persistent store. For
     * now, we're all memory, all the time.
     */
    @PostConstruct
    void init() {
        map = new HashMap<GroupId, MemberInfo>();

        // hack to get some data in there
        GroupId gid1 = new GroupId("agroup", "a");
        GroupId gid2 = new GroupId("bgroup", "a");
        GroupId gid3 = new GroupId("bgroup", "b");
        Location loc = new Location("127.0.0.1", 8123, "smail");
        MemberInfo hack1 = new MemberInfo(gid1, loc, "member1", false);
        MemberInfo hack2 = new MemberInfo(gid2, loc, "member2", false);
        MemberInfo hack3 = new MemberInfo(gid3, loc, "member2", false);
        setMaster(hack1);
        setMaster(hack2);
        setMaster(hack3);
    }

    @PreDestroy
    void takeDown() {
        if (client != null){
            client.close();
        }
    }

    /**
     * Sets the client to which to replicate information. Setting the base
     * uri creates the REST client. Setting a new URI replaces the existing
     * client. When a new REST client is added, thie service will pass
     * the current information to it before returning.
     *
     * @param baseURI The URI of the backup service.
     */
    public void setMirrorURI(String baseURI) {
        client = new MirrorClient(baseURI);
    }

    /*
     * Gets the base URI of the backup service.
     *
     * @return The base URI of the mirror service, or null if none
     * is set.
     */
    public String getMirrorURI() {
        if (client != null) {
            return client.getBaseUri();
        }
        return null;
    }

    /**
     * Returns the current master for the given group, or null
     * if there is no master set.
     *
     * @param groupId The group whose master should be returned
     * @return The master for the given group or null.
     */
    @Lock(LockType.READ) // which is the default
    public MemberInfo getMaster(GroupId groupId) {
        MemberInfo retVal = map.get(groupId);
        if (retVal == null) {
            System.err.println(String.format(
                "No master found for group %s. Returning null.",
                groupId.toString()));
        }
        return retVal;
    }

    /**
     * Set the master for a given group id. If one already exists,
     * it is not overridden. The container is blocking concurrent
     * access to business methods during this call, so this is
     * an atomic op.
     *
     * Like ConcurrentMap#putIfAbsent, this method returns the
     * previous master or null if one was not present.
     *
     * @param memberInfo The member to set as the master of the group
     * contained in that master.
     * @return The member info representing the group master after this
     * operation completes.
     */
    @Lock(LockType.WRITE)
    public MemberInfo setMaster(MemberInfo memberInfo) {
        GroupId groupId = memberInfo.getGroupId();
        if (groupId == null) {
            throw new RuntimeException(String.format(
                "Cannot set member '%s' with null group id.",
                memberInfo));
        }
        MemberInfo retVal = map.get(groupId);
        if (retVal == null) {
            memberInfo.setMaster(true);
            System.err.println("Setting master: " + memberInfo);
            map.put(groupId, memberInfo);
        } else {
            memberInfo.setMaster(false);
        }
        return retVal;
    }
    
    /**
     * If the member info passed in is the current master, it
     * is removed and there will be no master for this group.
     * If the param does not match the current master, the master
     * is not removed.
     *
     * Like ConcurrentMap#remove, this method returns true if the
     * value was removed. Otherwise it returns false.
     *
     * @param memberInfo The master to delete.
     * @return True if the member info was removed as the master.
     */
    @Lock(LockType.WRITE)
    public boolean deleteMaster(MemberInfo memberInfo) {
        GroupId groupId = memberInfo.getGroupId();
        if (groupId == null) {
            throw new RuntimeException(String.format(
                "Cannot delete member '%s' with null group id.",
                memberInfo));
        }
        if (memberInfo.equals(map.get(groupId))) {
            System.err.println("Removing master: " + memberInfo);
            map.remove(groupId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Clear all the group master data.
     *
     * TODO: add @RolesAllowed here to limit access
     */
    @Lock(LockType.WRITE)
    public void removeAllMasters() {
        map.clear();
        System.err.println("Removed all masters: " + map.isEmpty());
    }

    /**
     * Return set of group masters. Since each MemberInfo
     * contains its GroupId, we don't need to use an entry set.
     *
     * @return A collection of the masters
     */
    @Lock(LockType.READ)
    public Collection<MemberInfo> getMasters() {
        System.err.println("Returning all members: " + map.values().size());
        return map.values();
    }

}
