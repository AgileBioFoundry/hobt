package org.abf.hobt.dto;

import org.abf.hobt.cache.ElementCacheType;
import org.abf.hobt.service.ice.IDataTransferObject;

public class OrganismElementCache implements IDataTransferObject {

    private long id;
    private Organism organism;
    private ElementCacheType type;
    private long count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Organism getOrganism() {
        return organism;
    }

    public void setOrganism(Organism organism) {
        this.organism = organism;
    }

    public ElementCacheType getType() {
        return type;
    }

    public void setType(ElementCacheType type) {
        this.type = type;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
