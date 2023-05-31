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
    private By cardNumber = By.xpath("//span[contains(text(),'Номер карты')]/following::input");
    private By month = By.xpath("//span[contains(text(),'Месяц')]/following::input");
    private By year = By.xpath("//span[contains(text(),'Год')]/following::input");
    private By owner = By.xpath("//span[contains(text(),'Владелец')]/following::input");
    private By cvc = By.xpath("//span[contains(text(),'CVC/CVV')]/following::input");
    private By nextButton = By.xpath("//span[text()='Продолжить']");
    private By messageOk = By.className("notification_status_ok");
    private By messageError = By.className("notification_status_error");
    private By notificationInvalidFormat = By.xpath("//span[text()='Неверный формат']");
    private By notificationEmptyField = By.xpath("//span[text()='Поле обязательно для заполнения']");
    private By notificationInvalidFutureDate = By.xpath("//span[text()='Неверно указан срок действия карты']");
    private By notificationOverdueDate = By.xpath("//span[text()='Истёк срок действия карты']");
    private By messageCloseButton = By.className("icon_name_close");
    private By notification = By.className("input__sub");
    private HashMap<String, WebElement> webElements(WebDriver driver) {
        HashMap<String, WebElement> elements = new HashMap<String, WebElement>();
        elements.put("cardNumber", driver.findElement(cardNumber));
        elements.put("month", driver.findElement(month));
        elements.put("year", driver.findElement(year));
        elements.put("owner", driver.findElement(owner));
        elements.put("cvc", driver.findElement(cvc));
        elements.put("nextButton", driver.findElement(nextButton));
        return elements;
    }

    public void enteringDataCard(WebDriver driver, String number, String month, String year, String owner, String cvc) {
        webElements(driver).get("cardNumber").sendKeys(number);
        webElements(driver).get("month").sendKeys(month);
        webElements(driver).get("year").sendKeys(year);
        webElements(driver).get("owner").sendKeys(owner);
        webElements(driver).get("cvc").sendKeys(cvc);
    }

    public void getNotificationInvalidFormat(WebDriver driver) {
        WebElement notification = driver.findElement(notificationInvalidFormat);
        notification.isDisplayed();
    }

    public void getNotificationInvalidFutureDate(WebDriver driver) {
        WebElement notification = driver.findElement(notificationInvalidFutureDate);
        notification.isDisplayed();
    }

    public void getNotificationOverdueDate(WebDriver driver) {
        WebElement notification = driver.findElement(notificationOverdueDate);
        notification.isDisplayed();
    }

    public void getNotificationEmptyField(WebDriver driver) {
        WebElement notification = driver.findElement(notificationEmptyField);
        notification.isDisplayed();
    }


    public void clickNextButton(WebDriver driver) {
        webElements(driver).get("nextButton").click();
    }

    public void getOkMessage(WebDriver driver) {
        WebElement ok = driver.findElement(messageOk);

        new WebDriverWait(driver, 15, 0)
                .until(ExpectedConditions.visibilityOf(ok));
    }

    public void getOkMessageNotVisible(WebDriver driver) {
        WebElement buttonClose = driver.findElement(messageCloseButton);
        buttonClose.click();
        assertTrue(isElementNotVisible(messageOk, driver));
    }
    public void getErrorMessageNotVisible(WebDriver driver) {
        WebElement buttonClose = driver.findElement(messageCloseButton);
        buttonClose.click();
        assertTrue(isElementNotVisible(messageError, driver));
    }
    public void geInvalidFormatNotVisible(WebDriver driver) {
        assertTrue(isElementNotVisible(notificationInvalidFormat, driver));
    }
    public void getNotificationNotVisible(WebDriver driver) {
        assertTrue(isElementNotVisible(notification, driver));
    }


    @SneakyThrows
    public boolean isElementNotVisible(By by, WebDriver driver) {
        new WebDriverWait(driver, 10, 0)
                .until(ExpectedConditions.visibilityOf(driver.findElement(by)));
        return false;
    }

    public void getErrorMessage(WebDriver driver) {
        WebElement error = driver.findElement(messageError);

        new WebDriverWait(driver, 15, 0)
                .until(ExpectedConditions.visibilityOf(error));
    }


}
