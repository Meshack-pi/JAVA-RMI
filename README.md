# Java RMI Exercise

This exercise tests whether you have understood the main steps involved in writing, compiling, and running a **Java RMI** application.

## Tasks

1. **Modify the example application** above such that the response of the invoked method is:

   > *"Your name, how are you doing today?"*

2. **In groups of 3** (each with a computer), implement the same **Java RMI** application such that the `client program` runs on one machine, the `server program` runs on another machine, and the `RMI Registry` is hosted on a third machine.

3. **In the same groups of 3**, modify the `client app` into a **JavaFX** application that displays all messages in real time. Improve the remote object's method such that the **JavaFX** application prompts the user to enter text, and then sends back the text with the prefix:

   > *"You entered: ..."*

4. **In the same groups of 3**, modify the **Java RMI** application such that the `server program` hosts a **MySQL** database table containing a list of students, and the `client program` invokes a method that retrieves the student details and displays them in a **JavaFX** list. Assume we have a table named `student_data` in the database, as shown below.

## `student_data` Table

| ID | NAME     | COURSE | SCORE | EMAIL              |
|----|----------|--------|-------|--------------------|
| 1  | Jackline | BBIT   | 85    | jackie@gmail.com   |
| 2  | Konni    | ICS    | 95    | konni@gmail.com    |
| 3  | Pamela   | CNS    | 90    | pam123@gmail.com   |

---

## Solution — running with Maven

This repo is a Maven project (Java 21). All dependencies (JavaFX, MySQL Connector/J) are pulled automatically; no SDK downloads needed. Build everything with:

```bash
mvn compile
```

### Task 1 — "Your name, how are you doing today?"

```bash
mvn exec:java@task1-server     # terminal 1
mvn exec:java@task1-client     # terminal 2 — prompts for your name
```

### Task 2 — three machines (client / server / registry)

The JDK's RMI registry only accepts `bind`/`rebind` from its own machine, so the
server cannot register into a registry on a third machine directly. Instead, the
registry machine runs `RegistryHost`, which *pulls* the stub from the server
(remote `lookup` is allowed) and rebinds it locally.

```bash
# Machine B (server) — advertise its own IP inside the stub:
mvn exec:java@task1-server -Djava.rmi.server.hostname=<machine-B-ip>

# Machine C (registry) — mirror the server's bindings:
mvn exec:java@task2-registry -Dserver.host=<machine-B-ip>

# Machine A (client) — look up via the registry machine:
mvn exec:java@task1-client -Dregistry.host=<machine-C-ip>
```

Make sure all machines are on the same network and port 1099 is open in any firewall.

### Task 3 — JavaFX echo client ("You entered: ...")

```bash
mvn exec:java@task3-server                                  # server machine
mvn javafx:run@task3-client -Dregistry.host=<server-ip>     # client machine
```

Type text in the field and press Send — the list shows your message and the
server's `You entered: ...` response in real time. Omit `-Dregistry.host` to
run everything on one machine.

### Task 4 — MySQL `student_data` → JavaFX list

First create the database on the server machine:

```bash
mysql -u root -p < db/schema.sql
```

Then:

```bash
# Server (DB credentials are set in StudentServer.java — edit DB_USER / DB_PASS there)
mvn exec:java@task4-server

# Client
mvn javafx:run@task4-client -Dregistry.host=<server-ip>
```