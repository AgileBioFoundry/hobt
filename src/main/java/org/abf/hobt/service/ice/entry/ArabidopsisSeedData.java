package org.abf.hobt.service.ice.entry;

import org.abf.hobt.service.ice.IDataTransferObject;

import java.util.Date;

public class ArabidopsisSeedData implements IDataTransferObject {

    private static final long serialVersionUID = 1L;

    private String homozygosity;
    private String ecotype;
    private Date harvestDate;
    private String seedParents;
    private Generation generation;
    private PlantType plantType;
    private Boolean sentToAbrc;

    // getters and setters
    public String getHomozygosity() {
        return homozygosity;
    }

    public void setHomozygosity(String homozygosity) {
        this.homozygosity = homozygosity;
    }

    public String getEcotype() {
        return ecotype;
    }

    public void setEcotype(String ecotype) {
        this.ecotype = ecotype;
    }

    public Date getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(Date harvestDate) {
        this.harvestDate = harvestDate;
    }

    public String getSeedParents() {
        return seedParents;
    }

    public void setSeedParents(String parents) {
        this.seedParents = parents;
    }

    public Generation getGeneration() {
        return generation;
    }

    public void setGeneration(Generation generation) {
        this.generation = generation;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public Boolean isSentToAbrc() {
        return sentToAbrc;
    }

    public void setSentToAbrc(Boolean sentToAbrc) {
        this.sentToAbrc = sentToAbrc;
    }
}
