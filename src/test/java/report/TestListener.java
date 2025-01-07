package report;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import driver.DriverManager;

public class TestListener implements ITestListener{
	private WebDriver driver = null;
	private DriverManager drvManager;
	private ExtentSparkReporter sparkReporter;
	private ExtentReports extent;
    private ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	
	public TestListener(){
		drvManager= new DriverManager();
		driver = drvManager.getDriver("chrome",false);
	}
	
    public void onStart(ITestContext context) {
		sparkReporter = new ExtentSparkReporter( "./reports/AutomationReport.html");
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("functional test");
		sparkReporter.config().setTheme(Theme.DARK);
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Browser Name", "chrome");
    }	
	
	public void onTestSuccess(ITestResult result) {
		extentTest.set(extent.createTest(result.getName()));
		extentTest.get().log(Status.PASS, "test is passed " + result.getName());
	}	
	public void onTestFailure(ITestResult result) {
    	extentTest.set(extent.createTest(result.getName()));
		String className = result.getTestClass().getName();
		String methodName = result.getName();
		extentTest.set(extent.createTest(className + " - " + methodName));
		extentTest.get().log(Status.FAIL, "test is failed " + result.getName());
		String screenshotPath = captureScreenshot(result.getName());
		extentTest.get().addScreenCaptureFromPath(screenshotPath);
    }
    
    private String captureScreenshot(String testName) {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        String screenshotDirectory = "./screenshots/";
        File directory = new File(screenshotDirectory);
        if (!directory.exists()) {
            directory.mkdirs(); 
        }

        String filePath = screenshotDirectory + testName + ".png";
        File destinationFile = new File(filePath);

        try {
            FileUtils.copyFile(screenshotFile, destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }
    
	public void onFinish(ITestContext context) {
		extent.flush();
		if(driver != null)
		  drvManager.quitDriver(driver);
	} 
}
