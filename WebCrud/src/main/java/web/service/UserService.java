package web.service;

import web.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(long id);

    void addUser(User user);

    void updateUser(long id, String name, String surname);

    void deleteUser(long id);

}
