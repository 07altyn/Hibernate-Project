package org.example.repositry;


import org.example.model.DepartmentModel;
import org.example.model.StudentModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class StudentRepo {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
    return new Configuration().
            configure().
            addAnnotatedClass(StudentModel.class).buildSessionFactory();



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
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }

    }

    //read function
    public StudentModel readStudent(int id)
    {
        StudentModel student = null;
        try(Session session = sessionFactory.openSession())
        {
            student = session.find(StudentModel.class, id);
            return student;
        }catch(HibernateException e){
            e.printStackTrace();
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

            studentModel.setSname(student.getSname());
            studentModel.setSurname(student.getSurname());
            studentModel.setFaculty(student.getFaculty());
            studentModel.setMajor(student.getMajor());
            studentModel.setGPA(student.getGPA());

            session.merge(studentModel);
            transaction.commit();
            return true;
        }catch(HibernateException e){
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
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
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }



}