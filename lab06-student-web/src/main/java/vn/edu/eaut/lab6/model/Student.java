package vn.edu.eaut.lab6.model;

/** A simple in-memory student record used by Lab 6. */
public class Student {
    private String id;
    private String name;
    private String className;
    private String email;

    public Student() {
    }

    public Student(String id, String name, String className, String email) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.email = email;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
