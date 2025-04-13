package org.example.capstone.testers;

import org.example.capstone.unused.OLD_User;

public class UserTester {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        OLD_User user = new OLD_User();
        user.setName("Test Testerson");
        user.setEmail("tester@example.com");
        System.out.printf("User name %s, email: %s\n", user.getName(), user.getEmail());
    }

}
