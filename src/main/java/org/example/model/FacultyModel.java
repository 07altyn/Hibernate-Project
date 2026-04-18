package org.example.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "faculties")
public class FacultyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer facultyId;

    @Column(nullable = false, unique = true)
    private String facultyName;

    @Column(nullable = false)
    private String deanName;

    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DepartmentModel> departments = new ArrayList<>();

    public Integer getFacultyId() {
        return facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getDeanName() {
        return deanName;
    }

    public void setDeanName(String deanName) {
        this.deanName = deanName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<DepartmentModel> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentModel> departments) {
        this.departments.clear();
        if (departments != null) {
            departments.forEach(this::addDepartment);
        }
    }

    public void addDepartment(DepartmentModel department) {
        departments.add(department);
        department.setFaculty(this);
    }

    public void removeDepartment(DepartmentModel department) {
        departments.remove(department);
        department.setFaculty(null);
    }
}
