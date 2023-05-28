package netology.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import netology.data.DBHelper;
import netology.data.DataHelper;
import netology.page.CreditPage;
import netology.page.DebitPage;
import netology.page.InitialPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


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
        driver = new ChromeDriver(options);
        driver.get("http://localhost:8080/");
        var initialPage = new InitialPage();
        initialPage.buyByCreditCard(driver);
    }

    @AfterEach
    void teardown() {
        driver.quit();
        if (driver == null) ;
    }

    @AfterEach
    void removeDB() {
        DBHelper.removeDB();
    }
    @Test
    @DisplayName("Успешная оплата")
    void shouldSuccessPay() {
       var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                driver,
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton(driver);
        creditPage.getOkMessage(driver);

        Assertions.assertEquals("APPROVED", DBHelper.getStatus());
    }
    @Test
    @DisplayName("Попытка оплаты заблокированной картой")
    void shouldTryPayWithDeclinedNumberCard() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                driver,
                DataHelper.getDeclinedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton(driver);
        creditPage.getErrorMessage(driver);

        Assertions.assertEquals("DECLINED", DBHelper.getStatus());
    }

    @Test
    @DisplayName("Поле Карта заполнено коротким номером")
    void shouldBeNotificationInvalidFormatCard() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                driver,
                DataHelper.getShortNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton(driver);
        creditPage.getNotificationInvalidFormat(driver);

        Assertions.assertTrue(DBHelper.isEmptyDB());
    }

    @Test
    @DisplayName("Поля месяца и года заполнены датой больше 5 лет от текущей")
    void shouldBeNotificationInvalidPeriodFutureDate() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getFutureDate();
        creditPage.enteringDataCard(
                driver,
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton(driver);
        creditPage.getNotificationInvalidFutureDate(driver);

        Assertions.assertTrue(DBHelper.isEmptyDB());
    }

    @Test
    @DisplayName("Поля месяца и года заполнены прошедшей датой")
    void shouldBeNotificationOverdueDate() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getPastDate();
        creditPage.enteringDataCard(
                driver,
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton(driver);
        creditPage.getNotificationOverdueDate(driver);

        Assertions.assertTrue(DBHelper.isEmptyDB());
    }

    @Test
    @DisplayName("Поле Месяц заполнено 1 цифрой")
    void shouldBeNotificationInvalidFormatMonth() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getShortMonth();
        creditPage.enteringDataCard(
                driver,
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton(driver);
        creditPage.getNotificationInvalidFormat(driver);

        Assertions.assertTrue(DBHelper.isEmptyDB());
    }

    @Test
    @DisplayName("Поле Месяц заполнено 1 цифрой")
    void shouldBeNotificationInvalidFormatMonthZero() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.geDoubleZeroMonth();
        creditPage.enteringDataCard(
                driver,
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton(driver);
        creditPage.getNotificationInvalidFormat(driver);

        Assertions.assertTrue(DBHelper.isEmptyDB());
    }

    @Test
    @DisplayName("Поле Год заполнено 1 цифрой")
    void shouldBeNotificationInvalidFormatYear() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getShortYear();
        creditPage.enteringDataCard(
                driver,
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton(driver);
        creditPage.getNotificationInvalidFormat(driver);

        Assertions.assertTrue(DBHelper.isEmptyDB());
    }

    @Test
    @DisplayName("Поле Владелец заполнено на кириллице")
    void shouldBeNotificationInvalidFormatOwner() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                driver,
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getOwnerCyrillic(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton(driver);
        creditPage.getNotificationInvalidFormat(driver);

        Assertions.assertTrue(DBHelper.isEmptyDB());
    }

    @Test
    @DisplayName("Поле Владелец заполнено слишком длинным значением (более 20 символов)")
    void shouldBeNotificationTooLong() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                driver,
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getTooLongOwner(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton(driver);
        creditPage.getNotificationInvalidFormat(driver);

        Assertions.assertTrue(DBHelper.isEmptyDB());
    }

    @Test
    @DisplayName("Поле Владелец заполнено спецсимволами")
    void shouldBeNotificationInvalidFormatOwnerWithSymbols() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                driver,
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getOwnerWithSymbol(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton(driver);
        creditPage.getNotificationInvalidFormat(driver);

        Assertions.assertTrue(DBHelper.isEmptyDB());
    }

    @Test
    @DisplayName("Поле Владелец заполнено цифрами")
    void shouldBeNotificationInvalidFormatOwnerWithNumbers() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                driver,
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getOwnerWithNumber(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton(driver);
        creditPage.getNotificationInvalidFormat(driver);

        Assertions.assertTrue(DBHelper.isEmptyDB());
    }

    @Test
    @DisplayName("Поле Владелец заполнено пробелами")
    void shouldBeNotificationInvalidFormatOwnerWithSpaces() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                driver,
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getOwnerWithSpaces(),
                DataHelper.getValidCvc()
        );
        creditPage.clickNextButton(driver);
        creditPage.getNotificationEmptyField(driver);

        Assertions.assertTrue(DBHelper.isEmptyDB());
    }

    @Test
    @DisplayName("Поле CVC/CVV заполнено коротким номером")
    void shouldBeNotificationInvalidFormatCvc() {
        var creditPage = new CreditPage(driver);
        var date = DataHelper.getValidDate();
        creditPage.enteringDataCard(
                driver,
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidOwner(),
                DataHelper.getShortCvc()
        );
        creditPage.clickNextButton(driver);
        creditPage.getNotificationInvalidFormat(driver);

        Assertions.assertTrue(DBHelper.isEmptyDB());
    }

    @Test
    @DisplayName("Попытка отправить пустую форму")
    void shouldBeNotificationEmptyField() {
        var creditPage = new CreditPage(driver);
        creditPage.clickNextButton(driver);
        creditPage.getNotificationEmptyField(driver);

        Assertions.assertTrue(DBHelper.isEmptyDB());
    }

}
