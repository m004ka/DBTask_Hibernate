package overridetech.jdbc.jpa;

import overridetech.jdbc.jpa.model.User;
import overridetech.jdbc.jpa.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Хорёк", "Девидсон", (byte) 25);
        System.out.println("User с именем – Хорёк добавлен в базу данных");

        userService.saveUser("Бумер", "Битый", (byte) 30);
        System.out.println("User с именем – Бумер добавлен в базу данных");

        userService.saveUser("Рустам", "Минниханов", (byte) 28);
        System.out.println("User с именем – Рустам добавлен в базу данных");

        userService.saveUser("Сбер", "Кот", (byte) 22);
        System.out.println("User с именем – Сбер добавлен в базу данных");

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();

        userService.closeConnection();
    }
}
