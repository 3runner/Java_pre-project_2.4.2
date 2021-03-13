package web.dao;

import web.entity.Role;
import web.entity.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    List<User> getAllUsers();

    User getUser(long id);

    void addUser(User user);

    void updateUser(long id, String name, String surname, Set<Role> roles);

    void deleteUser(long id);

    User getUserByUsername(String name);
}
