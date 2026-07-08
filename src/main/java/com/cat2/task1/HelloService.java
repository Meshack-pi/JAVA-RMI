package com.cat2.task1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote {

    String greet(String name) throws RemoteException;
}
