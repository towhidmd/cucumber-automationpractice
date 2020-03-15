package helper;

import com.google.common.base.Strings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.fail;

public class CommonHelper {

    private static WebDriver driver;
    private static final String DEFAULT_BROWSER = "chrome";
    public static final int TIMEOUT_SECONDS_SIXTY = 60;
    public static final int TIMEOUT_SECONDS_DEFAULT = 5;
    public static final String APP_URL = "app.url";

    /**
     * Load WebDriver with default driver type Chrome if no DRIVER_TYPE passed
     * Set environment variable DRIVER_TYPE (ie. chrome, firefox, edge, ie, etc)
     * Set environment variable REMOTE=grid if want to run SeleniumGrid test in remote.
     */
    public static WebDriver loadWebDriver() {
        String driverType = getDriverType();
        if (Strings.isNullOrEmpty(driverType)) {
            driver = loadWebDriver(DriverType.CHROME);
            System.out.println("++++++++++ No driver type is set, loading default driver " + DEFAULT_BROWSER + " ++++++++++");
        } else {
            driver = loadWebDriver(DriverType.valueOf(driverType.toUpperCase()));
        }
        return driver;
    }

    public static WebDriver loadWebDriver(DriverType driverType) {
        String browser = driverType.name().toLowerCase();
        String remote = System.getenv("REMOTE");
        if (StringUtils.isEmpty(remote)) {
            remote = System.getProperty("REMOTE");
        }
        System.out.println("Environment: " + remote);
        if ("chrome".equalsIgnoreCase(browser)) {
            if (!Strings.isNullOrEmpty(remote) && remote.equalsIgnoreCase("grid")) {
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                capabilities.merge(chromeOptions());
                driver = loadRemoteWebDriver(capabilities, getEnvironmentProperty("grid.url"));
            } else {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions());

            }

        } else if ("firefox".equalsIgnoreCase(browser)) {
            if (!Strings.isNullOrEmpty(remote) && remote.equalsIgnoreCase("grid")) {
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                driver = loadRemoteWebDriver(capabilities, getEnvironmentProperty("grid.url"));
            } else {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions());

            }
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();

        } else if ("edge".equalsIgnoreCase(browser)) {
            WebDriverManager.edgedriver();
            driver = new EdgeDriver();

        } else if ("ie".equalsIgnoreCase(browser)) {
            WebDriverManager.iedriver();
            driver = new InternetExplorerDriver();

        } else {
            fail("Failed - please set a valid driver types: chrome, firefox, edge, ie"); //create enum
        }
        driver = driver;

        return driver;
    }

    public static WebDriver loadRemoteWebDriver(DesiredCapabilities ca, String gridUrl) {
        try {
            System.out.println("Running in grid mode with capabilities: " + ca);
            driver = new RemoteWebDriver(new URL(gridUrl), ca);
        } catch (Exception e) {
            fail("Failed to load RemoteWebDriver\n" + e);
        }
        return driver;
    }

    public static ChromeOptions chromeOptions() {
        Map<String, Object> chromePref = new HashMap<>();
        chromePref.put("download.default_directory", getSeleniumDownloadDirectoryPath());

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePref);
        options.addArguments("disable-plugins");
        options.addArguments("disable-extensions");
        options.addArguments("disable-popup-blocking");
        options.addArguments("disable-infobars");
        options.addArguments("start-maximized");
        options.addArguments("--no-sandbox");

        return options;
    }

    public static String getEnvironmentProperty(String propertyName) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(getEnvironment());
        String property = resourceBundle.getString(propertyName);
        if (Strings.isNullOrEmpty(property)) {
            fail("!!!!!!! Failed to retrieve property value of " + propertyName + ". Please verify property value in " + getEnvironment() + ".properties !!!!!!!");
        }
        return property;
    }

    public static String getEnvironment() {
        String defaultEnv = "test";
        String environment = System.getenv("ENVIRONMENT");
        if (StringUtils.isEmpty(environment)) {
            environment = defaultEnv; //default environment is dev
            System.out.println("!!!!!!!!!!!!!!! No environment is set, using default env: " + defaultEnv + " !!!!!!!!!!!!!!");
        }
        return environment.toLowerCase();
    }

    public static String getDriverType() {
        String driverType = null;
        try {
            driverType = System.getenv("DRIVER_TYPE");
            if (StringUtils.isEmpty(driverType)) {
                driverType = System.getProperty("DRIVER_TYPE");
            }
        } catch (Exception ignore) {
            System.out.println("!!!!!!! DRIVER_TYPE is not set for the test to run. Please set DRIVER_TYPE = chrome, firefox, or ie !!!!!!!");
        }
        return driverType;
    }

    public static String getSeleniumDownloadDirectoryPath() {
        String defaultDownloadPath = SystemUtils.IS_OS_WINDOWS ? "C:\\selenium-tests-downloads\\" : System.getProperty("user.home") + "/selenium-tests-downloads/";
        File direcotry = new File(defaultDownloadPath);
        if (!direcotry.exists()) {
            direcotry.mkdir();
        }
        System.out.println("++++++++++ All Selenium Test downloads located: " + defaultDownloadPath + " ++++++++++");
        return defaultDownloadPath;
    }

    public static void setDriverTimeout(int timeoutSeconds) {
        driver.manage().timeouts().implicitlyWait(timeoutSeconds, TimeUnit.SECONDS);
    }

    /**
     * @param daysToAddOrDeduct
     * @param format
     * @return formatted date
     */
    public static String getDate(int daysToAddOrDeduct, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysToAddOrDeduct);
        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    /**
     * @return current date with mm/dd/yyyy format
     */
    public static String getDate() {
        return getDate(0, "MM/dd/yyyy");
    }

    /**
     * @param addDays
     * @return date with mm/dd/yyyy format
     */
    public static String getDate(int addDays) {
        return getDate(addDays, "MM/dd/yyyy");
    }

    /**
     * Take screenshot and save to default Selenium download directory path with file name {testMethodName-timeStamp}
     * Add this method wherever want to capture the screenshot. ie. on failure.
     */
    public static void takeScreenshot() {
        String filePath = getSeleniumDownloadDirectoryPath() + getScreenshotFileName();
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(filePath));
            System.out.println("+++++++++++++++ Screenshot captured: " + filePath + " +++++++++++++++");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getScreenshotFileName() {
        String testName = "";
        StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
        for (int i = 0; i < traceElements.length; i++) {
            if (traceElements[i].getFileName().contains("Test.java")) {
                testName = Thread.currentThread().getStackTrace()[i].getMethodName();
                break;
            }
        }
        return testName + "_" + new SimpleDateFormat("yyMMhhmmssSS").format(new Date()) + ".png";
    }
}
