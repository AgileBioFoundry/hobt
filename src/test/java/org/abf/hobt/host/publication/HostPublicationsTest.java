package org.abf.hobt.host.publication;

import org.abf.hobt.common.ResultData;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.HibernateConfiguration;
import org.abf.hobt.dao.model.AccountModel;
import org.abf.hobt.dto.Account;
import org.abf.hobt.dto.Organism;
import org.abf.hobt.host.Organisms;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

class HostPublicationsTest {

    @BeforeEach
    void setUp() {
        HibernateConfiguration.initializeMock();
        HibernateConfiguration.beginTransaction();
    }

    @AfterEach
    void tearDown() {
        HibernateConfiguration.commitTransaction();
    }

    @Test
    void list() {
    }

    @Test
    void create() {
        Account account = createAccount();
        Assertions.assertNotNull(account);

        // create Organism
        Organism organism = new Organism();
        organism.setName("Test Org");
        organism.setPhylum("Test Phylum");
        Organisms organisms = new Organisms();
        organism = organisms.create(organism);
        Assertions.assertNotNull(organism);

        //create publication (marked as privileged)
        HostPublications hostPublications = new HostPublications(organism.getId(), account.getUserId());
        Publication publication = new Publication();
        publication.setPrivileged(true);
        publication.setLink("https://pubmed.ncbi.nlm.nih.gov/22718978/");
        publication.setJournal("Nucleic Acids Research");
        publication.setYear("2012");
        publication.setTitle("Design, implementation and practice of JBEI-ICE: an open source biological part registry platform and tools");
        publication.setAuthors("Timothy S Ham, Zinovii Dmytriv, Hector Plahar, Joanna Chen, Nathan J Hillson, Jay D Keasling");

        publication = hostPublications.create(publication);
        Assertions.assertNotNull(publication);

        // retrieve publications for organism
        ResultData<Publication> publications = hostPublications.list(0, 10, true, null);
        Assertions.assertFalse(publications.getRequested().isEmpty());
        Assertions.assertEquals(publications.getAvailable(), 1);

        // retrieve not privileged
        publications = hostPublications.list(0, 10, true, Boolean.FALSE);
        Assertions.assertTrue(publications.getRequested().isEmpty());
        Assertions.assertEquals(publications.getAvailable(), 0);

        // retrieve privileged
        publications = hostPublications.list(0, 10, true, Boolean.TRUE);
        Assertions.assertFalse(publications.getRequested().isEmpty());
        Assertions.assertEquals(publications.getAvailable(), 1);
    }

    public static Account createAccount() {
        AccountModel accountModel = new AccountModel();
        accountModel.setCreationTime(new Date());
        accountModel.setUserId(UUID.randomUUID().toString());
        accountModel.setEmail(accountModel.getUserId());
        accountModel.setFirstName("Test");
        accountModel.setLastName("test");
        return DAOFactory.getAccountDAO().create(accountModel).toDataTransferObject();
    }
}
