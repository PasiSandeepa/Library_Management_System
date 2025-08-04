package user;

import model.User;

import java.util.List;

public class UserController implements UserService{
    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean addUser(String user) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean updateUser(boolean user) {
        return false;
    }

    @Override
    public boolean searchUser(String id) {
        return false;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public boolean deleteUser(String id) {
        return false;
    }
}
