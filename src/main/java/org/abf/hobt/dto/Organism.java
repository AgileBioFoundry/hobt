package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;
import org.abf.hobt.tier.Tier;

import java.util.List;

public class Organism implements IDataTransferObject {

    private long id;
    private String name;
    private String phylum;
    private long created;
    private long updated;
    private String userId;
    private Tier tier;
    private List<OrganismAttribute> attributes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhylum() {
        return phylum;
    }

    public void setPhylum(String phylum) {
        this.phylum = phylum;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public List<OrganismAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<OrganismAttribute> attributes) {
        this.attributes = attributes;
    }
}
