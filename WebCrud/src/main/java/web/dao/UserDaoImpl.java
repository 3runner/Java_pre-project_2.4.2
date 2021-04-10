package web.dao;

import org.springframework.stereotype.Repository;
import web.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUserByUsername(String username) {
        return (User) entityManager.createQuery("select u from User as u where u.name = :usernameParam")
                .setParameter("usernameParam", username)
                .getSingleResult();
    }

    @Override
    public User getUser(long id) {
        return (User) entityManager
                .createQuery("select u from User as u where u.id = :idParam")
                .setParameter("idParam", id)
                .getSingleResult();
    }
}
