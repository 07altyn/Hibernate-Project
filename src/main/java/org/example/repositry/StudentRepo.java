package org.example.repositry;


import org.example.model.DepartmentModel;
import org.example.model.FacultyModel;
import org.example.model.StudentModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.util.logging.Logger;

public class StudentRepo {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static final Logger logger = Logger.getLogger(StudentRepo.class.getName());

    private static SessionFactory buildSessionFactory() {
    return new Configuration().
            configure().
            addAnnotatedClass(StudentModel.class).
            addAnnotatedClass(DepartmentModel.class).
            addAnnotatedClass(FacultyModel.class).
            buildSessionFactory();
    }

    //logger
    private void rollbackAndLog(Transaction transaction, HibernateException e, String message)
    {
        logger.severe(message + ' ' + e.getMessage());
        if (transaction != null) transaction.rollback();
    }

//create function
    public boolean createStudent(StudentModel student)
    {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.persist(student);
            transaction.commit();
            return true;

        }catch(HibernateException e){
            rollbackAndLog(transaction, e, "Error creating student");
            return false;
        }

    }

    //read function
    public StudentModel readStudent(int id)
    {
        try(Session session = sessionFactory.openSession())
        {
            return session.find(StudentModel.class, id);

        }catch(HibernateException e){
            logger.severe("Error reading student" + id +  e.getMessage());
            return null;
        }
    }
//update student
    public boolean updateStudent(StudentModel student, int id)
    {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            StudentModel studentModel = session.find(StudentModel.class, id);

            if (studentModel == null){
                System.out.println("Student not found");
                transaction.rollback();
                return false;
            }

            studentModel.setFirstName(student.getFirstName());
            studentModel.setLastName(student.getLastName());
            studentModel.setMajor(student.getMajor());
            studentModel.setGpa(student.getGpa());
            studentModel.setDepartment(student.getDepartment());
            studentModel.setStudentImage(student.getStudentImage());
            studentModel.setResume(student.getResume());


            session.merge(studentModel);
            transaction.commit();
            return true;
        }catch(HibernateException e){
            rollbackAndLog(transaction, e, "Error updating student");
            return false;
        }
    }

    public boolean deleteStudent(int id){

        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()){

            transaction = session.beginTransaction();
            StudentModel studentModel = session.find(StudentModel.class, id);

            if (studentModel == null){
                System.out.println("Student not found");
                transaction.rollback();
                return false;
            }

            session.remove(studentModel);
            transaction.commit();

            return true;
        }catch(HibernateException e){
            rollbackAndLog(transaction, e, "Error deleting student");
            return false;
        }
    }

}