package overridetech.jdbc.jpa.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import overridetech.jdbc.jpa.model.User;
import overridetech.jdbc.jpa.util.Util;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    private final static String TABLE_NAME = "users";

    private final static String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
            "    id SERIAL PRIMARY KEY,\n" +
            "    name VARCHAR(255) NOT NULL,\n" +
            "    lastName VARCHAR(255) NOT NULL,\n" +
            "    age SMALLINT CHECK (age >= 0)\n" +
            ");";

    private final static String DELETE_TABLE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    private final static String DELETE_ALL_STR_FROM_TABLE_SQL = "DELETE FROM " + TABLE_NAME;


    public void createUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE_SQL).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new HibernateException("Ошибка создания таблицы пользователей", e);
        }
    }


    public void dropUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(DELETE_TABLE_SQL).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new HibernateException("Ошибка удаления таблицы пользователей", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            transaction.commit();
        } catch (HibernateException e) {
            throw new HibernateException("Ошибка добавления пользователя", e);
        }
    }


    public void removeUserById(long id) {
        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (HibernateException e) {
            throw new HibernateException("Ошибка удаления пользователя", e);
        }
    }


    public List<User> getAllUsers() {
        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            List<User> users = session.createQuery("FROM User").list();
            transaction.commit();
            return users;
        } catch (HibernateException e) {
            throw new HibernateException("Ошибка получения списка пользователей", e);
        }
    }


    public void cleanUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(DELETE_ALL_STR_FROM_TABLE_SQL).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new HibernateException("Ошибка очистки таблицы пользователей", e);
        }
    }
}
