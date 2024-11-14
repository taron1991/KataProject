package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {


        UserServiceImpl userService = new UserServiceImpl(new UserDaoJDBCImpl());
        /*userService.createUsersTable();
        userService.saveUser("mark", "iov", (byte) 23);
        userService.saveUser("oleg", "gromov", (byte) 53);
        userService.saveUser("nika", "soev", (byte) 23);
        userService.saveUser("semen", "pikin", (byte) 12);
        System.out.println(userService.getAllUsers());*/
        //userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
