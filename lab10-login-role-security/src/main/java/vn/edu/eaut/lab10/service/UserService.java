package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.UserRepository;
import vn.edu.eaut.lab10.util.PasswordUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> search(String keyword, Role role) {
        return userRepository.search(keyword, role);
    }

    public Map<String, String> validate(User user, String rawPassword, boolean isUpdate) {
        Map<String, String> errors = new HashMap<>();

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            errors.put("email", "Email không được để trống.");
        } else if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.put("email", "Email không đúng định dạng.");
        } else if (userRepository.existsByEmail(user.getEmail().trim(), isUpdate ? user.getId() : null)) {
            errors.put("email", "Email này đã được sử dụng bởi tài khoản khác.");
        }

        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            errors.put("fullName", "Họ và tên không được để trống.");
        }

        if (user.getRole() == null) {
            errors.put("role", "Vui lòng chọn vai trò.");
        }

        if (!isUpdate) {
            if (rawPassword == null || rawPassword.trim().isEmpty()) {
                errors.put("password", "Mật khẩu không được để trống.");
            } else if (rawPassword.length() < 6) {
                errors.put("password", "Mật khẩu phải có ít nhất 6 ký tự.");
            }
        }

        return errors;
    }

    public void createUser(User user, String rawPassword) {
        user.setPassword(PasswordUtil.hashPassword(rawPassword));
        userRepository.save(user);
    }

    public void updateUser(User user) {
        User existing = userRepository.findById(user.getId());
        if (existing != null) {
            existing.setEmail(user.getEmail().trim());
            existing.setFullName(user.getFullName().trim());
            existing.setRole(user.getRole());
            existing.setActive(user.isActive());
            userRepository.update(existing);
        }
    }

    public boolean toggleStatus(Integer id, Integer currentUserId) {
        if (id == null || id.equals(currentUserId)) {
            return false; // Cannot disable own account
        }
        User user = userRepository.findById(id);
        if (user != null) {
            user.setActive(!user.isActive());
            userRepository.update(user);
            return true;
        }
        return false;
    }

    public boolean resetPassword(Integer id, String newPassword) {
        if (id == null || newPassword == null || newPassword.length() < 6) {
            return false;
        }
        User user = userRepository.findById(id);
        if (user != null) {
            user.setPassword(PasswordUtil.hashPassword(newPassword));
            userRepository.update(user);
            return true;
        }
        return false;
    }

    public boolean deleteUser(Integer id, Integer currentUserId) {
        if (id == null || id.equals(currentUserId)) {
            return false; // Cannot delete own account
        }
        User user = userRepository.findById(id);
        if (user != null) {
            userRepository.delete(id);
            return true;
        }
        return false;
    }

    public long count() {
        return userRepository.count();
    }
}
