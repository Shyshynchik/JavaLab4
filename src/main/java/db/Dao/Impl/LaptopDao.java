package db.Dao.Impl;

import db.Dao.Dao;
import db.Entity.Laptop;
import db.Utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LaptopDao implements Dao<Laptop> {

    private final BrandDao brandDao = new BrandDao();
    private final CpuDao cpuDao = new CpuDao();
    private final VideoCardDao videoCardDao = new VideoCardDao();

    @Override
    public Laptop get(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Laptop laptop = session.get(Laptop.class, id);
        session.close();
        return laptop;
    }

    @Override
    public List<Laptop> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Laptop> laptops = (List<Laptop>)  session.createQuery("From Laptop").list();
        session.close();
        return laptops;
    }

    @Override
    public void save(Laptop laptop) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(laptop);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Laptop laptop) {

    }

    @Override
    public void delete(Laptop laptop) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(laptop);
        transaction.commit();
        session.close();
    }

    @Override
    public Laptop getOrAdd(Laptop laptop) {
        return null;
    }

    @Override
    public Laptop getByName(String name) {
        return null;
    }

    public List<Laptop> find(String brand, String cpu, String videoCard) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        StringBuffer sql = new StringBuffer("from Laptop where");
        int count = 0;

        if (!brand.equals("-")) {
            sql.append(" brand = :brandId ");
            count++;
        }

        if (!cpu.equals("-")) {
            if (count > 0) {
                sql.append("and");
            }
            count++;
            sql.append(" cpu = :cpuId ");
        }

        if (!videoCard.equals("-")) {
            if (count > 0) {
                sql.append("and");
            }
            count++;
            sql.append(" videoCard = :videoCardId ");
        }

        Query query = session.createQuery(sql.toString());

        if (!brand.equals("-")){
            query.setParameter("brandId", brandDao.getByName(brand));
        }

        if (!cpu.equals("-")){
            query.setParameter("cpuId", cpuDao.getByName(cpu));
        }

        if (!videoCard.equals("-")){
            query.setParameter("videoCardId", videoCardDao.getByName(videoCard));
        }

        List<Laptop> laptops = query.list();

        transaction.commit();
        session.close();

        return laptops;
    }
}
