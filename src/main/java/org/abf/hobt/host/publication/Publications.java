package org.abf.hobt.host.publication;

import org.abf.hobt.common.ResultData;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.PublicationDAO;
import org.abf.hobt.dao.model.PublicationModel;

import java.util.Date;
import java.util.List;

public class Publications {

    private final PublicationDAO dao;

    public Publications() {
        this.dao = DAOFactory.getPublicationDAO();
    }

    public ResultData<Publication> retrieve(int offset, int limit, String sort, boolean asc) {
        List<PublicationModel> models = this.dao.list(sort, asc, offset, limit);
        ResultData<Publication> resultData = new ResultData<>();
        for (PublicationModel model : models) {
            resultData.getRequested().add(model.toDataTransferObject());
        }
        resultData.setAvailable(this.dao.listCount());
        return resultData;
    }

    public Publication create(String userId, Publication publication) {
        // todo :  validation and check permission
        PublicationModel publicationModel = new PublicationModel();
        publicationModel.setCreated(new Date());
        publicationModel.setAuthors(publication.getAuthors());
        publicationModel.setJournal(publication.getJournal());
        publicationModel.setTitle(publication.getTitle());
        publicationModel.setPrivileged(publication.isPrivileged());
        publicationModel.setLink(publication.getLink());
        publicationModel.setYear(publication.getYear());

        return this.dao.create(publicationModel).toDataTransferObject();
    }
}
