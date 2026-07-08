package com.cat2.task1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class HelloClient {

    public static void main(String[] args) {
        String registryHost = System.getProperty("registry.host", "localhost");

        try {
            Registry registry = LocateRegistry.getRegistry(registryHost, HelloServer.REGISTRY_PORT);
            HelloService stub = (HelloService) registry.lookup("HelloService");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your name: ");
            String name = scanner.nextLine().trim();

            String response = stub.greet(name);
            System.out.println("Server response: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
