package netology.page;

import lombok.Value;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class InitialPage {
    private WebDriver webDriver;

    public InitialPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private By buyButton = By.xpath("//span[text()='Купить']");
    private By buyCreditButton = By.xpath("//span[text()='Купить в кредит']");


    public DebitPage buyByDebitCard() {
        var buyButtonElement = this.webDriver.findElement(buyButton);
        buyButtonElement.click();
        return new DebitPage(this.webDriver);
    }

    public CreditPage buyByCreditCard() {
        var buyButtonElement = this.webDriver.findElement(buyCreditButton);
        buyButtonElement.click();
        return new CreditPage(this.webDriver);
    }

}
