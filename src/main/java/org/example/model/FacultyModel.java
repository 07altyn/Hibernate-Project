package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class DepartmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer DepartmentId;

    @Column
    private String FacultyName;

    @Column
    private String Lecturer;

    @Column
    private String Discipline;




    public DepartmentModel(){
        System.out.println("Department constructor");
    }

    public String getFacultyName() {
        return FacultyName;
    }

    public void setFacultyName(String facultyName) {
        FacultyName = facultyName;
    }

    public String getLecturer() {
        return Lecturer;
    }

    public void setLecturer(String lecturer) {
        Lecturer = lecturer;
    }

    public String getDiscipline() {
        return Discipline;
    }

    public void setDiscipline(String discipline) {
        Discipline = discipline;
    }
}
