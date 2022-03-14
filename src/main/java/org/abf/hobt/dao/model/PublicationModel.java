package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.host.publication.Publication;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "PUBLICATION")
public class PublicationModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "publication_id")
    @SequenceGenerator(name = "publication_id", sequenceName = "publication_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "authors", nullable = false, length = 512)
    private String authors;

    @Column(name = "\"year\"", nullable = false)
    private String year;

    @Column(name = "title", nullable = false, length = 1024)
    private String title;

    @Column(name = "journal", nullable = false, length = 512)
    private String journal;

    @Column(name = "privileged")
    private Boolean privileged = Boolean.FALSE;

    @Column(name = "link", nullable = false, length = 2048)
    private String link;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "publication_organisms", joinColumns = @JoinColumn(name = "publication_id"),
        inverseJoinColumns = @JoinColumn(name = "organism_id"))
    private final Set<OrganismModel> organisms = new LinkedHashSet<>();

    public long getId() {
        return id;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public Boolean getPrivileged() {
        return privileged;
    }

    public void setPrivileged(Boolean privileged) {
        this.privileged = privileged;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Set<OrganismModel> getOrganisms() {
        return organisms;
    }

    @Override
    public Publication toDataTransferObject() {
        Publication publication = new Publication();
        publication.setId(this.id);
        publication.setAuthors(this.authors);
        publication.setYear(this.year);
        publication.setJournal(this.journal);
        publication.setLink(this.link);
        publication.setCreated(this.created.getTime());
        publication.setPrivileged(this.privileged);
        return publication;
    }
}
