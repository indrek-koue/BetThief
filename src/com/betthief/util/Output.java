
package com.betthief.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.sql.Driver;

public class Output {
    private static WebDriver driver;

    public static void startBrowser(double output) {

        if (driver == null)
            driver = new FirefoxDriver();

        driver.get("http://www.google.com");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.name("q"));
        element.clear();
        element.sendKeys("" + output);

    }

}
