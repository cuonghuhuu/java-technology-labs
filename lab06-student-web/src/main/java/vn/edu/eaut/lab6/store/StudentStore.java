package vn.edu.eaut.lab6.store;

import vn.edu.eaut.lab6.model.Student;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Thread-safe, in-memory storage. Data resets when the application restarts. */
public final class StudentStore {
    private static final StudentStore INSTANCE = new StudentStore();
    private final List<Student> students = new ArrayList<>();

    private StudentStore() {
    }

    public static StudentStore getInstance() {
        return INSTANCE;
    }

    public synchronized List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public synchronized Student findById(String id) {
        if (id == null) return null;
        return students.stream().filter(student -> id.equals(student.getId())).findFirst().orElse(null);
    }

    public synchronized boolean add(Student student) {
        if (student == null || findById(student.getId()) != null) return false;
        students.add(student);
        return true;
    }

    public synchronized boolean update(Student updatedStudent) {
        Student existing = findById(updatedStudent == null ? null : updatedStudent.getId());
        if (existing == null) return false;
        existing.setName(updatedStudent.getName());
        existing.setClassName(updatedStudent.getClassName());
        existing.setEmail(updatedStudent.getEmail());
        return true;
    }

    public synchronized boolean deleteById(String id) {
        Student existing = findById(id);
        return existing != null && students.remove(existing);
    }

    public synchronized List<Student> searchByName(String keyword) {
        String normalized = keyword == null ? "" : keyword.trim().toLowerCase(Locale.ROOT);
        if (normalized.isEmpty()) return findAll();
        return students.stream()
                .filter(student -> student.getName().toLowerCase(Locale.ROOT).contains(normalized))
                .toList();
    }

    public synchronized int count() {
        return students.size();
    }

    public synchronized Map<String, Integer> countByClass() {
        Map<String, Integer> result = new LinkedHashMap<>();
        for (Student student : students) {
            result.merge(student.getClassName(), 1, Integer::sum);
        }
        return result;
    }

    public synchronized void initializeSampleData() {
        if (!students.isEmpty()) return;
        students.add(new Student("SV001", "Nguyễn Văn An", "DCCNTT15.10.1", "an.nguyen@example.com"));
        students.add(new Student("SV002", "Trần Thị Bình", "DCCNTT15.10.1", "binh.tran@example.com"));
        students.add(new Student("SV003", "Lê Minh Châu", "DCCNTT15.10.2", "chau.le@example.com"));
        students.add(new Student("SV004", "Phạm Quốc Dũng", "DCCNTT15.10.2", "dung.pham@example.com"));
        students.add(new Student("SV005", "Hoàng Thu Hà", "DCCNTT15.11.1", "ha.hoang@example.com"));
    }
}
