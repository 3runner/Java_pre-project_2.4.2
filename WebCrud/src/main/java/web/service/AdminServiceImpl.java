package web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.AdminDao;
import web.entity.Role;
import web.entity.User;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private final AdminDao adminDao;

    public AdminServiceImpl(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return adminDao.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return adminDao.getUser(id);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        adminDao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(long id, String name, String surname, Set<Role> roles) {
        adminDao.updateUser(id, name, surname, roles);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        adminDao.deleteUser(id);
    }
}
