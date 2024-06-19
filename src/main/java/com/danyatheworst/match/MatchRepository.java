package com.danyatheworst.match;

import com.danyatheworst.exceptions.DatabaseOperationException;
import com.danyatheworst.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class MatchRepository {

    public void save(Match match) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(match);
        } catch (HibernateException e) {
            throw new DatabaseOperationException("Failed to save match in the database");
        }
    }
}
