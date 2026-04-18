package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class StudentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private double gpa;

    @Column(nullable = false)
    @Lob
    private byte[] studentPhoto;

    @Lob
    private byte[] resume;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentModel department;


    public StudentModel() {
    }

    //creating all the basic functions for the columns
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
//setting Department
    public DepartmentModel getDepartment() {
        return department;
    }


    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }
    //allowing the user to upload image of themselves

    public byte[] getStudentImage() {
        return studentPhoto;
    }

    public void setStudentImage(byte[] studentPhoto) {
        this.studentPhoto = studentPhoto;
    }

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }
}