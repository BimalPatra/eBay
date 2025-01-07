package EbayAddToKart;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import driver.DriverManager;
import pages.HomePage_eBay;
import pages.LogInPage;

public class AddToKart {
	WebDriver driver = null;
	DriverManager drvManager;
	
	public AddToKart() {
		drvManager= new DriverManager();
		driver = drvManager.getDriver("chrome",false);
	}	
	
	@BeforeTest
	public void logInTest() {
		LogInPage logIn = new LogInPage(driver);
		boolean bool = logIn.logIn( "https://www.ebay.com/");
		Assert.assertTrue(bool,"ebay page is not opening properly");
	}	
	
	@Test(description = "add first book to cart")
	public void AddBookToCart() {
		boolean bool;
		HomePage_eBay home = new HomePage_eBay(driver);
		bool = home.searchItem("book");
		
		Assert.assertTrue(bool,"search list is not coming");
		
		bool = home.addToCart(1);
		Assert.assertTrue(bool,"fail to add first book to cart");
	}
	
   @AfterTest
   public void closeBrowser() {
	   drvManager.quitDriver(driver);
   }
}
