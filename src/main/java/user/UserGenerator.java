package user;

import com.github.javafaker.Faker;
import java.time.ZoneId;
import java.util.Locale;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class UserGenerator {

    public static Faker faker;

    public UserGenerator(Locale locale) {
        faker = new Faker(locale);
    }

    public  User randomUser(){

        return User.builder()
                .username(faker.name().username())
                .password(md5Hex(faker.internet().password()))
                .name(faker.name().name())
                .email(faker.internet().emailAddress())
                .gender(faker.options().option(User.Gender.values()))
                .birthdate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .enabled(faker.bool().bool())
                .build();
    }

}
