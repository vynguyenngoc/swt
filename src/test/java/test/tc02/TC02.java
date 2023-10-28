package test.tc02;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Test
public class TC02 {

    public static void testTC02() {

        WebDriver driver = DriverFactory.getChromeDriver();
        try {
            driver.get("http://live.techpanda.org/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement mobileBtn = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".level0"))));
            mobileBtn.click();

            List<WebElement> productInfoElements = driver.findElements(By.cssSelector(".products-grid > li"));

            WebElement productInfo = null;
            String titlePrice = null;

            for (WebElement productInfoElement : productInfoElements) {
                WebElement productNameElement = productInfoElement.findElement(By.cssSelector(".product-name"));
                String productName = productNameElement.getText();
                if (productName.equalsIgnoreCase("Sony Xperia")) {
                    productInfo = productInfoElement;
                    titlePrice = productInfoElement.findElement(By.cssSelector("span.price")).getText();
                    break;
                }
            }

            if (productInfo != null && titlePrice != null) {

                WebElement productImage = productInfo.findElement(By.cssSelector(".product-image"));
                productImage.click();
                String actualTitlePrice = driver.findElement(By.cssSelector(".product-shop * .regular-price > .price")).getText();

                if (titlePrice.equals(actualTitlePrice)) {
                    System.out.println("Price values matched!");
                } else {
                    throw new AssertionError("Price values do not match!");
                }

            }

            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
        driver.quit();
    }
}
