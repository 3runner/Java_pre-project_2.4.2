package web.dao;

import web.entity.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    User getUser(long id);

    void addUser(User user);

    void updateUser(long id, String name, String surname);

    void deleteUser(long id);
}
