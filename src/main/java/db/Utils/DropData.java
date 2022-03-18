package db.Utils;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;

public class DropData {

    private final static List<String> tables = Arrays.asList("Brand", "Cpu", "Laptop", "Os", "Tablet", "VideoCard");
    private final static List<String> tablesAlter = Arrays.asList("brand", "cpu", "laptop", "os", "tablet", "video_card");

    public static void dropDataAndResetId() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        for (String table: tables) {
            session.createQuery("DELETE " + table).executeUpdate();
        }

        for (String table: tablesAlter) {
            session.createSQLQuery("ALTER TABLE " + table + " AUTO_INCREMENT = 1").executeUpdate();
        }

        transaction.commit();
        session.close();
    }
}
