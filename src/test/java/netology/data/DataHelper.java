package netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import org.apache.commons.lang3.RandomStringUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    public static String getApprovedNumberCard() {
        return ("4444 4444 4444 4441");
    }

    public static String getDeclinedNumberCard() {
        return ("4444 4444 4444 4442");
    }

    public static String getShortNumberCard() {
        Faker faker = new Faker();
        return faker.numerify("###############");
    }

    public static String getRandomNumberCard() {
        Faker faker = new Faker();
        return faker.numerify("################");
    }
    private static int range(int min, int max) {
        Random random = new Random();
        int range = min + random.nextInt(max - min + 1);
        return range;
    }
    private static LocalDate validDate() {
        int range = range(12, 60);
        LocalDate valid = LocalDate.now().plusMonths(range);
        return valid;
    }

    private static LocalDate futureDate() {
        //int range = range(61, 120);
        LocalDate invalid = LocalDate.now().plusMonths(61);
        return invalid;
    }

    private static LocalDate pastDate() {
        int range = range(1, 12);
        LocalDate invalid = LocalDate.now().minusMonths(range);
        return invalid;
    }

    @Value
    public static class Date {
        String month;
        String year;
    }

    public static Date getValidDate() {
        String month = validDate().format(DateTimeFormatter.ofPattern("MM"));
        String year = validDate().format(DateTimeFormatter.ofPattern("yy"));
        return new Date(month, year);
    }

    public static Date getFutureDate() {
        String month = futureDate().format(DateTimeFormatter.ofPattern("MM"));
        String year = futureDate().format(DateTimeFormatter.ofPattern("yy"));
        return new Date(month, year);
    }

    public static Date getPastDate() {
        String month = pastDate().format(DateTimeFormatter.ofPattern("MM"));
        String year = pastDate().format(DateTimeFormatter.ofPattern("yy"));
        return new Date(month, year);
    }

    public static Date getShortMonth() {
        Faker faker = new Faker();
        String month = faker.numerify("#");
        String year = validDate().format(DateTimeFormatter.ofPattern("yy"));
        return new Date(month, year);
    }

    public static Date geDoubleZeroMonth() {
        String month = "00";
        String year = LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        return new Date(month, year);
    }

    public static Date getShortYear() {
        Faker faker = new Faker();
        String month = validDate().format(DateTimeFormatter.ofPattern("MM"));
        String year = faker.numerify("#");
        return new Date(month, year);
    }

    public static String getValidOwner() {
        Faker faker = new Faker(new Locale("en"));

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        return (firstName + " " + lastName);
    }

    public static String getTooLongOwner() {
        int lenght = range(21, 50);
        String generatedString = RandomStringUtils.random(lenght, "QWERTYUIOPASDFGHJKLZXCVBNM");

        return (generatedString);
    }

    public static String getOwnerCyrillic() {
        Faker faker = new Faker(new Locale("ru"));

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        return (firstName + " " + lastName);
    }

    public static String getOwnerWithNumber() {
        int min = 1;
        int max = 20;
        Random random = new Random();
        int length = min + random.nextInt(max - min + 1);

        String generatedString = RandomStringUtils.random(length, "1234567890");

        return generatedString;
    }

    public static String getOwnerWithSymbol() {
        int min = 1;
        int max = 20;
        Random random = new Random();
        int length = min + random.nextInt(max - min + 1);

        String generatedString = RandomStringUtils.random(length, "!@#$%^&*()-=+{}[];:'<>,./?`~");
        return generatedString;
    }


    public static String getOwnerWithSpaces() {
        int min = 1;
        int max = 20;
        Random random = new Random();
        int length = min + random.nextInt(max - min + 1);

        String generatedString = RandomStringUtils.random(length, " ");
        return generatedString;
    }

    public static String getValidCvc() {
        Faker faker = new Faker();
        return faker.numerify("###");
    }

    public static String getShortCvc() {
        Faker faker = new Faker();
        return faker.numerify("##");
    }

}
