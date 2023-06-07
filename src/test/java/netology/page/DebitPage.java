package netology.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DebitPage extends FormPage {
    private By header = By.xpath("//h3[text()='Оплата по карте']");

    public DebitPage(WebDriver webDriver) {
        super(webDriver);
        var headerElement = webDriver.findElement(header);
        headerElement.isDisplayed();
    }


}
