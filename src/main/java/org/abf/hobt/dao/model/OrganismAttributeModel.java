package org.abf.hobt.dao.model;

import org.abf.hobt.attribute.AttributeType;
import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.OrganismAttribute;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "OrganismAttribute")
@SequenceGenerator(name = "organism_attribute_id", sequenceName = "organism_attribute_id_seq", allocationSize = 1)
public class OrganismAttributeModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "organism_attribute_id")
    private long id;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private AccountModel account;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    // unique across same entry types for non-disabled fields
    @Column(name = "\"label\"")
    private String label;

    // type of field (e.g. multi choice etc)
    @Enumerated(EnumType.STRING)
    private AttributeType type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "attribute_organisms", joinColumns = @JoinColumn(name = "attribute_id"),
        inverseJoinColumns = @JoinColumn(name = "organism_id"))
    private final Set<OrganismModel> organisms = new LinkedHashSet<>();

    @Column(name = "required")
    private Boolean required = Boolean.FALSE;

    @Column(name = "disabled")
    private Boolean disabled = Boolean.FALSE;

    @Column(name = "all_organisms")
    private Boolean allOrganisms = Boolean.FALSE;

    /**
     * Options for attribute type "Multi_Choice"
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrganismAttributeOptionModel> options = new ArrayList<>();

    public long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public Set<OrganismModel> getOrganisms() {
        return organisms;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getAllOrganisms() {
        return allOrganisms;
    }

    public void setAllOrganisms(Boolean allOrganisms) {
        this.allOrganisms = allOrganisms;
    }

    public List<OrganismAttributeOptionModel> getOptions() {
        return options;
    }

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public OrganismAttribute toDataTransferObject() {
        OrganismAttribute attribute = new OrganismAttribute();
        attribute.setId(this.id);
        attribute.setLabel(this.label);
        attribute.setType(this.type);
        attribute.setAllOrganisms(this.allOrganisms);
        for (OrganismModel model : this.organisms)
            attribute.getHosts().add(model.toDataTransferObject());
        return attribute;
    }
}
