package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;

/**
 * DTO for {@link org.abf.hobt.dao.model.OrganismCriteriaModel}
 *
 * @author Hector Plahar
 */
public class OrganismCriteria implements IDataTransferObject {

    private long id;
    private Organism organism;
    private Criteria criteria;

}
