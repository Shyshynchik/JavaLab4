package db.Dao.Impl;

import db.Dao.Dao;
import db.Entity.VideoCard;
import db.Utils.CreateSql;
import db.Utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class VideoCardDao implements Dao<VideoCard> {
    @Override
    public VideoCard get(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        VideoCard videoCard = session.get(VideoCard.class, id);
        session.close();
        return videoCard;
    }

    @Override
    public List<VideoCard> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<VideoCard> videoCards = (List<VideoCard>)  session.createQuery("From VideoCard").list();
        session.close();
        return videoCards;
    }

    @Override
    public void save(VideoCard videoCard) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(videoCard);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(VideoCard videoCard) {

    }

    @Override
    public void delete(VideoCard videoCard) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(videoCard);
        transaction.commit();
        session.close();
    }

    @Override
    public VideoCard getOrAdd(VideoCard videoCard) {
        if (getByName(videoCard.getName()) == null) {
            save(videoCard);
            return videoCard;
        } else {
            return getByName(videoCard.getName());
        }
    }

    @Override
    public VideoCard getByName(String name) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery(CreateSql.makeSql("VideoCard", name));
        List<VideoCard> videoCards = query.list();
        session.close();
        if (videoCards.isEmpty()) {
            return null;
        } else {
            return videoCards.get(0);
        }
    }
}
