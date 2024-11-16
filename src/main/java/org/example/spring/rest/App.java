package org.example.spring.rest;

import org.example.spring.rest.configuration.MyConfig;
import org.example.spring.rest.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

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

        // 1. Получить всех пользователей и сохранить Session ID
        String sessionId = communication.getAllUsersAndSaveSessionId();
        System.out.println("Session ID: " + sessionId);

        // 2. Сохранить пользователя
        User saveUser = new User(3L, "James", "Brown", (byte) 27);
        String code1 = communication.saveUser(saveUser);
        System.out.println("Code 1: " + code1);

        // 3. Обновить пользователя
        User updateUser = new User(3L, "Thomas", "Shelby", (byte) 27);
        String code2 = communication.updateUser(updateUser);
        System.out.println("Code 2: " + code2);

        // 4. Удалить пользователя
        String code3 = communication.deleteUser(3L);
        System.out.println("Code 3: " + code3);


        // Объединяем коды (если необходимо)
        String fullCode = code1 + code2 + code3;
        System.out.println("Full Code: " + fullCode);

        context.close(); // Закрываем контекст Spring
    }
}
