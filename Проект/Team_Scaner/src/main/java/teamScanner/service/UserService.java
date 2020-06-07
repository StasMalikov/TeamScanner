package teamScanner.service;


import teamScanner.model.User;

import java.util.List;


public interface UserService {

    User register(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    String encodePassword(String password);

    public boolean matchesPassword(String password1, String password2);

    void delete(Long id);

    public User removeUserRole(User user, String roleName);

    public User setUserRole(User user, String roleName);
}
