package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("CREATE TABLE IF NOT EXISTS \"User\"(id serial primary key,name text,lastName text,age int)");
         nativeQuery.executeUpdate();
        transaction.commit();

    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("drop table if exists \"User\"");
        nativeQuery.executeUpdate();
        transaction.commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        transaction.commit();

    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("delete from User where id =:id");
        query.setParameter("id", id);
        query.executeUpdate();
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query<User> query = session.createQuery("from User", User.class);
        List<User> resultList = query.getResultList();
        transaction.commit();
        return resultList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("truncate table \"User\"");
        nativeQuery.executeUpdate();

        transaction.commit();
    }
}
