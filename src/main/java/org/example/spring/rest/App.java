package org.example.spring.rest;

import org.example.spring.rest.configuration.MyConfig;
import org.example.spring.rest.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context=
                new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication",Communication.class);

        String sessionId = communication.getAllUsersAndSaveSessionId();
        System.out.println("Session ID: " + sessionId);

        User saveUser = new User(3L, "James", "Brown", (byte) 27);
        String code1 = communication.saveUser(saveUser);
        System.out.println("Code 1: " + code1);

        User updateUser = new User(3L, "Thomas", "Shelby", (byte) 27);
        String code2 = communication.updateUser(updateUser);
        System.out.println("Code 2: " + code2);

        String code3 = communication.deleteUser(3L);
        System.out.println("Code 3: " + code3);


        String fullCode = code1 + code2 + code3;
        System.out.println("Full Code: " + fullCode);

        context.close();
    }
}
