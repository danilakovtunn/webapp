package ru.msu.cmc.webapp.utils;

import ru.msu.cmc.webapp.models.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class HibernateUtility {
    private static final SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new Configuration().
                    configure()
                    .addAnnotatedClass(Person.class)
                    .addAnnotatedClass(Education.class)
                    .addAnnotatedClass(Company.class)
                    .addAnnotatedClass(Places.class)
                    .addAnnotatedClass(Resume.class)
                    .addAnnotatedClass(Vacancy.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}