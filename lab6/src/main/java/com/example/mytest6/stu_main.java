package com.example.mytest6;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class stu_main {


    Configuration config=new Configuration().configure("hibernate.cfg.xml");

    SessionFactory sf= config.buildSessionFactory();

    public void insert(String name, String usn , int totalmarks) {
        Session session= sf.openSession();
        Transaction t=session.beginTransaction();

        Student s = new Student();
        s.setName(name);
        s.setUsn(usn);
        s.setTotal_marks(totalmarks);

        session.save(s);
        t.commit();
        session.close();
    }

    public void display() {

        Session session= sf.openSession();

        Query q = session.createQuery("from Student");
        List<Student> l = q.getResultList();
        System.out.println("List of Students:");
        for (int i = 0; i < l.size(); i++) {
            System.out.println(l.get(i));
        }

        session.close();
    }

    public void search(String usn) {

        Session session= sf.openSession();

        Query q = session.createQuery("from Student where usn = :usn");
        q.setParameter("usn", usn);
        List<Student> l = q.getResultList();
        if (l.isEmpty()) {
            System.out.println("Not Found");
        } else {
            System.out.println("Student Details:");
            for (int i = 0; i < l.size(); i++) {
                System.out.println(l.get(i));
            }
        }

        session.close();
    }

    public void delete(String usn) {

        Session session= sf.openSession();
        Transaction t=session.beginTransaction();

        Query q = session.createQuery("delete from Student where usn = :usn");
        q.setParameter("usn", usn);
        int status = q.executeUpdate();
        if (status != 0) {
            System.out.println(usn + " Deleted successfully");
        } else {
            System.out.println(usn + " not found");
        }


        t.commit();
        session.close();
    }

    public static void main(String[] args) {

        stu_main sm=new stu_main();

        Scanner sc = new Scanner(System.in);

        lp:
        while (true)
        {
            System.out.println("1: Insert");
            System.out.println("2: Delete");
            System.out.println("3: Search");
            System.out.println("4: Display");
            System.out.println("7: exit");
            System.out.print("Make your choice: ");
            int ch = sc.nextInt();
            switch (ch) {
                case 1:
                    System.out.print("Enter the Student Details to insert \n");
                    System.out.print("Enter the Student usn \n");
                    String usn = sc.next();
                    System.out.print("Enter the Student name \n");
                    String name = sc.next();
                    System.out.print("Enter the Student totalmarks \n");
                    int tm = sc.nextInt();
                    sm.insert(name, usn, tm);
                    break;
                case 2:
                    System.out.print("Enter student usn to delete\n");
                    usn = sc.next();
                    sm.delete(usn);
                    break;
                case 3:
                    System.out.print("Enter student usn to search\n");
                    usn = sc.next();
                    sm.search(usn);
                    break;
                case 4:
                    sm.display();
                    break;
                case 7:
                    break lp;
                default:
                    System.out.println("Invalid choice! Please make a valid choice. \n\n");
            }
        }
    }
}
