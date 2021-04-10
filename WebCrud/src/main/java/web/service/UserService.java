package web.service;

import web.entity.User;

public interface UserService {

    User getUser(long id);

    User getUserByUsername(String s);
}
