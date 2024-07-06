package com.danyatheworst.match;

import com.danyatheworst.common.Paginated;
import com.danyatheworst.exceptions.DatabaseOperationException;
import com.danyatheworst.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.util.List;

public class MatchRepository {

    public Paginated<Match> find(int pageNumber, int pageSize, String filterAlike) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hqlCount = "SELECT COUNT(m) FROM Match m WHERE m.player1.name LIKE :alike OR m.player2.name LIKE :alike";
            String hql = "FROM Match m WHERE m.player1.name LIKE :alike OR m.player2.name LIKE :alike ORDER BY m.ID DESC";

            Query<Long> countQuery = session.createQuery(hqlCount, Long.class);
            countQuery.setParameter("alike", "%" + filterAlike + "%");

            Query<Match> query = session.createQuery(hql, Match.class);
            query.setParameter("alike", "%" + filterAlike + "%");

            long totalCount = countQuery.getSingleResult();

            List<Match> matches = query.setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();

            return new Paginated<>(totalCount, matches);
        } catch (HibernateException e) {
            throw new DatabaseOperationException("Failed to read matches in the database");
        }
    }

    public void save(Match match) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(match);
        } catch (HibernateException e) {
            throw new DatabaseOperationException("Failed to save match in the database");
        }
    }
}
