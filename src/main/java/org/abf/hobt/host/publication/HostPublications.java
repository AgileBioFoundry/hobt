package org.abf.hobt.host.publication;

import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.PublicationDAO;

import java.util.ArrayList;
import java.util.List;

public class HostPublications {

    private final long hostId;
    private final String userId;
    private final PublicationDAO dao;

    public HostPublications(long hostId, String userId) {
        this.hostId = hostId;
        this.userId = userId;
        this.dao = DAOFactory.getPublicationDAO();
    }

    public List<Publication> list() {
        return new ArrayList<>();
    }

    public Publication create(Publication publication) {
        return publication;
    }
}
