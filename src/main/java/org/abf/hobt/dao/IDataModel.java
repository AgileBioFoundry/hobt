package org.abf.hobt.dao;

import org.abf.hobt.service.ice.IDataTransferObject;

import java.io.Serializable;

/**
 * Interface for all Data Objects stored in the database
 *
 * @author Hector Plahar
 */
public interface IDataModel extends Serializable {

    IDataTransferObject toDataTransferObject();
}
