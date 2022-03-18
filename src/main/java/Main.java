import cli.Cli;
import db.Dao.Impl.BrandDao;
import db.Dao.Impl.OsDao;
import db.Dao.Impl.TabletDao;
import db.Entity.Brand;
import db.Entity.Os;
import db.Entity.Tablet;

public class Main {

    public static void main(String[] args) {

//        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        Cli.start();
    }

}
