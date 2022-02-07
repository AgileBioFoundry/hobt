package org.abf.hobt.dao.hibernate;

import org.abf.hobt.dao.model.PublicationModel;

import java.util.List;

public class PublicationDAO extends HibernateRepository<PublicationModel> {

    @Override
    public PublicationModel get(long id) {
        return super.retrieve(PublicationModel.class, id);
    }

    public List<PublicationModel> list(String sort, boolean asc, int start, int limit) {
        return super.list(PublicationModel.class, sort, asc, start, limit);
    }

    public long listCount() {
        return super.listCount(PublicationModel.class);
    }
}
