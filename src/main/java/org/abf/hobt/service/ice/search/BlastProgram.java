package org.abf.hobt.service.ice.search;

import org.abf.hobt.service.ice.IDataTransferObject;

/**
 * Types of blast programs that this system supports for nucleotide search
 *
 * @author Hector Plahar
 */
public enum BlastProgram implements IDataTransferObject {

    BLAST_N("blastn"),
    TBLAST_X("tblastx");

    private String name;

    BlastProgram(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
