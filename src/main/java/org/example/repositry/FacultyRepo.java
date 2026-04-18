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

public class FacultyRepo {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static final Logger logger = Logger.getLogger(FacultyRepo.class.getName());

    private static SessionFactory buildSessionFactory() {
        return new Configuration().
                configure().
                addAnnotatedClass(FacultyModel.class).
                addAnnotatedClass(DepartmentModel.class).
                addAnnotatedClass(StudentModel.class).
                buildSessionFactory();
    }

    private void rollbackAndLog(Transaction transaction, HibernateException e, String message) {
        logger.severe(message + ' ' + e.getMessage());
        if (transaction != null) transaction.rollback();
    }

    public boolean createFaculty(FacultyModel faculty) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(faculty);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            rollbackAndLog(transaction, e, "Error while creating faculty");
            return false;
        }
    }

    public FacultyModel readFaculty(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(FacultyModel.class, id);
        } catch (HibernateException e) {
            logger.severe("Error retrieving faculty " + id + " " + e.getMessage());
            return null;
        }
    }

    public boolean updateFaculty(FacultyModel newFaculty, int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            FacultyModel faculty = session.find(FacultyModel.class, id);

            if (faculty == null) {
                logger.warning("Faculty not found with id: " + id);
                transaction.rollback();
                return false;
            }

            faculty.setFacultyName(newFaculty.getFacultyName());
            faculty.setDeanName(newFaculty.getDeanName());
            faculty.setLocation(newFaculty.getLocation());

            session.merge(faculty);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            rollbackAndLog(transaction, e, "Error updating faculty");
            return false;
        }
    }

    public boolean deleteFaculty(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            FacultyModel faculty = session.find(FacultyModel.class, id);

            if (faculty == null) {
                transaction.rollback();
                return false;
            }

            session.remove(faculty);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            rollbackAndLog(transaction, e, "Error deleting faculty");
            return false;
        }
    }
}
