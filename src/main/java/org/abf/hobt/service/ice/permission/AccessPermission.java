package org.abf.hobt.service.ice.permission;

import org.abf.hobt.service.ice.IDataTransferObject;

public class AccessPermission implements IDataTransferObject {

    private long id;
    private Type type;
    private Article article;
    private long typeId;      // id for type of permission (entry or folder)
    private long articleId;   // id for article being acted on (group or account)
    private String display;   // account or group name

    public AccessPermission() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long id) {
        this.articleId = id;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public boolean isCanRead() {
        return type == Type.READ_ENTRY || type == Type.READ_FOLDER;
    }

    public boolean isCanWrite() {
        return type == Type.WRITE_ENTRY || type == Type.WRITE_FOLDER;
    }

    public boolean isEntry() {
        return type == Type.READ_ENTRY || type == Type.WRITE_ENTRY;
    }

    public boolean isFolder() {
        return type == Type.READ_FOLDER || type == Type.WRITE_FOLDER;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public enum Type implements IDataTransferObject {
        READ_ENTRY, WRITE_ENTRY, READ_FOLDER, WRITE_FOLDER
    }

    public enum Article implements IDataTransferObject {
        ACCOUNT, GROUP, GLOBAL
    }

    @Override
    public String toString() {
        String typeName = type == null ? "" : type.name();
        return typeName + " (" + typeId + ") for " + article.name() + "(" + articleId + ")";
    }
}
