package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class StudentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String Sname;

    @Column
    private String Surname;

    @Column
    private String Faculty;

    @Column
    private String major;

    @Column
    private double GPA;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DepartmentId")
    private DepartmentModel department;



    public StudentModel(){
        System.out.println("Student constructor");
    }


    public DepartmentModel getDepartment() {
        return department;
    }
    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }



    public Integer getId() {
        return id;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }




}