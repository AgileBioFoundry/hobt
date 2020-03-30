package org.abf.hobt.service.ice.entry;

import org.abf.hobt.service.ice.IDataTransferObject;

public class StrainData implements IDataTransferObject {

    private static final long serialVersionUID = 1L;

    private String host;
    private String genotypePhenotype;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getGenotypePhenotype() {
        return genotypePhenotype;
    }

    public void setGenotypePhenotype(String genotypePhenotype) {
        this.genotypePhenotype = genotypePhenotype;
    }
}
