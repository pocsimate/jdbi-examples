package user;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(User.class)
public interface UserDao {

    @SqlUpdate("""
        CREATE TABLE user (
            id IDENTITY PRIMARY KEY,
            username VARCHAR UNIQUE NOT NULL,
            password VARCHAR NOT NULL,
            name VARCHAR NOT NULL,
            email VARCHAR NOT NULL,
            gender ENUM('FEMALE', 'MALE'),
            birthdate DATE NOT NULL,
            enabled BOOLEAN
        )
        """
    )
    void createTable();

    @SqlUpdate("INSERT INTO user (username, password, name, email, gender, birthdate, enabled) VALUES (:username, :password, :name, :email, :gender, :birthdate, :enabled)")
    @GetGeneratedKeys
    long insertUser(@BindBean User user);

    @SqlUpdate("DELETE FROM user WHERE username = :username")
    void delete(@BindBean User user);

    @SqlQuery("SELECT * FROM user WHERE id = :id")
    Optional<User> findUserById(@Bind("id") long id);

    @SqlQuery("SELECT * FROM user WHERE username = :username")
    Optional<User> findUserByUserName(@Bind("username") String username);

    @SqlQuery("SELECT * FROM user ORDER BY id")
    List<User> listUsers();
}
