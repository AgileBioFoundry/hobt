package org.abf.hobt.account;

import org.abf.hobt.service.ice.IDataTransferObject;

/**
 * @author Hector Plahar
 */
public enum AccountRole implements IDataTransferObject {

    RESEARCHER, // this is the default account type
    DIVA_TEAM,
    PRINCIPAL_INVESTIGATOR,
    ADMINISTRATOR,
}
