import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TripAdvisor {
    void crawler() throws IOException {
        File file = new File("tripAdvisor.txt");
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        FirefoxDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, 120);

        List<String> totalUrls = new ArrayList<>();

        driver.navigate().to("https://www.tripadvisor.co.uk/Search?q=iran%20tourism&searchSessionId=E06029801CB146644C52CE58C6D855421586085877124ssid&geo=293998&sid=7B5391083549DB87CA3AF64F291778CA1586086880706&blockRedirect=true&ssrc=f&o=30&rf=1");
        wait.until(ExpectedConditions.elementToBeClickable(By.className("forum-result-card")));
        driver.findElements(By.className("forum-result-card")).forEach(obj -> totalUrls.add(obj.getAttribute("onclick").split(",")[3]));
        for (int i = 2; i <= 8; i++) {
            String aboutButtonXpath = "a[data-page='" + i + "']";
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(aboutButtonXpath)));
            driver.findElement(By.cssSelector(aboutButtonXpath)).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.className("forum-result-card")));
            driver.findElements(By.className("forum-result-card")).forEach(obj -> totalUrls.add(obj.getAttribute("onclick").split(",")[3]));
        }

        System.out.println("totalUrls size: " + totalUrls.size());
        int count = 0;
        for (String url : totalUrls) {
            System.out.println(count);
            driver.navigate().to("https://www.tripadvisor.com" + url.substring(2));
            wait.until(ExpectedConditions.elementToBeClickable(By.className("postTitle")));

            bw.write(driver.findElement(By.className("postBody")).getText().replaceAll("\n", "") + "\n" +
                    driver.findElement(By.className("postTitle")).getText() + "\n" +
                    driver.findElement(By.className("postDate")).getText() + "\n" +
                    driver.findElement(By.className("username")).getText() + "\n" +
                    driver.findElement(By.className("location")).getText() + "\n");
            bw.flush();
            count++;
        }

        bw.flush();
        bw.close();
        driver.close();
    }
}
