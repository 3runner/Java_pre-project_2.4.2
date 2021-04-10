package web.dao;

import web.entity.User;

public interface UserDao {
    User getUser(long id);

    User getUserByUsername(String name);
}
