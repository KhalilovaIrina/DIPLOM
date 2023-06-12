package netology.page;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormPage {

    private WebDriver webDriver;

    public FormPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private By cardNumberField = By.xpath("//span[contains(text(),'Номер карты')]/following::input");
    private By monthField = By.xpath("//span[contains(text(),'Месяц')]/following::input");
    private By yearField = By.xpath("//span[contains(text(),'Год')]/following::input");
    private By ownerField = By.xpath("//span[contains(text(),'Владелец')]/following::input");
    private By cvcField = By.xpath("//span[contains(text(),'CVC/CVV')]/following::input");
    private By nextButton = By.xpath("//span[text()='Продолжить']");
    private By messageOk = By.cssSelector(".notification_status_ok .notification__title");
    private By messageError = By.cssSelector(".notification_status_error .notification__title");
    private By notificationInvalidFormat = By.xpath("//span[text()='Неверный формат']");
    private By notificationEmptyField = By.xpath("//span[text()='Поле обязательно для заполнения']");
    private By notificationInvalidDate = By.xpath("//span[text()='Неверно указан срок действия карты']");
    private By notificationInvalidYear = By.xpath("//span[text()='Истёк срок действия карты']");
    private By messageCloseButton = By.cssSelector(".notification_status_error button");


    public void enteringDataCard(String number, String month, String year, String owner, String cvc) {
        this.webDriver.findElement(cardNumberField).sendKeys(number);

        this.webDriver.findElement(monthField).sendKeys(month);

        this.webDriver.findElement(yearField).sendKeys(year);

        this.webDriver.findElement(ownerField).sendKeys(owner);

       this.webDriver.findElement(cvcField).sendKeys(cvc);
    }

    public void assertNotificationInvalidFormat() {
        var isElementVisible = this.webDriver.findElement(notificationInvalidFormat).isDisplayed();
        assertTrue(isElementVisible, "Отображается уведомление 'Неверный формат'");
    }



    public void assertNotificationInvalidDate() {
        var notification = this.webDriver.findElement(notificationInvalidDate);
        var isElementVisible = notification.isDisplayed();
        assertTrue(isElementVisible, "Отображается уведомление 'Неверно указан срок действия карты'");
    }

    public void assertNotificationOverdueYear() {
        var notificationYear = this.webDriver.findElement(notificationInvalidYear);
        var isElementVisible = notificationYear.isDisplayed();
        assertTrue(isElementVisible, "Отображается уведомление 'Истёк срок действия карты'");
    }

    public void assertNotificationEmptyField() {
        var notification = this.webDriver.findElement(notificationEmptyField);
        var isElementVisible = notification.isDisplayed();
        assertTrue(isElementVisible, "Поле обязательно для заполнения'");
    }


    public void clickNextButton() {
        this.webDriver.findElement(nextButton).click();
    }

    public void waitOkMessage() {
        var ok = this.webDriver.findElement(messageOk);

        new WebDriverWait(this.webDriver, 15, 0)
                .until(ExpectedConditions.visibilityOf(ok));
        String okText = ok.getText();
        Assertions.assertEquals(true, okText.contains("Успешно"), "Сообщение содержит текст 'Успешно'");
    }

    public void waitErrorMessage() {

        var error = this.webDriver.findElement(messageError);

        new WebDriverWait(this.webDriver, 15, 0)
                .until(ExpectedConditions.visibilityOf(error));
        String errorText = error.getText();
        Assertions.assertEquals(true, errorText.contains("Ошибка"), "Сообщение содержит текст 'Ошибка'");
    }


    public void assertInvalidFormatNotVisible() {
        var invalidFormatNotVisible = !this.webDriver.findElement(notificationInvalidFormat).isDisplayed();
        assertTrue(invalidFormatNotVisible, "Не отображается уведомление 'Неверный формат'");
    }

    public void assertEmptyFieldsNotVisible() {
        var emptyFieldsNotVisible = !this.webDriver.findElement(notificationEmptyField).isDisplayed();
        assertTrue(emptyFieldsNotVisible, "Не отображается уведомление 'Поле обязательно для заполнения'");
    }
    public void closeNotificationMessage() {

        var wait = new WebDriverWait(this.webDriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(messageCloseButton)).click();
    }

    public void assertOkMessageNotVisible() {
        var okMessageNotVisible = !this.webDriver.findElement(messageOk).isDisplayed();
        assertTrue(okMessageNotVisible, "Не отображается сообщение об успешной оплате");
    }
}








