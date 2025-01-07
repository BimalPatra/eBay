package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import driver.DriverManager;

public class LogInPage {
	private WebDriver driver;
	
	By logo = By.id("gh-logo");
	
	public LogInPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean logIn(String url) {
		launchBrowser(url);
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
		try {
            wait.until(ExpectedConditions.presenceOfElementLocated(logo)); 
            return true;  
        } catch (Exception e) {
            return false; 
        }
	}
	
	private void launchBrowser(String url) {
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
}
