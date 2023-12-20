package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class NewTests {

    private String url = "https://app-ift.sbercrm.com/#/login";

    @BeforeAll
    static void browserSettings() {
        Configuration.browser = "FIREFOX";
        Configuration.browserSize = "1920x1200";
    }

    @Test
    public void firstTest() {
        Selenide.open(url);
        sleep(2000);
        $("input[name=\"username\"]").setValue("aedivanov@sberbank.ru");
        $("input[name=\"password\"]").setValue("Lelikus1981asd");
        $("button[type=\"submit\"]").click();
        sleep(9000);
        $("button.MuiButtonBase-root:nth-child(6) > span:nth-child(1)").click();
        sleep(2000);
//        $(byText("Collapse")).shouldBe(Condition.visible);
        $(".MuiCollapse-wrapperInner > li:nth-child(3) > div:nth-child(1) " +
                "> div:nth-child(2) > p:nth-child(1)").click();
        sleep(2000);
        $("div.MuiGrid-root:nth-child(7) > div:nth-child(1)").click();
        sleep(2000);
        $(byText("Installed")).shouldBe(Condition.visible);
    }

    @Test
    public void secondTest() {
        Selenide.open(url);
        sleep(2000);
        $("input[name=\"username\"]").setValue("aedivanov@sberbank.ru");
        $("input[name=\"password\"]").setValue("Lelikus1981asd");
        $("button[type=\"submit\"]").click();
        sleep(9000);
        $("div.MuiButtonBase-root:nth-child(17) > div:nth-child(2) > span:nth-child(1)").click();
        $("div.MuiButtonBase-root:nth-child(4) > div:nth-child(1) > span:nth-child(1)").click();
        $("button.MuiButton-root:nth-child(3) > span:nth-child(1)").click();
        $("textarea.MuiInputBase-input:nth-child(1)").setValue("AutoTest");
        sleep(2000);
        $("input[name=\"invoice_type$erp\"]").click();
        $("input[name=\"invoice_type$erp\"]").setValue("Исходящий");
        sleep(2000);
    }
}
