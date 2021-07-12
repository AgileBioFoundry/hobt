package org.abf.hobt.tier;

import org.abf.hobt.dao.hibernate.HibernateConfiguration;
import org.junit.jupiter.api.*;

import java.util.BitSet;
import java.util.List;

class TiersTest {

    @BeforeAll
    static void initialize() {
        HibernateConfiguration.initializeMock();
    }

    @BeforeEach
    void setUp() {
        HibernateConfiguration.beginTransaction();
    }

    @AfterEach
    void tearDown() {
        HibernateConfiguration.commitTransaction();
    }

    @AfterAll
    static void close() {
        HibernateConfiguration.close();
    }

    @Test
    void get() {
        Tiers tiers = new Tiers();
        final int count = 50;

        for (int i = 0; i < count; i += 1) {
            Tier tier = new Tier();
            tier.setIndex(i);
            tier.setLabel("tier" + i);
            Assertions.assertNotNull(tiers.create(tier));
        }
        List<Tier> tierList = tiers.get(false);
        BitSet bitSet = new BitSet();
        for (Tier tier : tierList)
            bitSet.set(tier.getIndex());
        Assertions.assertTrue(count <= tierList.size());
    }

    @Test
    void create() {
        final String label = "test1";
        Tiers tiers = new Tiers();
        Tier tier = new Tier();
        tier.setIndex(0);
        tier.setLabel(label);
        tier = tiers.create(tier);
        Assertions.assertNotNull(tier);
        Assertions.assertEquals(label, tier.getLabel());
    }
}
