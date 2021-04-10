package web.dao;

import org.springframework.stereotype.Repository;
import web.entity.Role;
import web.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Repository
public class AdminDaoImpl implements AdminDao{
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

    @Override
    public void addUser(User user) {
        entityManager.createNativeQuery("INSERT INTO users (name, password) VALUES (?, ?)")
                .setParameter(1, user.getName())
                .setParameter(2, user.getPassword())
                .executeUpdate();

        long id = getUserByUsername(user.getUsername()).getId();

        for (Role role : user.getRoles()) {
            if (role.getAuthority().equals("ADMIN")) {
                entityManager.createNativeQuery("INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)")
                        .setParameter(1, id)
                        .setParameter(2, 1)
                        .executeUpdate();
            } else if (role.getAuthority().equals("USER")) {
                entityManager.createNativeQuery("INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)")
                        .setParameter(1, id)
                        .setParameter(2, 2)
                        .executeUpdate();
            }
        }
    }

    @Override
    public void updateUser(long id, String name, String password, Set<Role> roles) {
        entityManager.createQuery("update User set name = :nameParam, password = :passwordParam where id = :idParam")
                .setParameter("nameParam", name)
                .setParameter("passwordParam", password)
                .setParameter("idParam", id)
                .executeUpdate();
        entityManager.createNativeQuery("DELETE FROM users_roles where user_id = ?")
                .setParameter(1, id)
                .executeUpdate();
        for (Role role : roles) {
            if (role.getAuthority().equals("ADMIN")) {
                entityManager.createNativeQuery("INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)")
                        .setParameter(1, id)
                        .setParameter(2, 1)
                        .executeUpdate();
            } else if (role.getAuthority().equals("USER")) {
                entityManager.createNativeQuery("INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)")
                        .setParameter(1, id)
                        .setParameter(2, 2)
                        .executeUpdate();
            }
        }
    }

    @Override
    public void deleteUser(long id) {
        entityManager.createQuery("delete from User where id = :idParam")
                .setParameter("idParam", id)
                .executeUpdate();
    }
}
