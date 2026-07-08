package com.cat2.task4;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentServer extends UnicastRemoteObject implements StudentService {

    public static final int REGISTRY_PORT = 1099;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/rmi_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "12345678";

    protected StudentServer() throws RemoteException {
        super();
    }

    @Override
    public List<Student> getStudents() throws RemoteException {
        List<Student> students = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM student_data")) {

            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("ID"),
                    rs.getString("NAME"),
                    rs.getString("COURSE"),
                    rs.getInt("SCORE"),
                    rs.getString("EMAIL")
                ));
            }

            System.out.println("Fetched " + students.size() + " students from DB");

        } catch (Exception e) {
            throw new RemoteException("Database error: " + e.getMessage(), e);
        }

        return students;
    }

    public static void main(String[] args) {
        try {
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(REGISTRY_PORT);
            } catch (RemoteException alreadyRunning) {
                registry = LocateRegistry.getRegistry(REGISTRY_PORT);
            }

            StudentServer serverObject = new StudentServer();
            registry.rebind("StudentService", serverObject);
            System.out.println("StudentServer is running on port " + REGISTRY_PORT + "...");
            Thread.currentThread().join(); // keep the server (and mvn exec:java) alive

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
