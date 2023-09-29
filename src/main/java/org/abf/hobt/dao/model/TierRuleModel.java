package org.abf.hobt.dao.model;

import jakarta.persistence.*;
import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.tier.Rule;
import org.abf.hobt.tier.rule.RuleType;

@Entity
@Table(name = "TierRule")
public class TierRuleModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "tier_rule_id")
    @SequenceGenerator(name = "tier_rule_id", sequenceName = "tier_rule_id_seq", allocationSize = 1)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id")
    private TierModel tier;

    @Column(name = "percentage")
    private int percentage;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private RuleType type;

    public long getId() {
        return id;
    }

    public TierModel getTier() {
        return tier;
    }

    public void setTier(TierModel tier) {
        this.tier = tier;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public RuleType getType() {
        return type;
    }

    public void setType(RuleType type) {
        this.type = type;
    }

    @Override
    public Rule toDataTransferObject() {
        Rule rule = new Rule();
        rule.setId(this.id);
        rule.setPercentage(this.percentage);
        rule.setType(this.type);
        return rule;
    }
}
