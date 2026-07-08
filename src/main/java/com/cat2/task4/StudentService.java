package com.cat2.task4;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StudentService extends Remote {

    List<Student> getStudents() throws RemoteException;
}
