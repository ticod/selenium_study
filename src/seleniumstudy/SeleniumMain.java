package seleniumstudy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumMain {

    private WebDriver driver;
    private WebElement webElement;

    public SeleniumMain() {
        System.setProperty("webdriver.chrome.driver",
                "D:\\goodee\\selenium_study\\modules\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    public static void main(String[] args) {
        SeleniumMain main = new SeleniumMain();
        main.crawl();
    }

    private void crawl() {
        try {
            driver.get("http://localhost:8081/model2/member/loginform.me");
            webElement = driver.findElement(By.name("id"));
            webElement.sendKeys("admin");
            webElement = driver.findElement(By.name("pass"));
            webElement.sendKeys("1234");
            webElement = driver.findElement(By.name("f"));
            webElement.submit();
            Thread.sleep(500);

            Alert alert = driver.switchTo().alert();
            alert.accept();

            /*
            / : 문서 노드, 루트 노드

            html : 루트 태그
            body : body 태그

            / : 자식 노드
            // : 자손 노드 (모든 자식)

            a[@href='list.me'] : a태그이고,
                @ 속성 의미
                @href : href 속성의 값이 list.me

             */
            webElement = driver.findElement(By.xpath("/html/body//a[@href='list.me']"));
            webElement.click();
            Thread.sleep(500);

            
            driver.get("http://localhost:8081/model2/member/list.me");
            Document doc = Jsoup.parse(driver.getPageSource());
            Elements table = doc.select("table");
            for (Element e : table) {
                for (Element tr : e.select("tr")) {
                    Elements tds = tr.select("td");
                    if (tds.size() > 3) {
                        System.out.println(tds.get(2).html());
                    }
                }
            }
            Thread.sleep(500);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

}
