package db.Dao.Impl;

import db.Dao.Dao;
import db.Entity.Cpu;
import db.Utils.CreateSql;
import db.Utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CpuDao implements Dao<Cpu> {

    @Override
    public Cpu get(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Cpu cpu = session.get(Cpu.class, id);
        session.close();
        return cpu;
    }

    @Override
    public List<Cpu> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Cpu> cpus = (List<Cpu>)  session.createQuery("From Cpu").list();
        session.close();
        return cpus;
    }

    @Override
    public void save(Cpu cpu) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(cpu);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Cpu cpu) {

    }

    @Override
    public void delete(Cpu cpu) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(cpu);
        transaction.commit();
        session.close();
    }

    @Override
    public Cpu getOrAdd(Cpu cpu) {
        if (getByName(cpu.getName()) == null) {
            save(cpu);
            return cpu;
        } else {
            return getByName(cpu.getName());
        }
    }

    @Override
    public Cpu getByName(String name) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery(CreateSql.makeSql("Os", name));
        List<Cpu> cpus = query.list();
        session.close();
        if (cpus.isEmpty()) {
            return null;
        } else {
            return cpus.get(0);
        }
    }
}
