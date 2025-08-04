package user;

import model.User;

import java.util.List;

public interface UserService {

    boolean addUser(User user);

    boolean addUser(String user);

    boolean updateUser(User user);

    boolean updateUser(boolean user);

    boolean searchUser(String id);
    List<User> getAll();
    boolean deleteUser(String id);






}
