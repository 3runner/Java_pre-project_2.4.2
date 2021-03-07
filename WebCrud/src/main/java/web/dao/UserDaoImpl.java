package web.dao;

import org.springframework.stereotype.Repository;
import web.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return entityManager
                .createQuery("select u from User as u")
                .getResultList();
    }

    @Override
    public User getUser(long id) {
        return (User) entityManager
                .createQuery("select u from User as u where u.id = :idParam")
                .setParameter("idParam", id)
                .getSingleResult();
    }

    @Override
    public void addUser(User user) {
        entityManager.createNativeQuery("INSERT INTO users (name, surname) VALUES (?, ?)")
                .setParameter(1, user.getName())
                .setParameter(2, user.getSurname())
                .executeUpdate();
    }

    @Override
    public void updateUser(long id, String name, String surname) {
        entityManager.createQuery("update User set name = :nameParam, surname = :surnameParam where id = :idParam")
                .setParameter("nameParam", name)
                .setParameter("surnameParam", surname)
                .setParameter("idParam", id)
                .executeUpdate();
    }

    @Override
    public void deleteUser(long id) {
        entityManager.createQuery("delete from User where id = :idParam")
                .setParameter("idParam", id)
                .executeUpdate();
    }
}
