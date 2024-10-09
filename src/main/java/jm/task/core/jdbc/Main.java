package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        User user = new User("Mosha", "Moshovich", (byte) 19);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        System.out.println("User с именем - " + user.getName() + " добавлен в базу данных");
        user = new User("Bosha", "Boshovich", (byte) 32);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        System.out.println("User с именем - " + user.getName() + " добавлен в базу данных");
        user = new User("Wosha", "Woshovich", (byte) 57);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        System.out.println("User с именем - " + user.getName() + " добавлен в базу данных");
        user = new User("Gosha", "Goshovich", (byte) 12);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        System.out.println("User с именем - " + user.getName() + " добавлен в базу данных");

        userService.getAllUsers().forEach(x -> System.out.println(x.toString()));
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
