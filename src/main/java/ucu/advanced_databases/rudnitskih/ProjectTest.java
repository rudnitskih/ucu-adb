package ucu.advanced_databases.rudnitskih;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.lits.kundera.test.BaseTest;

public class ProjectTest extends BaseTest {

    @Override
    public void customTest() throws IOException {

        Query query;
        List<String> result;

        Map<String, String> props = new HashMap<>();
        // props.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hbase_pu", props);
        EntityManager em = emf.createEntityManager();

        query = em.createQuery("select p from Physician p");
        result = query.getResultList();
        if (result.isEmpty()) {
            System.out.println("FAIL : Physican table is empty!");
            exit();
        }

        query = em.createQuery("select p from Patient p where p.firstName = \"Wendy\"");
        result = query.getResultList();
        if (result.isEmpty()) {
            System.out.println("FAIL : Patient doesn't exist!");
            exit();
        }

        query = em.createQuery("select mr from MedicalRecord mr where mr.type = \"type\"");
        result = query.getResultList();
        if (!result.isEmpty()) {
            System.out.println("FAIL : MedicalRecord is selected with unexisting type!");
            exit();
        }

        em.close();
        emf.close();
    }

    public static void main(String[] args) {
        ProjectTest test = new ProjectTest();
        try {
            test.runSuite();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
