import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Quora {
    void crawler() throws IOException, InterruptedException {
        File file = new File("quora.txt");
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        FirefoxDriver driver = new FirefoxDriver();
        new WebDriverWait(driver, 30);

        driver.get("https://www.quora.com/search?q=iran+visit");
        driver.manage().window().maximize();
        for (int i = 0; i < 100; i++) {
            Thread.sleep(5000);
            driver.executeScript("window.scrollBy(0,3000)");
        }

        Thread.sleep(5000);

        AtomicInteger count = new AtomicInteger();
        driver.findElements(By.className("ui_qtext_rendered_qtext")).forEach(title -> {
            try {
                bw.write(title.getText().replaceAll("\n", "") + "\n");
                System.out.println(count.getAndIncrement());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.out.println("writes completed.");
        bw.flush();
        bw.close();
        driver.close();
    }
}
