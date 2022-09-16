package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;

/**
 * Statistics about host organism (e.g. number of publications, number of experiments etc)
 *
 * @author Hector Plahar
 */
public class HostStatistics implements IDataTransferObject {

    private long publicationCount;
    private long partCount;
    private long strainCount;
    private long protocolCount;
    private long experimentCount;
    private long attributesCount;

    public long getPartCount() {
        return partCount;
    }

    public void setPartCount(long partCount) {
        this.partCount = partCount;
    }

    public long getStrainCount() {
        return strainCount;
    }

    public void setStrainCount(long strainCount) {
        this.strainCount = strainCount;
    }

    public long getProtocolCount() {
        return protocolCount;
    }

    public void setProtocolCount(long protocolCount) {
        this.protocolCount = protocolCount;
    }

    public long getExperimentCount() {
        return experimentCount;
    }

    public void setExperimentCount(long experimentCount) {
        this.experimentCount = experimentCount;
    }

    public long getPublicationCount() {
        return publicationCount;
    }

    public void setPublicationCount(long publicationCount) {
        this.publicationCount = publicationCount;
    }

    public long getAttributesCount() {
        return attributesCount;
    }

    public void setAttributesCount(long attributesCount) {
        this.attributesCount = attributesCount;
    }
}
