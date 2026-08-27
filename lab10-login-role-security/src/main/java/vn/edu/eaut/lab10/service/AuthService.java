package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.UserRepository;
import vn.edu.eaut.lab10.util.PasswordUtil;

public class AuthService {

    private final UserRepository userRepository = new UserRepository();

    public User login(String email, String password) {
        if (email == null || password == null) {
            return null;
        }
        User user = userRepository.findByEmail(email.trim());
        if (user == null || !user.isActive()) {
            return null;
        }
        if (!PasswordUtil.checkPassword(password, user.getPassword())) {
            return null;
        }
        return user;
    }

    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        if (userId == null || oldPassword == null || newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }
        User user = userRepository.findById(userId);
        if (user == null) {
            return false;
        }
        if (!PasswordUtil.checkPassword(oldPassword, user.getPassword())) {
            return false;
        }
        user.setPassword(PasswordUtil.hashPassword(newPassword.trim()));
        userRepository.update(user);
        return true;
    }

    public boolean updateProfile(Integer userId, String fullName, String email) {
        if (userId == null || fullName == null || email == null) {
            return false;
        }
        User user = userRepository.findById(userId);
        if (user == null) {
            return false;
        }
        if (userRepository.existsByEmail(email.trim(), userId)) {
            return false;
        }
        user.setFullName(fullName.trim());
        user.setEmail(email.trim());
        userRepository.update(user);
        return true;
    }
}
