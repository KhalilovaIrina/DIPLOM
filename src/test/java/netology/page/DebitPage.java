package netology.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DebitPage extends FormPage {
    private By header = By.xpath("//h3[text()='Оплата по карте']");


    public DebitPage(WebDriver driver) {
        WebElement headerElement = driver.findElement(header);
        headerElement.isDisplayed();
    }
}
