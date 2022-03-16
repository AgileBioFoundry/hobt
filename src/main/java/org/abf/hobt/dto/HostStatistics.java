package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;

/**
 * Statistics about host organism (e.g. number of publications, number of experiments etc)
 *
 * @author Hector Plahar
 */
public class HostStatistics implements IDataTransferObject {

    private long publicationCount;

    public long getPublicationCount() {
        return publicationCount;
    }

    public void setPublicationCount(long publicationCount) {
        this.publicationCount = publicationCount;
    }
}
