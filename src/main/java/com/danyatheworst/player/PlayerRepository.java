package com.danyatheworst.player;

import com.danyatheworst.exceptions.DatabaseOperationException;
import com.danyatheworst.utils.HibernateUtil;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

public class PlayerRepository {
    public Optional<Player> findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Player> query = session.createQuery("FROM Player WHERE name = :name", Player.class);
            query.setParameter("name", name);
            Player player = query.uniqueResult();
            return Optional.ofNullable(player);
        } catch (HibernateException e) {
            throw new DatabaseOperationException("Failed to read player with name " + name + " from the database");
        }
    }

    private Player save(Player player) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(player);
            return player;
        } catch (HibernateException e) {
            throw e;
        }
    }

    public Player saveOrFindExisting(Player player) {
        try {
            save(player);
        } catch (HibernateException e) {
            if (e.getCause() instanceof JdbcSQLIntegrityConstraintViolationException) {
                player = findByName(player.getName()).get();
            } else {
                throw new DatabaseOperationException("Failed to save or read existing player with name " +
                        player.getName() + " from the database");
            }
        }

        return player;
    }
}
