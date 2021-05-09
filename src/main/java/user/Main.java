package user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        UserGenerator generator = new UserGenerator(new Locale("EN", "INDIA"));
        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();

            User user1 = generator.randomUser();
            User user2 = generator.randomUser();
            User user3 = generator.randomUser();
            User user4 = generator.randomUser();

            long id1 = dao.insertUser(user1);
            long id2 = dao.insertUser(user2);
            long id3 = dao.insertUser(user3);
            long id4 = dao.insertUser(user4);

            dao.listUsers().forEach(System.out::println);

            System.out.println("DELETE");

            dao.delete(user2);
            dao.listUsers().forEach(System.out::println);

            System.out.println("FIND BY ID");
            dao.findUserById(4).ifPresent(System.out::println);

            System.out.println("FIND BY USERNAME");
            dao.findUserByUserName(user1.getUsername()).ifPresent(System.out::println);
        }
    }
}
