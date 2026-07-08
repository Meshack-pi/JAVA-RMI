package com.cat2.task2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

/**
 * Task 2: runs on the third machine to host the RMI Registry.
 *
 * The JDK registry only accepts bind/rebind from the machine it runs on,
 * so the server cannot register itself here directly. Instead this program
 * looks the stubs up from the server's own registry (remote lookup is
 * allowed) and rebinds them into the local registry it hosts. Clients then
 * point at this machine and receive stubs that connect straight to the server.
 *
 * Usage: mvn exec:java@task2-registry -Dserver.host=<server-machine-ip>
 */
public class RegistryHost {

    public static final int REGISTRY_PORT = 1099;

    public static void main(String[] args) throws Exception {
        String serverHost = System.getProperty("server.host", "localhost");

        Registry localRegistry = LocateRegistry.createRegistry(REGISTRY_PORT);
        Registry serverRegistry = LocateRegistry.getRegistry(serverHost, REGISTRY_PORT);

        for (String name : serverRegistry.list()) {
            localRegistry.rebind(name, serverRegistry.lookup(name));
            System.out.println("Mirrored '" + name + "' from " + serverHost);
        }

        System.out.println("RMI Registry running on port " + REGISTRY_PORT
                + ", serving: " + Arrays.toString(localRegistry.list()));
        Thread.currentThread().join(); // keep the registry alive
    }
}
