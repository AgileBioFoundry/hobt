package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.Setting;

import javax.persistence.*;

/**
 * @author Hector Plahar
 */
@Entity
@Table(name = "CONFIGURATION")
public class ConfigurationModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "configuration_id")
    @SequenceGenerator(name = "configuration_id", sequenceName = "configuration_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "key", unique = true)
    private String key;

    @Column(name = "value")
    private String value;

    public long getId() {
        return this.id;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Setting toDataTransferObject() {
        Setting setting = new Setting();
        setting.setKey(this.key);
        setting.setValue(this.value);
        return setting;
    }
}
