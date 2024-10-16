package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoHibernateImpl.class);

    @Override
    public void createUsersTable() {
        logger.debug("Creating users table using Hibernate");

        String sql = "CREATE TABLE IF NOT EXISTS Users" +
                "(id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +
                "name CHARACTER VARYING(30)," +
                "lastName CHARACTER VARYING(30)," +
                "age INTEGER)";
        connectAndExecute(sql);
    }

    @Override
    public void dropUsersTable() {
        logger.debug("Dropping users table using Hibernate");

        String sql = "DROP TABLE IF EXISTS Users";
        connectAndExecute(sql);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        logger.debug("Saving user in table using Hibernate");

        Transaction transaction = null;
        if (name == null || lastName == null) {
            throw new NullPointerException("Saving user without name or last name using Hibernate");
        }
        try (SessionFactory sessionFactory = Util.getSessionFactory();
             Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Exception during saving user by id using Hibernate", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        logger.debug("Removing user from table by id using Hibernate");

        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.getSessionFactory();
             Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Exception during removing user by id using Hibernate", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        logger.debug("Get all users from table using Hibernate");

        try (SessionFactory sessionFactory = Util.getSessionFactory();
             Session session = sessionFactory.openSession()) {

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> rootEntry = criteriaQuery.from(User.class);
            CriteriaQuery<User> all = criteriaQuery.select(rootEntry);

            TypedQuery<User> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Exception during getting list of all users using Hibernate", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        logger.debug("Cleaning users table using Hibernate");

        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createCriteria(User.class).list().forEach(session::delete);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Exception during cleaning users table using Hibernate", e);
        }
    }

    private void connectAndExecute(String sql) {
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Exception when not parametrized sql call using Hibernate", e);
        }
    }
}
