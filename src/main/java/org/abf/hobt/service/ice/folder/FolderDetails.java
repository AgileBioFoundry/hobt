package org.abf.hobt.service.ice.folder;

/**
 * Folder Transfer Object
 *
 * @author Hector Plahar
 */

public class FolderDetails extends AbstractFolder {

    private String folderName;
    private long count;
    private String description;
    private boolean canEdit;
    private FolderDetails parent;

    public FolderDetails() {
        super();
    }

    public String getName() {
        return this.folderName;
    }

    public void setName(String name) {
        this.folderName = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FolderDetails getParent() {
        return parent;
    }

    public void setParent(FolderDetails parent) {
        this.parent = parent;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
}
