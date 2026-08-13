package vn.edu.eaut.lab4.model;

public class Student {
    private final String id;
    private final String fullName;
    private final double score;

    public Student(String id, String fullName, double score) {
        this.id = id;
        this.fullName = fullName;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public double getScore() {
        return score;
    }
}
