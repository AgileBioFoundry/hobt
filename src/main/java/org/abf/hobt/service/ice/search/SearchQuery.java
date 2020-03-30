package org.abf.hobt.service.ice.search;

import org.abf.hobt.service.ice.IDataTransferObject;
import org.abf.hobt.service.ice.entry.BioSafetyOption;
import org.abf.hobt.service.ice.entry.EntryField;
import org.abf.hobt.service.ice.entry.EntryType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hector Plahar
 */
public class SearchQuery implements IDataTransferObject {

    public static final long serialVersionUID = 1L;

    private boolean isAnnotation;
    private String queryString;
    private BlastQuery blastQuery;
    private BioSafetyOption bioSafetyOption;
    private ArrayList<EntryType> entryTypes;
    private Parameters parameters;
    private ArrayList<FieldFilter> fieldFilters;

    /**
     * set the query default values
     */
    public SearchQuery() {
        entryTypes = null;
        parameters = new Parameters();
    }

    public boolean hasBlastQuery() {
        return blastQuery != null && blastQuery.getSequence() != null && !blastQuery.getSequence().isEmpty();
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public boolean isAnnotation() {
        return isAnnotation;
    }

    public void setIsAnnotation(boolean isAnnotation) {
        this.isAnnotation = isAnnotation;
    }

    public BlastQuery getBlastQuery() {
        return blastQuery;
    }

    public void setBlastQuery(BlastQuery blastQuery) {
        this.blastQuery = blastQuery;
    }

    public BioSafetyOption getBioSafetyOption() {
        return bioSafetyOption;
    }

    public void setBioSafetyOption(BioSafetyOption bioSafetyOption) {
        this.bioSafetyOption = bioSafetyOption;
    }

    public ArrayList<EntryType> getEntryTypes() {
        return entryTypes;
    }

    public void setEntryTypes(List<EntryType> entryTypes) {
        this.entryTypes = new ArrayList<>();
        this.entryTypes.addAll(entryTypes);
    }

    public ArrayList<FieldFilter> getFieldFilters() {
        return fieldFilters;
    }

    public void setFieldFilters(ArrayList<FieldFilter> fieldFilters) {
        this.fieldFilters = fieldFilters;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public static class Parameters implements IDataTransferObject {

        public static final long serialVersionUID = 1L;

        private EntryField sortField;
        private boolean sortAscending;
        private int start;
        private int retrieveCount;
        private boolean hasSequence;
        private boolean hasAttachment;
        private boolean hasSample;

        public Parameters() {
            start = 0;
            retrieveCount = 15;
            sortField = EntryField.RELEVANCE;
            sortAscending = false;
        }

        public boolean getHasSequence() {
            return hasSequence;
        }

        public void setHasSequence(boolean hasSequence) {
            this.hasSequence = hasSequence;
        }

        public boolean getHasAttachment() {
            return hasAttachment;
        }

        public void setHasAttachment(boolean hasAttachment) {
            this.hasAttachment = hasAttachment;
        }

        public boolean getHasSample() {
            return hasSample;
        }

        public void setHasSample(boolean hasSample) {
            this.hasSample = hasSample;
        }

        public EntryField getSortField() {
            return sortField;
        }

        public void setSortField(EntryField sortField) {
            this.sortField = sortField;
        }

        public boolean isSortAscending() {
            return sortAscending;
        }

        public void setSortAscending(boolean sortAscending) {
            this.sortAscending = sortAscending;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getRetrieveCount() {
            return retrieveCount;
        }

        public void setRetrieveCount(int retrieveCount) {
            this.retrieveCount = retrieveCount;
        }
    }
}
