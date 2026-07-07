package cat_2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author harshvirsinghahuja
 */
// Name: [Your Name] | Student Number: [Your Number] | Date: July 2026

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

    private static final String DB_URL  = "jdbc:mysql://localhost:3306/rmi_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Harshvir082005"; // ← change this

    public StudentServer() throws RemoteException {
        super();
    }

    @Override
    public List<Student> getStudents() throws RemoteException {
        List<Student> students = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM student_data");

            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("ID"),
                    rs.getString("NAME"),
                    rs.getString("COURSE"),
                    rs.getInt("SCORE"),
                    rs.getString("EMAIL")
                ));
            }

            rs.close();
            stmt.close();
            conn.close();

            System.out.println("Fetched " + students.size() + " students from DB");

        } catch (Exception e) {
            throw new RemoteException("Database error: " + e.getMessage(), e);
        }

        return students;
    }

    public static void main(String[] args) {
        try {
            StudentServer serverObject = new StudentServer();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("StudentService", serverObject);

            System.out.println("Server is running...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}