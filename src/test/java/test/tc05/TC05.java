package test.tc05;

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

/* Verify can create an account in e-Commerce site and can share wishlist to other poeple using email.

Test Steps:

1. Go to http://live.techpanda.org/
2. Click on my account link
3. Click Create an Account link and fill New User information excluding the registered Email ID.
4. Click Register
5. Verify Registration is done. Expected account registration done.
6. Go to TV menu
7. Add product in your wish list - use product - LG LCD
8. Click SHARE WISHLIST
9. In next page enter Email and a message and click SHARE WISHLIST
10. Check wishlist is shared. Expected wishlist shared successfully.

 */

@Test
public class TC05 {

    public static void testTC05() {

        WebDriver driver = DriverFactory.getDriver();

        try {
            //1. Go to http://live.techpanda.org/
            driver.get("http://live.techpanda.org/");

            //2. Click on my account link
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement accBtn = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a"))));
            accBtn.click();

            driver.findElement(By.cssSelector(".skip-content.skip-active * a[title=Register]")).click();

            //3. Click Create an Account link and fill New User information excluding the registered Email ID.
            new RegisterInput(driver)
                    .inputFirstName("Bao")
                    .inputLastName("Tu")
                    .inputEmail("wickyplays@gmail.com")
                    .inputPassword("1234567")
                    .inputConfirmation("1234567");

            //4. Click Register
            driver.findElement(By.xpath("//*[@id=\"form-validate\"]/div[2]/button")).click();

            //5. Verify Registration is done. Expected account registration done.
            List<WebElement> successes = driver.findElements(By.cssSelector(".success-msg * span"));
            Assert.assertFalse(successes.isEmpty());

            String text = successes.get(0).getText();
            Assert.assertTrue(text.equalsIgnoreCase("Thank you for registering with Main Website Store."));

            //6. Go to TV menu
            driver.findElement(By.cssSelector(".level0.nav-2")).click();

            //7. Add product in your wish list - use product - LG LCD
            WebElement tvLG = findProductElement(driver, "LG LCD");
            Assert.assertNotNull(tvLG);
            tvLG.findElement(By.cssSelector(".link-wishlist")).click();

            //8. Click SHARE WISHLIST
            driver.findElement(By.cssSelector("button[name=save_and_share]")).click();

            //9. In next page enter Email and a message and click SHARE WISHLIST
            driver.findElement(By.cssSelector("textarea#email_address")).sendKeys("nasdfdsfsfsaf@fpt.edu.vn");
            driver.findElement(By.cssSelector("textarea#message")).sendKeys("This is a test. Please don't mind me.");
            driver.findElement(By.xpath("//*[@id=\"form-validate\"]/div[2]/button")).click();

            //Verify if the wishlist has been shared.
            if (driver.findElements(By.cssSelector(".success-msg")).isEmpty()) {
                throw new AssertionError("The wishlist has not been shared yet!");
            } else {
                System.out.println("The wishlist has been shared!");
            }

            Thread.sleep(3000);
        } catch (Exception e){
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