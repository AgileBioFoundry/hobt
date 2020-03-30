package org.abf.hobt.service.ice.entry;

import org.abf.hobt.service.ice.IDataTransferObject;
import org.abf.hobt.service.ice.folder.FolderDetails;

import java.util.ArrayList;

/**
 * Represents entry selection in a specific context and by type
 * (e.g. select all plasmids from a search result)
 *
 * @author Hector Plahar
 */
public class EntrySelection implements IDataTransferObject {

    private ArrayList<FolderDetails> destination;   // destination for entry selection
    private ArrayList<Long> entries;                // if no context, then ad hoc selection
    private EntrySelectionType selectionType;

    public EntrySelection() {
        entries = new ArrayList<>();
        destination = new ArrayList<>();
    }

    public ArrayList<FolderDetails> getDestination() {
        return destination;
    }

    public ArrayList<Long> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Long> entries) {
        this.entries = entries;
    }

    public EntrySelectionType getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(EntrySelectionType selectionType) {
        this.selectionType = selectionType;
    }
}
