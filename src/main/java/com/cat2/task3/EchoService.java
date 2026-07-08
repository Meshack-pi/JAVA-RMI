package com.cat2.task3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EchoService extends Remote {

    String echo(String text) throws RemoteException;
}
