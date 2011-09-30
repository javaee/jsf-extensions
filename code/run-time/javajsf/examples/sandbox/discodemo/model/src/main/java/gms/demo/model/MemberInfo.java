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

package gms.demo.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents information about a GMS member.
 */
@XmlRootElement
public class MemberInfo {

    private GroupId groupId;
    private Location location;
    private String memberName;
    private boolean isMaster;

    /* generated below here */

    public MemberInfo() {}

    public MemberInfo(GroupId groupId, Location location,
        String memberName, boolean master) {
        
        this.groupId = groupId;
        this.location = location;
        this.memberName = memberName;
        isMaster = master;
    }

    public GroupId getGroupId() {
        return groupId;
    }

    public void setGroupId(GroupId groupId) {
        this.groupId = groupId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean master) {
        isMaster = master;
    }

    @Override
    public String toString() {
        return "MemberInfo{" +
            "groupId=" + groupId +
            ", location=" + location +
            ", memberName='" + memberName + '\'' +
            ", isMaster=" + isMaster +
            '}';
    }

    @Override
    /**
     * Overridden equals method includes GroupId, Location,
     * and memberName fields. Does not include isMaster.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberInfo that = (MemberInfo) o;

        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null)
            return false;
        if (location != null ? !location.equals(that.location) : that.location != null)
            return false;
        if (memberName != null ? !memberName.equals(that.memberName) : that.memberName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (memberName != null ? memberName.hashCode() : 0);
        return result;
    }
}
