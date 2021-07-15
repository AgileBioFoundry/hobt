package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.service.ice.IDataTransferObject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "OrganismAudit")
public class OrganismAuditModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "organism_audit_id")
    @SequenceGenerator(name = "organism_audit_id", sequenceName = "organism_audit_id_seq", allocationSize = 1)
    private long id;

    @OneToOne
    @JoinColumn(name = "organism_id", nullable = false, updatable = false)
    private OrganismModel organism;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private AccountModel account;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    @Override
    public IDataTransferObject toDataTransferObject() {
        return null;
    }
}
