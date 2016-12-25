package ucu.advanced_databases.rudnitskih;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.lang.ArrayUtils;
import org.fluttercode.datafactory.impl.DataFactory;

public class DataProducer {

    public static void main(String[] args)
    {
        List<String> specializations = new ArrayList<String>();
        specializations.add("therapist");
        specializations.add("dentist");
        specializations.add("ophthalmologist");

        List<String> mrTypes = new ArrayList<String>();
        mrTypes.add("visit");
        mrTypes.add("exam");
        mrTypes.add("prescription");
        mrTypes.add("other");

        Map<String, String> props = new HashMap();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hbase_pu", props);
        EntityManager em = emf.createEntityManager();

        DataFactory df = new DataFactory();

        for (int ph = 0; ph < 10; ph++) {
            Physician physician = new Physician();
            String phFirstName = df.getFirstName();
            String phLastName = df.getLastName();
            String phFullName = phFirstName.concat(" ").concat(phLastName);
            physician.setId(phFullName.getBytes());
            physician.setFullName(phFullName);
            physician.setClinicName(df.getRandomWord());
            physician.setSpecialization(df.getItem(specializations));
            for (int p = 0; p < 10; p++) {
                Patient patient = new Patient();
                String pFirstName = df.getFirstName();
                String pLastName = df.getLastName();
                patient.setId(UuidAdapter.getBytesFromUUID(UUID.randomUUID()));
                patient.setFirstName(pFirstName);
                patient.setLastName(pLastName);
                patient.setDateOfBirth(df.getBirthDate());
                for (int mr = 0; mr < 10; mr++) {
                    String description = df.getRandomText(100);
                    MedicalRecord medicalRecord = new MedicalRecord();
                    medicalRecord.setId(patient.getId(), UuidAdapter.getBytesFromUUID(UUID.randomUUID()));
                    medicalRecord.setDatePerformed(df.getBirthDate());
                    medicalRecord.setDescription(df.getRandomText(100));
                    medicalRecord.setType(df.getItem(mrTypes));
                    em.persist(medicalRecord);
                }
                em.persist(patient);
            }
            em.persist(physician);
        }

        em.close();
        emf.close();
    }
}
