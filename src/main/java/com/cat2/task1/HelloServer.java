package com.cat2.task1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class HelloServer extends UnicastRemoteObject implements HelloService {

    public static final int REGISTRY_PORT = 1099;

    protected HelloServer() throws RemoteException {
        super();
    }

    @Override
    public String greet(String name) throws RemoteException {
        System.out.println("greet() invoked by: " + name);
        return name + ", how are you doing today?";
    }

    public static void main(String[] args) {
        try {
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(REGISTRY_PORT);
            } catch (RemoteException alreadyRunning) {
                registry = LocateRegistry.getRegistry(REGISTRY_PORT);
            }

            HelloServer serverObject = new HelloServer();
            registry.rebind("HelloService", serverObject);
            System.out.println("HelloServer is running on port " + REGISTRY_PORT + "...");
            Thread.currentThread().join(); // keep the server (and mvn exec:java) alive

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
