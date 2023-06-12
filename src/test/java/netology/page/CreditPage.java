package netology.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreditPage extends FormPage {
    private By header = By.xpath("//h3[text()='Кредит по данным карты']");

    public CreditPage(WebDriver webDriver) {
        super(webDriver);
        var headerElement = webDriver.findElement(header);
        var isElementVisible = headerElement.isDisplayed();
        assertTrue(isElementVisible, "Отображена форма заявки для оплаты по кредитной карте");
    }

}
