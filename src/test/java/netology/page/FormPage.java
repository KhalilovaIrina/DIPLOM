package netology.page;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
    private By messageOk = By.className("notification_status_ok");
    private By messageError = By.className("notification_status_error");
    private By notificationInvalidFormat = By.xpath("//span[text()='Неверный формат']");
    private By notificationEmptyField = By.xpath("//span[text()='Поле обязательно для заполнения']");
    private By notificationInvalidDate = By.xpath("//span[text()='Неверно указан срок действия карты']");
    private By notificationInvalidYear = By.xpath("//span[text()='Истёк срок действия карты']");
    private By messageCloseButton = By.cssSelector(".notification_status_error button");


    public void enteringDataCard(String number, String month, String year, String owner, String cvc) {
        var cardNumberElement = this.webDriver.findElement(cardNumberField);
        cardNumberElement.sendKeys(number);

        var monthElement = this.webDriver.findElement(monthField);
        monthElement.sendKeys(month);

        var yearElement = this.webDriver.findElement(yearField);
        yearElement.sendKeys(year);

        WebElement ownerElement = this.webDriver.findElement(ownerField);
        ownerElement.sendKeys(owner);

        WebElement cvcElement = this.webDriver.findElement(cvcField);
        cvcElement.sendKeys(cvc);
    }

    public void getNotificationInvalidFormat() {
        var notification = this.webDriver.findElement(notificationInvalidFormat);
        var isElementVisible = notification.isDisplayed();
        assertTrue(isElementVisible);
    }

    public void getNotificationInvalidDate() {
        var notification = this.webDriver.findElement(notificationInvalidDate);
        var isElementVisible = notification.isDisplayed();
        assertTrue(isElementVisible);
    }

    public void getNotificationOverdueYear() {
        var notificationYear = this.webDriver.findElement(notificationInvalidYear);
        var isElementVisible = notificationYear.isDisplayed();
        assertTrue(isElementVisible);
    }

    public void getNotificationEmptyField() {
        var notification = this.webDriver.findElement(notificationEmptyField);
        var isElementVisible = notification.isDisplayed();
        assertTrue(isElementVisible);
    }


    public void clickNextButton() {
        var nextElement = this.webDriver.findElement(nextButton);

        nextElement.click();
    }

    public void getOkMessage() {
        var ok = this.webDriver.findElement(messageOk);

        new WebDriverWait(this.webDriver, 15, 0)
                .until(ExpectedConditions.visibilityOf(ok));
    }

    public void getErrorMessage() {
        var error = this.webDriver.findElement(messageError);

        new WebDriverWait(this.webDriver, 15, 0)
                .until(ExpectedConditions.visibilityOf(error));
    }

    public boolean getInvalidFormatNotVisible() {
        var notification = this.webDriver.findElement(notificationInvalidFormat);
        if (notification.isDisplayed()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean getEmptyFieldsNotVisible() {
        var notification = this.webDriver.findElement(notificationEmptyField);
        if (notification.isDisplayed()) {
            return false;
        } else {
            return true;
        }
    }

    public void closeNotificationMessage() {

        var wait = new WebDriverWait(this.webDriver, 10);
        var closeElement = wait.until(ExpectedConditions.elementToBeClickable(messageCloseButton));
        closeElement.click();
    }

    public boolean getOkMessageNotVisible() {
        var notification = this.webDriver.findElement(messageOk);
        if (notification.isDisplayed()) {
            return false;
        } else {
            return true;
        }
    }


}








