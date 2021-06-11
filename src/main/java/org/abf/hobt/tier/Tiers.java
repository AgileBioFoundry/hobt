package org.abf.hobt.tier;

import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.TierDAO;
import org.abf.hobt.dao.model.TierModel;

import java.util.ArrayList;
import java.util.List;

public class Tiers {

    private final TierDAO dao;

    public Tiers() {
        this.dao = DAOFactory.getTierDAO();
    }

    public List<Tier> get() {
        List<TierModel> tierModels = this.dao.list();
        List<Tier> result = new ArrayList<>(tierModels.size());
        for (TierModel model : tierModels)
            result.add(model.toDataTransferObject());
        return result;
    }

    public Tier create(Tier tier) {
        if (tier == null || StringUtils.isBlank(tier.getLabel()))
            throw new IllegalArgumentException("Invalid tier for saving");

        if (this.dao.getByName(tier.getLabel()).isPresent())
            throw new IllegalArgumentException("Tier with name '" + tier.getLabel() + "' already exists");

        TierModel model = new TierModel();
        model.setName(tier.getLabel());

        return this.dao.create(model).toDataTransferObject();
    }
}
