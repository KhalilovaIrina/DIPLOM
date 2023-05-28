package netology.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CreditPage extends FormPage {
    private By header = By.xpath("//h3[text()='Кредит по данным карты']");


    public CreditPage(WebDriver driver) {
        WebElement headerElement = driver.findElement(header);
        headerElement.isDisplayed();
    }
}
