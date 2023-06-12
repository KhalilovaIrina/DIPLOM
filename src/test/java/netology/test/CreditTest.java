package netology.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import netology.data.DBHelper;
import netology.data.DataHelper;
import netology.page.CreditPage;
import netology.page.InitialPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


class CreditTest {

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
        initialPage.buyByCreditCard();
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
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        creditPage.waitOkMessage();

        Assertions.assertEquals("APPROVED", DBHelper.getStatusForCredit());
    }

    @Test
    @DisplayName("Попытка оплаты заблокированной картой")
    void shouldTryPayWithDeclinedNumberCard() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                DataHelper.getDeclinedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        creditPage.waitErrorMessage();

        Assertions.assertEquals("DECLINED", DBHelper.getStatusForCredit());
    }

    @Test
    @DisplayName("Попытка оплаты незарегистрированной картой")
    void shouldTryPayWithNotRegisteredCard() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                DataHelper.getRandomNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        creditPage.waitErrorMessage();
        creditPage.closeNotificationMessage();
        creditPage.assertOkMessageNotVisible();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Карта заполнено коротким номером")
    void shouldBeNotificationInvalidFormatCard() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                DataHelper.getShortNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        creditPage.assertNotificationInvalidFormat();


        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поля месяца и года заполнены датой больше 5 лет от текущей")
    void shouldBeNotificationInvalidPeriodFutureDate() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getFutureDate();
        creditPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        creditPage.assertNotificationInvalidDate();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поля месяца и года заполнены прошедшей датой")
    void shouldBeNotificationOverdueDate() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getPastDate();
        creditPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        if (date.getYear().equals(LocalDate.now().format(DateTimeFormatter.ofPattern("yy")))) {
            creditPage.assertNotificationInvalidDate();
        } else {
            creditPage.assertNotificationOverdueYear();
        }
        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Месяц заполнено 1 цифрой")
    void shouldBeNotificationInvalidFormatMonth() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getShortMonth();
        creditPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        creditPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Месяц заполнено нолями")
    void shouldBeNotificationInvalidFormatMonthZero() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.geDoubleZeroMonth();
        creditPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        creditPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Год заполнено 1 цифрой")
    void shouldBeNotificationInvalidFormatYear() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getShortYear();
        creditPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        creditPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Владелец заполнено на кириллице")
    void shouldBeNotificationInvalidFormatOwner() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getOwnerCyrillic(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        creditPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Владелец заполнено слишком длинным значением (более 20 символов)")
    void shouldBeNotificationTooLong() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getTooLongOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        creditPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Владелец заполнено спецсимволами")
    void shouldBeNotificationInvalidFormatOwnerWithSymbols() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getOwnerWithSymbol(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        creditPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Владелец заполнено цифрами")
    void shouldBeNotificationInvalidFormatOwnerWithNumbers() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getOwnerWithNumber(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        creditPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле Владелец заполнено пробелами")
    void shouldBeNotificationInvalidFormatOwnerWithSpaces() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getOwnerWithSpaces(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        creditPage.assertNotificationEmptyField();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Поле CVC/CVV заполнено коротким номером")
    void shouldBeNotificationInvalidFormatCvc() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getShortCvc()
        );
        creditPage.clickNextButton();
        creditPage.assertNotificationInvalidFormat();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Попытка отправить пустую форму")
    void shouldBeNotificationEmptyField() {
        var creditPage = new CreditPage(driver);
        creditPage.clickNextButton();
        creditPage.assertNotificationEmptyField();

        creditPage.assertInvalidFormatNotVisible();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());
    }

    @Test
    @DisplayName("Повторная отправка формы")
    void shouldBeNotErrorNotification() {
        var creditPage = new CreditPage(driver);
        creditPage.clickNextButton();
        creditPage.assertNotificationEmptyField();

        creditPage.assertInvalidFormatNotVisible();

        Assertions.assertTrue(DBHelper.isOrderEntityTableEmpty());

        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton();
        creditPage.waitOkMessage();

        creditPage.assertInvalidFormatNotVisible();
        creditPage.assertEmptyFieldsNotVisible();

        Assertions.assertEquals("APPROVED", DBHelper.getStatusForCredit());

    }
}
