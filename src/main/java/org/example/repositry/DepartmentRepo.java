package org.example.repositry;

import org.example.model.DepartmentModel;
import org.example.model.FacultyModel;
import org.example.model.StudentModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.logging.Logger;

public class DepartmentRepo {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static final Logger logger = Logger.getLogger(DepartmentRepo.class.getName());
    private static SessionFactory buildSessionFactory() {
        return new Configuration().
                configure().
                addAnnotatedClass(FacultyModel.class).
                addAnnotatedClass(DepartmentModel.class).
                addAnnotatedClass(StudentModel.class).
                buildSessionFactory();
    }

    //logger
    private void rollbackAndLog(Transaction transaction, HibernateException e, String message){
        logger.severe(message + ' ' + e.getMessage());
        if (transaction != null) transaction.rollback();
    }


    // create a department
    public boolean createDepartment(DepartmentModel department)
    {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            session.persist(department);
            transaction.commit();
            return true;
        }catch(HibernateException e){
            rollbackAndLog(transaction, e,"Error while creating department");
            return false;
        }
    }

    //read the data and retrive
    public DepartmentModel readDepartment(int id){

        try(Session session = sessionFactory.openSession())
        {
            return session.find(DepartmentModel.class, id);
        }catch(HibernateException e){
            logger.severe("Error retriving departments" + id +  e.getMessage());
            return null;
        }
    }

    //updating the data
    public boolean updateDepartent(DepartmentModel newDepartment,int id){
        DepartmentModel department;
        Transaction transaction = null ;

        try(Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            department = session.find(DepartmentModel.class, id);

            if(department == null)
            {
                logger.warning("Department not found with id: " + id);
                transaction.rollback();
                return false;
            }

            department.setDepartmentName(newDepartment.getDepartmentName());
            department.setFaculty(newDepartment.getFaculty());

            session.merge(department);
            transaction.commit();
            return true;
        }catch (HibernateException e){
            rollbackAndLog(transaction, e,"Error updating department");
            return false;
        }
    }
    //deleting a department
    public  boolean deleteDepartment(int id){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            DepartmentModel department = session.find(DepartmentModel.class, id);

            if(department == null){
                transaction.rollback();
                return false;
            }

            session.remove(department);
            transaction.commit();
            return true;

        }catch (HibernateException e) {
            rollbackAndLog(transaction, e, "Error deleting department");
            return false;
        }

    }

}