package test.tc03;

/*
Test Steps:

1. Go to http://live.techpanda.org/
2. Click on �MOBILE� menu
3. In the list of all mobile , click on �ADD TO CART� for Sony Xperia mobile
4. Change �QTY� value to 1000 and click �UPDATE� button. Expected that an error is displayed
"The requested quantity for "Sony Xperia" is not available.
5. Verify the error message
6. Then click on �EMPTY CART� link in the footer of list of all mobiles. A message "SHOPPING CART IS EMPTY" is shown.
7. Verify cart is empty
*/

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

@Test
public class TC03 {

    public static void testTC03() {

        WebDriver driver = DriverFactory.getDriver();
        try {
            //1. Go to http://live.techpanda.org/
            driver.get("http://live.techpanda.org/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            //2. Click on �MOBILE� menu
            WebElement mobileBtn = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".level0"))));
            mobileBtn.click();

            //3. In the list of all mobile , click on �ADD TO CART� for Sony Xperia mobile
            List<WebElement> productInfoElements = driver.findElements(By.cssSelector(".products-grid > li"));
            WebElement productInfo = null;
            for (WebElement productInfoElement : productInfoElements) {
                WebElement productNameElement = productInfoElement.findElement(By.cssSelector(".product-name"));
                String productName = productNameElement.getText();
                if (productName.equalsIgnoreCase("Sony Xperia")) {
                    productInfo = productInfoElement;
                    break;
                }
            }

            Assert.assertNotNull(productInfo);

            WebElement productImage = productInfo.findElement(By.cssSelector(".btn-cart"));
            productImage.click();

            //4. Change �QTY� value to 1000 and click �UPDATE� button. Expected that an error is displayed
            //"The requested quantity for "Sony Xperia" is not available.
            WebElement inputQty = driver.findElement(By.xpath("//*[@id=\"shopping-cart-table\"]/tbody/tr/td[4]/input"));

            inputQty.sendKeys("1000");
            Thread.sleep(3000);
            inputQty.findElement(By.xpath("//*[@id=\"shopping-cart-table\"]/tbody/tr/td[4]/button")).click();

            //5. Verify the error message
            Assert.assertFalse(driver.findElements(By.cssSelector(".error-msg")).isEmpty());

            //6. Then click on �EMPTY CART� link in the footer of list of all mobiles. A message "SHOPPING CART IS EMPTY" is shown.
            driver.findElement(By.cssSelector("button#empty_cart_button")).click();

            //7. Verify cart is empty
            WebElement title = driver.findElement(By.cssSelector(".page-title > h1"));
            Assert.assertTrue(title.getText().equalsIgnoreCase("SHOPPING CART IS EMPTY"));

            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
        driver.quit();
    }
}