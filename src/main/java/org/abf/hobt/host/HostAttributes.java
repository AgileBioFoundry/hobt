package org.abf.hobt.host;

import org.abf.hobt.account.AccountAuthorization;
import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.OrganismAttributeDAO;
import org.abf.hobt.dao.hibernate.OrganismAttributeValueDAO;
import org.abf.hobt.dao.model.OrganismAttributeModel;
import org.abf.hobt.dao.model.OrganismAttributeValueModel;
import org.abf.hobt.dao.model.OrganismModel;
import org.abf.hobt.dto.Organism;
import org.abf.hobt.dto.OrganismAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HostAttributes {

    private final OrganismAttributeDAO dao;
    private final OrganismModel organismModel;
    private final OrganismAttributeValueDAO valueDAO;


    public HostAttributes(long hostId) {
        this.dao = DAOFactory.getOrganismAttributeDAO();
        this.organismModel = DAOFactory.getOrganismDAO().get(hostId);
        if (this.organismModel == null)
            throw new IllegalArgumentException("Invalid host " + hostId);
        this.valueDAO = DAOFactory.getOrganismAttributeValueDAO();
    }

    public List<OrganismAttribute> get(String userId) {
        // todo : check write privileges
        // get attributes applicable to all hosts or specified host
        List<OrganismAttribute> results = new ArrayList<>();
        List<OrganismAttributeModel> list = dao.pageAttributes(0, Integer.MAX_VALUE, false, "id");
        for (OrganismAttributeModel model : list) {
            if (model.getDisabled() || model.getType() == null)
                continue;

            if ((model.getAllOrganisms() != null && model.getAllOrganisms())) {
                results.add(model.toDataTransferObject());
                continue;
            }

            if (model.getOrganisms().contains(organismModel))
                results.add(model.toDataTransferObject());
        }
        return results;
    }

    public boolean delete(String userId, long attributeId) {
        // only admins can delete
        AccountAuthorization accountAuthorization = new AccountAuthorization();
        accountAuthorization.expectAdmin(userId);

        OrganismAttributeModel model = dao.get(attributeId);
        if (model == null)
            return false;

        OrganismAttributeValueDAO attributeValueDAO = DAOFactory.getOrganismAttributeValueDAO();
        Optional<OrganismAttributeValueModel> optional = attributeValueDAO.getByAttributeAndHost(this.organismModel, model);
        optional.ifPresent(attributeValueDAO::delete);

        dao.delete(model);
        return true;
    }

    public void update(Organism organism) {
        if (organism.getAttributes() == null)
            return;


        for (OrganismAttribute attribute : organism.getAttributes()) {
            if (StringUtils.isBlank(attribute.getValue()))
                continue;

            OrganismAttributeModel model = dao.get(attribute.getId());
            if (model == null)
                continue;

            // get value
            Optional<OrganismAttributeValueModel> optional = valueDAO.getByAttributeAndHost(this.organismModel, model);
            if (optional.isPresent()) {
                // update
                OrganismAttributeValueModel valueModel = optional.get();
                valueModel.setValue(attribute.getValue());
                valueDAO.update(valueModel);
            } else {
                // create new
                OrganismAttributeValueModel valueModel = new OrganismAttributeValueModel();
                valueModel.setOrganism(this.organismModel);
                valueModel.setValue(attribute.getValue());
                valueModel.setOrganismAttribute(model);
                valueDAO.create(valueModel);
            }
        }
    }

    public List<OrganismAttribute> getValues() {
        List<OrganismAttributeValueModel> list = this.valueDAO.getByHost(this.organismModel);
        List<OrganismAttribute> result = new ArrayList<>();
        for (OrganismAttributeValueModel model : list) {
            // note that the id in this case is the id of the attribute, note the value
            result.add(model.toDataTransferObject());
        }
        return result;
    }
}
