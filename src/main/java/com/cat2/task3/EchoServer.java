package com.cat2.task3;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class EchoServer extends UnicastRemoteObject implements EchoService {

    public static final int REGISTRY_PORT = 1099;

    protected EchoServer() throws RemoteException {
        super();
    }

    @Override
    public String echo(String text) throws RemoteException {
        System.out.println("echo() received: " + text);
        return "You entered: " + text;
    }

    public static void main(String[] args) {
        try {
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(REGISTRY_PORT);
            } catch (RemoteException alreadyRunning) {
                registry = LocateRegistry.getRegistry(REGISTRY_PORT);
            }

            EchoServer serverObject = new EchoServer();
            registry.rebind("EchoService", serverObject);
            System.out.println("EchoServer is running on port " + REGISTRY_PORT + "...");
            Thread.currentThread().join(); // keep the server (and mvn exec:java) alive

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
