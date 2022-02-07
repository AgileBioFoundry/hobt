package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.host.publication.Publication;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PUBLICATION")
public class PublicationModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "publication_id")
    @SequenceGenerator(name = "publication_id", sequenceName = "publication_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "privileged")
    private Boolean privileged = Boolean.FALSE;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;


    @Override
    public Publication toDataTransferObject() {
        return new Publication();
    }
}
