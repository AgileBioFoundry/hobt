package org.abf.hobt.dao.model;

import jakarta.persistence.*;
import org.abf.hobt.cache.ElementCacheType;
import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.OrganismElementCache;

@Entity
@Table(name = "OrganismElementCache")
public class OrganismElementCacheModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "organism_element_id")
    @SequenceGenerator(name = "organism_element_id", sequenceName = "organism_element_id_seq", allocationSize = 1)
    private long id;

    @OneToOne
    @JoinColumn(name = "organism_id")
    private OrganismModel organism;

    @Enumerated(EnumType.STRING)
    private ElementCacheType type;

    @Column(name = "\"count\"")
    private long count;

    public long getId() {
        return id;
    }

    public OrganismModel getOrganism() {
        return organism;
    }

    public void setOrganism(OrganismModel organism) {
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

    @Override
    public OrganismElementCache toDataTransferObject() {
        OrganismElementCache cache = new OrganismElementCache();
        cache.setCount(this.count);
        cache.setId(this.id);
        cache.setType(this.type);
        cache.setOrganism(this.organism.toDataTransferObject());
        return cache;
    }
}
