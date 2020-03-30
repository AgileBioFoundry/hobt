package org.abf.hobt.service.ice.search;

import org.abf.hobt.service.ice.IDataTransferObject;

/**
 * Represents the different kinds of indexes available
 *
 * @author Hector Plahar
 */
public enum IndexType implements IDataTransferObject {

    BLAST,
    LUCENE
}
