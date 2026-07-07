package cat_2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author harshvirsinghahuja
 */

import cat_2.Student;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface  StudentService extends Remote {
    
    List<Student> getStudents() throws RemoteException;
    
}