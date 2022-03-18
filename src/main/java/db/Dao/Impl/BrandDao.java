package db.Dao.Impl;

import db.Dao.Dao;
import db.Entity.Brand;
import db.Utils.CreateSql;
import db.Utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BrandDao implements Dao<Brand> {

    @Override
    public Brand get(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Brand brand = session.get(Brand.class, id);
        session.close();
        return brand;
    }

    @Override
    public List<Brand> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Brand> brands = (List<Brand>)  session.createQuery("From Brand").list();
        session.close();
        return brands;
    }

    @Override
    public void save(Brand brand) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(brand);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Brand brand) {
//        Session session = HibernateSessionFactory.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//        session.update(brand);
//        transaction.commit();
//        session.close();
    }

    @Override
    public void delete(Brand brand) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(brand);
        transaction.commit();
        session.close();
    }

    @Override
    public Brand getOrAdd(Brand brand) {
        if (getByName(brand.getName()) == null) {
            save(brand);
            return brand;
        } else {
            return getByName(brand.getName());
        }
    }

    @Override
    public Brand getByName(String name) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery(CreateSql.makeSql("Brand", name));
        List<Brand> brands = query.list();
        session.close();
        if (brands.isEmpty()) {
            return null;
        } else {
            return brands.get(0);
        }
    }
}
