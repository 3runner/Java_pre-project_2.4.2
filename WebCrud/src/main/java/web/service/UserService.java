package web.service;

import web.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(int id, List<User> list);

    void addUser(User user);

    void updateUser(long id, String name, String surname);

    void deleteUser(User user);

}
