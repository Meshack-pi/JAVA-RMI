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