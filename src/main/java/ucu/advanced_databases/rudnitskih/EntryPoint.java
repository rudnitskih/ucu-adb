package ucu.advanced_databases.rudnitskih;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;
import java.util.Date;

public class EntryPoint {
    public static void main(String[] args)
    {
        Patient patient = new Patient();
        patient.setId(UuidAdapter.getBytesFromUUID(UUID.randomUUID()));
        patient.setFirstName("John");
        patient.setLastName("Smith");
        patient.setDateOfBirth(new Date(1990,9,10));

        Physician physycian = new Physician();
        physycian.setId(UuidAdapter.getBytesFromUUID(UUID.randomUUID()));
        physycian.setFullName("John Snow");
        physycian.setClinicName("Black Castle");
        physycian.setSpecialization("Friendskilling");

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(patient.getId(), UuidAdapter.getBytesFromUUID(UUID.randomUUID()));
        medicalRecord.setDatePerformed(new Date());
        medicalRecord.setDescription("blabla");
        medicalRecord.setType("visit");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hbase_pu");
        EntityManager em = emf.createEntityManager();

        em.persist(patient);
        em.persist(physycian);
        em.persist(medicalRecord);
        em.close();
        emf.close();

        CustomTest test = new CustomTest();
        try {
            test.runSuite();
        } catch (IOException ex) {

        }
    }
}