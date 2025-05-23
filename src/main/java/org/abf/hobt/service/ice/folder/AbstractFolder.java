package org.abf.hobt.service.ice.folder;

import org.abf.hobt.service.ice.IDataTransferObject;
import org.abf.hobt.service.ice.entry.PartData;
import org.abf.hobt.service.ice.permission.AccessPermission;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a grouping, in this case of folders / collections
 *
 * @author Hector Plahar
 */
public abstract class AbstractFolder implements IDataTransferObject, Comparable<AbstractFolder> {

    private long id;
    private long creationTime;
    private ArrayList<AccessPermission> accessPermissions;
    private List<PartData> entries;

    public AbstractFolder() {
        entries = new LinkedList<>();
    }

    public AbstractFolder(long id) {
        this.id = id;
        this.entries = new LinkedList<>();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public ArrayList<AccessPermission> getAccessPermissions() {
        return accessPermissions;
    }

    public void setAccessPermissions(ArrayList<AccessPermission> accessPermissions) {
        this.accessPermissions = accessPermissions;
    }

    public List<PartData> getEntries() {
        return entries;
    }

    public void setEntries(List<PartData> entries) {
        this.entries = entries;
    }

    public int compareTo(AbstractFolder details) {
        return Long.compare(id, details.getId());
    }
}
