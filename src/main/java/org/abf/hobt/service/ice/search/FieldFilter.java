package org.abf.hobt.service.ice.search;

import org.abf.hobt.service.ice.IDataTransferObject;
import org.abf.hobt.service.ice.entry.EntryField;

/**
 * Filter a specified entry field
 *
 * @author Hector Plahar
 */
public class FieldFilter implements IDataTransferObject {

    private EntryField field;
    private String filter;

    public EntryField getField() {
        return field;
    }

    public void setField(EntryField field) {
        this.field = field;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
