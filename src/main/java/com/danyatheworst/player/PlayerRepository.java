package com.danyatheworst.player;

import com.danyatheworst.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

public class PlayerRepository {
    public Optional<Player> findByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query<Player> query = session.createQuery("from Player where name = :name", Player.class);
        query.setParameter("name", name);
        Player player = query.uniqueResult();

        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(player);
    }

    public Player save(Player player) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(player);
        session.getTransaction().commit();
        session.close();
        return player;
    }
}
