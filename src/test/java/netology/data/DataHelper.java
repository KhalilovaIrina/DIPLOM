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
        var faker = new Faker();
        return faker.numerify("###############");
    }

    public static String getRandomNumberCard() {
        var faker = new Faker();
        return faker.numerify("################");
    }

    private static int range(int min, int max) {
        var random = new Random();
        var range = min + random.nextInt(max - min + 1);
        return range;
    }

    private static LocalDate validDate() {
        var range = range(12, 60);
        var valid = LocalDate.now().plusMonths(range);
        return valid;
    }

    private static LocalDate futureDate() {
        var invalid = LocalDate.now().plusMonths(61);
        return invalid;
    }

    private static LocalDate pastDate() {
        var invalid = LocalDate.now().minusMonths(range(1, 12));
        return invalid;
    }

    @Value
    public static class Date {
        String month;
        String year;
    }

    public static Date getValidDate() {
        var validDate = validDate();
        var month = validDate.format(DateTimeFormatter.ofPattern("MM"));
        var year = validDate.format(DateTimeFormatter.ofPattern("yy"));
        return new Date(month, year);
    }

    public static Date getFutureDate() {
        var futureDate = futureDate();
        var month = futureDate.format(DateTimeFormatter.ofPattern("MM"));
        var year = futureDate.format(DateTimeFormatter.ofPattern("yy"));
        return new Date(month, year);
    }

    public static Date getPastDate() {
        var pastDate = pastDate();
        var month = pastDate.format(DateTimeFormatter.ofPattern("MM"));
        var year = pastDate.format(DateTimeFormatter.ofPattern("yy"));
        return new Date(month, year);
    }

    public static Date getShortMonth() {
        var faker = new Faker();
        var month = faker.numerify("#");
        var year = validDate().format(DateTimeFormatter.ofPattern("yy"));
        return new Date(month, year);
    }

    public static Date geDoubleZeroMonth() {
        var month = "00";
        var year = LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        return new Date(month, year);
    }

    public static Date getShortYear() {
        var faker = new Faker();
        var month = validDate().format(DateTimeFormatter.ofPattern("MM"));
        var year = faker.numerify("#");
        return new Date(month, year);
    }

    public static String getValidOwner() {
        var faker = new Faker(new Locale("en"));

        var firstName = faker.name().firstName();
        var lastName = faker.name().lastName();

        return (firstName + " " + lastName);
    }

    public static String getTooLongOwner() {
        var lenght = range(21, 50);
        var generatedString = RandomStringUtils.random(lenght, "QWERTYUIOPASDFGHJKLZXCVBNM");

        return (generatedString);
    }

    public static String getOwnerCyrillic() {
        var faker = new Faker(new Locale("ru"));

        var firstName = faker.name().firstName();
        var lastName = faker.name().lastName();

        return (firstName + " " + lastName);
    }

    public static String getOwnerWithNumber() {
        var min = 1;
        var max = 20;
        var random = new Random();
        var length = min + random.nextInt(max - min + 1);

        var generatedString = RandomStringUtils.random(length, "1234567890");

        return generatedString;
    }

    public static String getOwnerWithSymbol() {
        var min = 1;
        var max = 20;
        var random = new Random();
        var length = min + random.nextInt(max - min + 1);

        var generatedString = RandomStringUtils.random(length, "!@#$%^&*()-=+{}[];:'<>,./?`~");
        return generatedString;
    }


    public static String getOwnerWithSpaces() {
        var min = 1;
        var max = 20;
        var random = new Random();
        var length = min + random.nextInt(max - min + 1);

        var generatedString = RandomStringUtils.random(length, " ");
        return generatedString;
    }

    public static String getValidCvc() {
        var faker = new Faker();
        return faker.numerify("###");
    }

    public static String getShortCvc() {
        var faker = new Faker();
        return faker.numerify("##");
    }

}
