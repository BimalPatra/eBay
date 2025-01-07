package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverManager {
	private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
		
	public  WebDriver getDriver(String browser, boolean headless) {
		if(driver.get() == null)
		   setDriver(browser,headless);

		return driver.get();
	}
	
	private  void setDriver(String browser, boolean headless) {
		WebDriver webDriver;
		switch(browser) {
			case "chrome":
				ChromeOptions options = new ChromeOptions();
				if(headless)
					options.addArguments("--headless");
				webDriver = new ChromeDriver(options);
				break;
			case "edge":
				webDriver = new EdgeDriver();
				break;
			default:
				throw new RuntimeException("browers name is not provided");
		}
		
		driver.set(webDriver);
	}
	
    public void quitDriver(WebDriver driver) {
    	if(driver != null) {
		    driver.quit();
		    driver =null;
    	}
    }	
}
