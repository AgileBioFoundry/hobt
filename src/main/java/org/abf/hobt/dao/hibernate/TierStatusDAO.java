package org.abf.hobt.dao.hibernate;

import org.abf.hobt.dao.model.TierStatusModel;

public class TierStatusDAO extends HibernateRepository<TierStatusModel> {

    @Override
    public TierStatusModel get(long id) {
        return super.retrieve(TierStatusModel.class, id);
    }

//    public TierStatusModel getForOrganism(long organismId, long hostId) {
//        try {
//            CriteriaQuery<TierStatusModel> query = getBuilder().createQuery(TierStatusModel.class);
//            Root<TierStatusModel> from = query.from(TierStatusModel.class);
//            Join<TierStatusModel, OrganismModel> organisms = from.join("organisms");
////            if (privilegedOnly == null) {
////                query.where(getBuilder().equal(organisms.get("id"), organism.getId()));
////            } else {
////                query.where(getBuilder().equal(organisms.get("id"), organism.getId()),
////                        getBuilder().equal(from.get("privileged"), privilegedOnly));
////            }
////            query.orderBy(asc ? getBuilder().asc(from.get(sort)) : getBuilder().desc(from.get(sort)));
////            return currentSession().createQuery(query).setMaxResults(limit).setFirstResult(start).list();
//        } catch (HibernateException e) {
//            Logger.error(e);
//            throw new DataAccessException(e);
//        }
//    }
}
