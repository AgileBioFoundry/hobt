package org.abf.hobt.tier;

import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.CriteriaDAO;
import org.abf.hobt.dao.hibernate.TierDAO;
import org.abf.hobt.dao.hibernate.TierRuleDAO;
import org.abf.hobt.dao.model.CriteriaModel;
import org.abf.hobt.dao.model.TierModel;
import org.abf.hobt.dao.model.TierRuleModel;
import org.abf.hobt.dto.Criteria;
import org.abf.hobt.tier.rule.RuleType;

import java.util.ArrayList;
import java.util.List;

public class Tiers {

    private final TierDAO dao;
    private final CriteriaDAO criteriaDAO;
    private final TierRuleDAO tierRuleDAO;

    public Tiers() {
        this.dao = DAOFactory.getTierDAO();
        this.criteriaDAO = DAOFactory.getCriteriaDAO();
        this.tierRuleDAO = DAOFactory.getTierRuleDAO();
    }

    public List<Tier> get(boolean includeCriteria) {
        List<TierModel> tierModels = this.dao.list();
        List<Tier> result = new ArrayList<>(tierModels.size());
        for (TierModel model : tierModels) {
            Tier tier = model.toDataTransferObject();
            if (includeCriteria) {
                for (CriteriaModel criteriaModel : model.getCriteria()) {
                    tier.getCriteria().add(criteriaModel.toDataTransferObject());
                }
            }
            result.add(tier);
        }
        return result;
    }

    public Tier create(Tier tier) {
        if (tier == null || StringUtils.isBlank(tier.getLabel()))
            throw new IllegalArgumentException("Invalid tier for saving");

        if (this.dao.getByName(tier.getLabel()).isPresent())
            throw new IllegalArgumentException("Tier with name '" + tier.getLabel() + "' already exists");

        TierModel model = new TierModel();
        model.setName(tier.getLabel());
        model.setIndex(tier.getIndex());

        return this.dao.create(model).toDataTransferObject();
    }

    public Tier update(long id, Tier update) {
        TierModel model = this.dao.get(id);
        if (model == null)
            throw new IllegalArgumentException("Cannot find tier with id: " + id);

        model.setIndex(update.getIndex());
        return this.dao.update(model).toDataTransferObject();
    }

    private TierModel getTierModel(long id) {
        TierModel model = this.dao.get(id);
        if (model == null)
            throw new IllegalArgumentException("Cannot find tier with id: " + id);
        return model;
    }

    public Criteria addCriteria(long id, Criteria criteria) {
        TierModel model = getTierModel(id);
        CriteriaModel criteriaModel = new CriteriaModel();
        criteriaModel.setLabel(criteria.getLabel());
        criteriaModel.setDescription(criteria.getDescription());
        criteriaModel.setTier(model);
        return this.criteriaDAO.create(criteriaModel).toDataTransferObject();
    }

    public List<Criteria> getCriteria(long id) {
        getTierModel(id);

        List<CriteriaModel> list = criteriaDAO.criteriaList();
        List<Criteria> result = new ArrayList<>();
        for (CriteriaModel criteriaModel : list)
            result.add(criteriaModel.toDataTransferObject());
        return result;
    }

    public Criteria updateTierCriteria(long id, long criteriaId, Criteria criteria) {
        getTierModel(id);

        // get criteria
        CriteriaModel criteriaModel = this.criteriaDAO.get(criteriaId);
        if (criteriaModel == null)
            throw new IllegalArgumentException("Cannot find criteria model with id: " + criteriaId);

        if (criteriaModel.getTier().getId() != id)
            throw new IllegalArgumentException("Tier for criteria model doesn't match");

        if (StringUtils.isBlank(criteria.getDescription()) && StringUtils.isBlank(criteria.getLabel()))
            throw new IllegalArgumentException("Cannot have description and label for criteria to be both blank");

        if (!StringUtils.isBlank(criteria.getDescription()))
            criteriaModel.setDescription(criteria.getDescription());

        if (!StringUtils.isBlank(criteria.getLabel()))
            criteriaModel.setLabel(criteria.getLabel());

        return this.criteriaDAO.update(criteriaModel).toDataTransferObject();
    }

    public Tier updateIndex(long tierId, int newIndex) {
        TierModel model = getTierModel(tierId);
        if (model.getIndex() == newIndex)
            return model.toDataTransferObject();

        model.setIndex(newIndex);
        return this.dao.update(model).toDataTransferObject();
    }

    public boolean deleteTierCriteria(long tierId, long criteriaId) {
        TierModel tierModel = getTierModel(tierId);

        CriteriaModel criteriaModel = this.criteriaDAO.get(criteriaId);
        if (criteriaModel == null)
            throw new IllegalArgumentException("Cannot find criteria model with id: " + criteriaId);

        if (criteriaModel.getTier().getId() != tierId)
            throw new IllegalArgumentException("Tier for criteria model doesn't match");

        criteriaModel.setTier(null);
        tierModel.getCriteria().remove(criteriaModel);

//        this.criteriaDAO.update(criteriaModel);
        DAOFactory.getTierDAO().update(tierModel);

//        this.criteriaDAO.delete(criteriaModel);
        return true;
    }

    public List<Rule> getRules(long tierId) {
        getTierModel(tierId);
        List<TierRuleModel> models = tierRuleDAO.getRules(tierId);
        List<Rule> rules = new ArrayList<>();
        for (TierRuleModel model : models)
            rules.add(model.toDataTransferObject());
        return rules;
    }

    public Rule addRule(long tierId, Rule rule) {
        TierModel tierModel = getTierModel(tierId);

        TierRuleModel tierRuleModel = new TierRuleModel();
        if (rule.getType() == null)
            rule.setType(RuleType.SIMPLE);

        tierRuleModel.setType(rule.getType());
        tierRuleModel.setPercentage(rule.getPercentage());
        tierRuleModel.setTier(tierModel);

        tierRuleModel = this.tierRuleDAO.create(tierRuleModel);
        return tierRuleModel.toDataTransferObject();
    }
}
