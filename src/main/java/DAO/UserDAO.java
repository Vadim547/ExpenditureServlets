package DAO;

import model.User;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;

public class UserDAO {

    public void saveUser(User user) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

        public List<User> getUsers() {
            List<User> userList = null;
            try (Session session = HibernateUtil.getSession()) {
                session.beginTransaction();
                userList = session.createQuery("from User").list();
                session.getTransaction().commit();
                session.close();

            } catch (Exception e) {
                e.getMessage();
            }
            return userList;
        }

}
