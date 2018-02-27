package generic;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExReports implements IAuoConst {
	WebDriver driver;
	ExtentReports reports;
	ExtentTest tests;

	static {
		System.setProperty(CHROME_KEY, CHROME_VALUE);
	}
	
	@BeforeSuite(alwaysRun = true)
	public void init()
	{	
		String path = "./Reports/MyReport.html";
		reports=new ExtentReports(path);
	}
	
	@BeforeMethod(alwaysRun = true)
	public void preCondition(Method testName)
	{
		String name = testName.getName();
		tests=reports.startTest(name);
		
		driver = new ChromeDriver();
		//driver.manage().window().maximize();
		String url = Lib.pptFile(CONFIG_FILE, "URL");
		driver.get(url);
		String time = Lib.pptFile(CONFIG_FILE, "ITO");
		long ITO = Long.parseLong(time);
		driver.manage().timeouts().implicitlyWait(ITO, TimeUnit.SECONDS);
	}
	
	@AfterMethod(alwaysRun = true)
	public void postCondition(ITestResult res, Method method)
	{
		int s = res.getStatus();
		if(s==1)
			tests.log(LogStatus.PASS, "pass");
		else if(s!=1) {
			tests.log(LogStatus.FAIL, "faill");
						
			try {
				String dateTime = new Date().toString().replaceAll(":", "_");
				TakesScreenshot t = (TakesScreenshot) driver;
				File srcFile = t.getScreenshotAs(OutputType.FILE);
				File dstFile = new File("./Results/res1" + method.getName()+dateTime+".png");
				FileUtils.copyFile(srcFile, dstFile);
				} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		reports.endTest(tests);
		driver.close();
	}
	
	@AfterSuite(alwaysRun = true)
	public void end()
	{
		reports.flush();
	}

}
