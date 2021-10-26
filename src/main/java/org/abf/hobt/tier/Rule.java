package org.abf.hobt.tier;

import org.abf.hobt.service.ice.IDataTransferObject;
import org.abf.hobt.tier.rule.RuleType;

public class Rule implements IDataTransferObject {

    private long id;
    private RuleType type;
    private int percentage;

    public RuleType getType() {
        return type;
    }

    public void setType(RuleType type) {
        this.type = type;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
