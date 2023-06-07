package netology.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreditPage extends FormPage {
    private By header = By.xpath("//h3[text()='Кредит по данным карты']");

    public CreditPage(WebDriver webDriver) {
        super(webDriver);
        var headerElement = webDriver.findElement(header);
        headerElement.isDisplayed();
    }

}
