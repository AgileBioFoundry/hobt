package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.service.ice.IDataTransferObject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ORGANISM")
public class OrganismModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "organism_id")
    @SequenceGenerator(name = "organism_id", sequenceName = "organism_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "name", length = 125, nullable = false)
    private String name;

    @Column(name = "name", length = 125, nullable = false)
    private String phylum;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date creationTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private Date lastUpdateTime;

    @Override
    public IDataTransferObject toDataTransferObject() {
        return null;
    }
}
