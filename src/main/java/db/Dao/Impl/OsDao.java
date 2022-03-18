package db.Dao.Impl;

import db.Dao.Dao;
import db.Entity.Os;
import db.Utils.CreateSql;
import db.Utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OsDao implements Dao<Os> {

    @Override
    public Os get(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Os os = session.get(Os.class, id);
        session.close();
        return os;
    }

    @Override
    public List<Os> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Os> os = (List<Os>)  session.createQuery("From Os").list();
        session.close();
        return os;
    }

    @Override
    public void save(Os os) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(os);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Os os) {

    }

    @Override
    public void delete(Os os) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(os);
        transaction.commit();
        session.close();
    }

    @Override
    public Os getOrAdd(Os os) {
        if (getByName(os.getName()) == null) {
            save(os);
            return os;
        } else {
            return getByName(os.getName());
        }
    }

    @Override
    public Os getByName(String name) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery(CreateSql.makeSql("Os", name));
        List<Os> oss = query.list();
        session.close();
        if (oss.isEmpty()) {
            return null;
        } else {
            return oss.get(0);
        }
    }

}
