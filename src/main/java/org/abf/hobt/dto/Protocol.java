package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;

public class Protocol implements IDataTransferObject {
    private int id;
    private String guid;
    private String title;
    private String doi;
    private String uri;
    private int published_on;
    private int created_on;
    private ProtocolImage image;
    private ProtocolUser creator;
    private String url;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getPublished_on() {
        return published_on;
    }

    public void setPublished_on(int published_on) {
        this.published_on = published_on;
    }

    public int getCreated_on() {
        return created_on;
    }

    public void setCreated_on(int created_on) {
        this.created_on = created_on;
    }

    public ProtocolImage getImage() {
        return image;
    }

    public void setImage(ProtocolImage image) {
        this.image = image;
    }

    public ProtocolUser getCreator() {
        return creator;
    }

    public void setCreator(ProtocolUser creator) {
        this.creator = creator;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
