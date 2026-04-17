package org.example.repositry;

import org.example.model.DepartmentModel;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DepartmentRepo {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        return new Configuration().
                configure().
                addAnnotatedClass(DepartmentModel.class).
                buildSessionFactory();
    }

    // create a department


}
