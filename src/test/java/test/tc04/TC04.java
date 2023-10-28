package test.tc04;

/*

Test Steps:
1. Go to http://live.techpanda.org/
2. Click on �MOBILE� menu
3. In mobile products list , click on �Add To Compare� for 2 mobiles (Sony Xperia & Iphone)
4. Click on �COMPARE� button. A popup window opens
5. Verify the pop-up window and check that the products are reflected in it
Heading "COMPARE PRODUCTS" with selected products in it.
6. Close the Popup Windows

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
public class TC04 {

    public static void testTC04() {

        WebDriver driver = DriverFactory.getDriver();
        String mainWindowHandle = driver.getWindowHandle();

        try {
            //1. Go to http://live.techpanda.org/
            driver.get("http://live.techpanda.org/");

            //2. Click on �MOBILE� menu
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement mobileBtn = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".level0"))));
            mobileBtn.click();

            //3. In mobile products list , click on �Add To Compare� for 2 mobiles (Sony Xperia & Iphone)
            WebElement piSony = findProductElement(driver, "Sony Xperia");
            Assert.assertNotNull(piSony);

            piSony.findElement(By.cssSelector(".link-compare")).click();

            WebElement piIphone = findProductElement(driver, "IPhone");
            Assert.assertNotNull(piIphone);

            piIphone.findElement(By.cssSelector(".link-compare")).click();
            driver.findElement(By.cssSelector("button[title=Compare]")).click();

            //5. Verify the pop-up window and check that the products are reflected in it
            WebDriverWait popupWait = new WebDriverWait(driver, Duration.ofSeconds(20));
            popupWait.until(ExpectedConditions.numberOfWindowsToBe(2)); // Assuming the new popup is the second window

            Assert.assertEquals(driver.getWindowHandles().size(), 2);

            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(mainWindowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            //Heading "COMPARE PRODUCTS" with selected products in it.
            Assert.assertEquals(driver.findElements(By.cssSelector(".product-shop-row.top > td")).size(), 2);

            //7. Close popup.
            driver.close();
            driver.switchTo().window(mainWindowHandle);

            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
        driver.quit();
    }

    public static WebElement findProductElement(WebDriver driver, String product) {
        List<WebElement> productInfoElements = driver.findElements(By.cssSelector(".products-grid > li"));

        for (WebElement productInfoElement : productInfoElements) {
            WebElement productNameElement = productInfoElement.findElement(By.cssSelector(".product-name"));
            String productName = productNameElement.getText();
            if (productName.equalsIgnoreCase(product)) {
                return productInfoElement;
            }
        }
        return null;
    }
}