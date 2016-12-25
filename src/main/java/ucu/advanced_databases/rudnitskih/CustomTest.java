package ucu.advanced_databases.rudnitskih;

import com.lits.kundera.test.BaseTest;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.Assert;

public class CustomTest extends BaseTest {

    @Override
    public void customTest() throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hbase_pu");
        EntityManager em = emf.createEntityManager();

        Query findQuery = em.createQuery("select p from Physician p");
        List<Physician> allPhysicians = findQuery.getResultList();
        Assert.assertNotEquals(0, allPhysicians.size());

        Query findQuery2 = em.createQuery("select p from Patient p where p.firstName = John");
        List<Patient> allJohns = findQuery2.getResultList();
        Assert.assertNotEquals(0, allJohns.size());

        Query findQuery3 = em.createQuery("select mr from MedicalRecord mr where mr.type = visit");
        List<MedicalRecord> allRecords = findQuery3.getResultList();
        Assert.assertEquals(0, allRecords.size());
    }
}
