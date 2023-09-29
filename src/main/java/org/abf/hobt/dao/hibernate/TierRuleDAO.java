package org.abf.hobt.dao.hibernate;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.abf.hobt.dao.model.TierModel;
import org.abf.hobt.dao.model.TierRuleModel;

import java.util.List;

public class TierRuleDAO extends HibernateRepository<TierRuleModel> {

    @Override
    public TierRuleModel get(long id) {
        return super.retrieve(TierRuleModel.class, id);
    }

    public List<TierRuleModel> getRules(long tierId) {
        CriteriaQuery<TierRuleModel> criteriaQuery = getBuilder().createQuery(TierRuleModel.class);
        Root<TierRuleModel> from = criteriaQuery.from(TierRuleModel.class);
        Join<TierRuleModel, TierModel> model = from.join("tier");
        criteriaQuery.where(getBuilder().equal(model.get("id"), tierId));
        return currentSession().createQuery(criteriaQuery).list();
    }
}
