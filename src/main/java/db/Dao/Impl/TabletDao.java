package db.Dao.Impl;

import db.Dao.Dao;
import db.Entity.Tablet;
import db.Utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class TabletDao implements Dao<Tablet> {

    private final BrandDao brandDao = new BrandDao();
    private final OsDao osDao = new OsDao();

    @Override
    public Tablet get(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Tablet tablet = session.get(Tablet.class, id);
        session.close();
        return tablet;
    }

    @Override
    public List<Tablet> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Tablet> tablet = (List<Tablet>)  session.createQuery("From Tablet").list();
        session.close();
        return tablet;
    }

    @Override
    public void save(Tablet tablet) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(tablet);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Tablet tablet) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "update Tablet set brand = :brandId, diagonal = :diagonalV,"
                + " ram = :ramV, os = :osId, memory = :memoryV"
                + " where id = :tabletId";


        Query query = session.createQuery(sql);

        query.setParameter("brandId", tablet.getBrand());
        query.setParameter("diagonalV", tablet.getDiagonal());
        query.setParameter("ramV", tablet.getRam());
        query.setParameter("osId", tablet.getOs());
        query.setParameter("memoryV", tablet.getMemory());
        query.setParameter("tabletId", tablet.getId());

        query.executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Tablet tablet) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(tablet);
        transaction.commit();
        session.close();
    }

    @Override
    public Tablet getOrAdd(Tablet tablet) {
        return null;
    }

    @Override
    public Tablet getByName(String name) {
        return null;
    }

    public List<Tablet> find(String brand, String os) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        StringBuffer sql = new StringBuffer("from Tablet where");
        int count = 0;

        if (!brand.equals("-")) {
            sql.append(" brand = :brandId ");
            count++;
        }

        if (!os.equals("-")) {
            if (count > 0) {
                sql.append("and");
            }
            sql.append(" os = :osId ");
        }

        Query query = session.createQuery(sql.toString());

        if (!brand.equals("-")){
            query.setParameter("brandId", brandDao.getByName(brand));
        }

        if (!os.equals("-")){
            query.setParameter("osId", osDao.getByName(os));
        }

        List<Tablet> tablets = query.list();

        transaction.commit();
        session.close();

        return tablets;
    }
}
