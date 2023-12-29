package ui.config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;

public class ConfigForTests {

    @BeforeAll
    static void browserSettings() {
        Configuration.browser = "FIREFOX";
        Configuration.browserSize = "1900x1070";
    }

    public void clearCashAndCookies() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }
}
