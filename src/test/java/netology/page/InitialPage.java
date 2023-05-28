package netology.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class InitialPage {
    private By buyButton = By.xpath("//span[text()='Купить']");
    private By buyCreditButton = By.xpath("//span[text()='Купить в кредит']");


    public DebitPage buyByDebitCard(WebDriver driver) {
        WebElement buyButtonElement = driver.findElement(buyButton);
        buyButtonElement.click();
        return new DebitPage(driver);
    }

    public CreditPage buyByCreditCard(WebDriver driver) {
        WebElement buyButtonElement = driver.findElement(buyCreditButton);
        buyButtonElement.click();
        return new CreditPage(driver);
    }

}
