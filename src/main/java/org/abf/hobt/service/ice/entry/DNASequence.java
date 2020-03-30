package org.abf.hobt.service.ice.entry;

import org.abf.hobt.service.ice.IDataTransferObject;

/**
 * Value object to hold DNA sequence.
 *
 * @author Hector Plahar
 */
public class DNASequence implements IDataTransferObject {

    private static final long serialVersionUID = 1L;

    private String sequence;

    public DNASequence() {
        sequence = "";
    }

    public DNASequence(String sequence) {
        this.sequence = sequence;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
