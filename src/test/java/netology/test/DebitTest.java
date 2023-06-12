package netology.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import netology.data.DBHelper;
import netology.data.DataHelper;
import netology.page.DebitPage;
import netology.page.InitialPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


class DebitTest {

    static WebDriver driver;

    @BeforeAll
    static void setupAll() {

        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:8080/");
        var initialPage = new InitialPage(driver);
        initialPage.buyByDebitCard();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @AfterEach
    void removeDbTable() {
        DBHelper.clearDbTable();
    }


    @Test
    @DisplayName("Успешная оплата")
    void shouldSuccessPay() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.getValidDate();
        debitPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );

        debitPage.clickNextButton();
        debitPage.waitOkMessage();

        Assertions.assertEquals("APPROVED", DBHelper.getStatusForDebit());
    }

    @Test
    @DisplayName("Попытка оплаты заблокированной картой")
    void shouldTryPayWithDeclinedNumberCard() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.getValidDate();
        debitPage.enteringDataCard(
                DataHelper.getDeclinedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        debitPage.clickNextButton();
        debitPage.waitErrorMessage();

        Assertions.assertEquals("DECLINED", DBHelper.getStatusForDebit());
    }

    @Test
    @DisplayName("Попытка оплаты незарегистрированной картой")
    void shouldTryPayWithNotRegisteredCard() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.getValidDate();
        debitPage.enteringDataCard(
                DataHelper.getRandomNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        debitPage.clickNextButton();
        debitPage.waitErrorMessage();
        debitPage.closeNotificationMessage();
        debitPage.assertOkMessageNotVisible();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Карта заполнено коротким номером")
    void shouldBeNotificationInvalidFormatCard() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.getValidDate();
        debitPage.enteringDataCard(
                DataHelper.getShortNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        debitPage.clickNextButton();
        debitPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поля месяца и года заполнены датой больше 5 лет от текущей")
    void shouldBeNotificationInvalidPeriodFutureDate() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.getFutureDate();
        debitPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        debitPage.clickNextButton();
        debitPage.assertNotificationInvalidDate();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поля месяца и года заполнены прошедшей датой")
    void shouldBeNotificationOverdueDate() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.getPastDate();
        debitPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        debitPage.clickNextButton();
        if (date.getYear().equals(LocalDate.now().format(DateTimeFormatter.ofPattern("yy")))) {
            debitPage.assertNotificationInvalidDate();
        } else {
            debitPage.assertNotificationOverdueYear();
        }
        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Месяц заполнено 1 цифрой")
    void shouldBeNotificationInvalidFormatMonth() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.getShortMonth();
        debitPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        debitPage.clickNextButton();
        debitPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Месяц заполнено нолями")
    void shouldBeNotificationInvalidFormatMonthZero() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.geDoubleZeroMonth();
        debitPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        debitPage.clickNextButton();
        debitPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Год заполнено 1 цифрой")
    void shouldBeNotificationInvalidFormatYear() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.getShortYear();
        debitPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        debitPage.clickNextButton();
        debitPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Владелец заполнено на кириллице")
    void shouldBeNotificationInvalidFormatOwner() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.getValidDate();
        debitPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getOwnerCyrillic(),
                DataHelper.getValidCvc()
        );
        debitPage.clickNextButton();
        debitPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Владелец заполнено слишком длинным значением (более 20 символов)")
    void shouldBeNotificationTooLong() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.getValidDate();
        debitPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getTooLongOwner(),
                DataHelper.getValidCvc()
        );
        debitPage.clickNextButton();
        debitPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Владелец заполнено спецсимволами")
    void shouldBeNotificationInvalidFormatOwnerWithSymbols() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.getValidDate();
        debitPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getOwnerWithSymbol(),
                DataHelper.getValidCvc()
        );
        debitPage.clickNextButton();
        debitPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Владелец заполнено цифрами")
    void shouldBeNotificationInvalidFormatOwnerWithNumbers() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.getValidDate();
        debitPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getOwnerWithNumber(),
                DataHelper.getValidCvc()
        );
        debitPage.clickNextButton();
        debitPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Владелец заполнено пробелами")
    void shouldBeNotificationInvalidFormatOwnerWithSpaces() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.getValidDate();
        debitPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getOwnerWithSpaces(),
                DataHelper.getValidCvc()
        );
        debitPage.clickNextButton();
        debitPage.assertNotificationEmptyField();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле CVC/CVV заполнено коротким номером")
    void shouldBeNotificationInvalidFormatCvc() {
        var debitPage = new DebitPage(driver);
        var date = DataHelper.getValidDate();
        debitPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getShortCvc()
        );
        debitPage.clickNextButton();
        debitPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Попытка отправить пустую форму")
    void shouldBeNotificationEmptyField() {
        var debitPage = new DebitPage(driver);
        debitPage.clickNextButton();
        debitPage.assertNotificationEmptyField();

        debitPage.assertInvalidFormatNotVisible();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Повторная отправка формы")
    void shouldBeNotErrorNotification() {
        var debitPage = new DebitPage(driver);
        debitPage.clickNextButton();
        debitPage.assertNotificationEmptyField();

        debitPage.assertInvalidFormatNotVisible();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty(), "Таблица OrderEntityTable пуста");

        var date = DataHelper.getValidDate();
        debitPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        debitPage.clickNextButton();
        debitPage.waitOkMessage();

        debitPage.assertInvalidFormatNotVisible();
        debitPage.assertEmptyFieldsNotVisible();

        Assertions.assertEquals("APPROVED", DBHelper.getStatusForDebit());

    }

}
