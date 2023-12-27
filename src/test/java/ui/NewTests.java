package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import ui.pages.LoginPageSBBOL;
import ui.pages.LoginPageSberCRM;
import ui.pages.SberCRM;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class NewTests {

    private String url = "https://app-ift.sbercrm.com/#/login";
    String newUrl;
    LoginPageSberCRM loginPage = new LoginPageSberCRM();
    LoginPageSBBOL loginPageSBBOL = new LoginPageSBBOL();
    SberCRM sberCRM = new SberCRM();

    // executeJavaScript("window.open(\"http://www.google.com\");"); новая вкладка

    @BeforeAll
    static void browserSettings() {
        Configuration.browser = "FIREFOX";
        Configuration.browserSize = "1900x1070";
    }

    @Test
    @Disabled
    void checkAddMP() {
        // НЕ маин юзер для подключения Моментальных платежей
        Selenide.open(url);
        sleep(2000);
        loginPage.textFieldUserName.setValue("aedivanov@sberbank.ru");
        loginPage.textFieldPassword.setValue("Lelikus1981asd");
        loginPage.buttonChooseLanguage.click();
        loginPage.buttonRUSLanguage.click();
        loginPage.buttonSubmit.click();
        sleep(3000);
        $x("//div[@id='scrollContainer']//div[2]//button[5]").click();
        $("li:nth-child(7) div:nth-child(1) div:nth-child(2) p:nth-child(1)").click();
        sleep(2000);
        $(By.xpath("//p[contains(text(),'Моментальные платежи B2B')]")).click();
        sleep(2000);
        $(By.xpath("//span[contains(text(),'Установить')]")).click();
        sleep(2000);
        //todo
        //refresh();
    }

    @Test
    @Disabled
    @DisplayName("Connect and disconnect KVK with mainUser")
    void registerBySbbolForKvk() {
        // маин юзер для подключения Кредита в корзине
        Selenide.open(url);
        loginPage.buttonLogInBySBBOL.click();
        loginPageSBBOL.textFieldLogin.setValue("test_user_inn3277300909_06");
        loginPageSBBOL.textFieldPassword.setValue("123456");
        loginPageSBBOL.buttonNext.click();
        sleep(5000);
        sberCRM.navBarFormMarketPlace.click();
        // нажатие на кнопку "Способы оплаты"
        $("//div[@class='MuiCollapse-wrapperInner']//li[7]").click();
        // выбор тикета "Рассрочка для бизнеса"
        $(By.xpath("//p[contains(text(),'Рассрочка для бизнеса')]")).click();
        $(By.xpath("//span[contains(text(),'Установить')]")).click();
        sleep(1000);
        refresh();
        sleep(3000);
        $(By.xpath("//span[contains(text(),'Отключить')]")).click();
        refresh();
        sleep(2000);
        Selenide.closeWebDriver();
        $(ByText.cssSelector("")).click();
    }

    @Test
    @DisplayName("Connect B2B with mainUser")
    void registerBySbbolForMp() {
        // маин юзер для подключения Моментальных платежей
        Selenide.open(url);
        loginPage.buttonLogInBySBBOL.click();
        loginPageSBBOL.textFieldLogin.setValue("test_user_inn3277300909_06");
        loginPageSBBOL.textFieldPassword.setValue("123456");
        loginPageSBBOL.buttonNext.click();
        sleep(4000);
        sberCRM.navBarFormMarketPlace.click();
        $("li:nth-child(7) div:nth-child(1) div:nth-child(2) p:nth-child(1)").click();
        $(By.xpath("//p[contains(text(),'Моментальные платежи B2B')]")).click();
        $(By.xpath("//*[contains(text(),'Установить')]")).click();
        sleep(3000);
        refresh();
        sleep(3000);
        $(By.xpath("//*[contains(text(),'Подключен')]"))
                .shouldBe(Condition.visible);
//        $(By.xpath("//span[contains(text(),'Отключить')]")).shouldBe(Condition.visible);

        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWebDriver();
    }

    @Test
    @DisplayName("Verification of link creation and account creation for Instant Payments service")
    void checkMpLink() {

        // Авторизация через СББОЛ
        Selenide.open(url);
        $x("//*[contains(text(),'ОК')]").click();
        loginPage.buttonLogInBySBBOL.click();
        loginPageSBBOL.textFieldLogin.setValue("test_user_inn3277300909_06");
        loginPageSBBOL.textFieldPassword.setValue("123456");
        loginPageSBBOL.buttonNext.click();
//        loginPageSBBOL.buttonAccept.click();
//        loginPageSBBOL.TextFieldSmsCode.setValue("111111");

        // открыть счет
        $(By.xpath("//span[contains(text(),'Управление предприятием')]"))
                .shouldBe(Condition.visible, Duration.ofSeconds(9000))
                .click();
        $(By.xpath("//span[contains(text(),'Счета')]")).click();
        $(By.xpath("//span[contains(text(),'Новая запись')]")).click();

        // создать счет
        $x("//textarea[@placeholder='...']")
                .shouldBe(Condition.visible, Duration.ofSeconds(9000))
                .setValue("TestTest122");
        $x("//*[@name='invoice_type$erp']").hover().setValue("Исходящий");
        $x("//*[@class='MuiAutocomplete-listbox']//*[.='Исходящий']").click();
        $x("//*[@name='bank_details$erp']").click();
        $x("//*[@id='autocomplete-bank_details$erp-option-1']").click();
        $x("//*[@name='organization$erp']").click();
        $x("//*[@id='autocomplete-organization$erp-option-3']/div").click();
        $x("//*[@name='total_sum$erp']").hover();
        $x("//*[@name='total_sum$erp']/following-sibling::*//*[@title='Отключить расчет по формуле']").click();
        $x("//span[contains(text(),'ОК')]").click();
        $x("//*[@name='total_sum$erp']").setValue("1.25");
        $x("//body/div[@role='presentation']/div[@role='none presentation']/div[@role='dialog']" +
                "/form[@action='#']/div[@class='MuiDialogContent-root']/div[6]")
                .scrollIntoView("true");
        $x("//*[@name='vat_rate$erp']").hover().click();
        $x("//*[@class='MuiAutocomplete-listbox']//*[.='0%']").click();
        $x("//*[@name='total_with_vat$erp']").hover();
        $x("//*[@name='total_with_vat$erp']/following-sibling::*//*[@title='Отключить расчет по формуле']").click();
        $x("//span[contains(text(),'ОК')]").click();
        $x("//*[@name='total_with_vat$erp']").setValue("1.25");
        $x("//span[contains(text(),'Сохранить и перейти')]").click();

        // Создание ссылки на оплату
        $x("//span[contains(text(),'Ссылка на оплату')]")
                .shouldBe(Condition.visible, Duration.ofSeconds(9000))
                .click();
        newUrl = $x("/html/body/div[3]/div[3]/div/form/div[1]/div/div/div[2]/a")
                .shouldBe(Condition.visible, Duration.ofSeconds(9000))
                .getOwnText();
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWebDriver();

        // открытие ссылки
        // авторизация в СББОЛе плательщиком
        open(newUrl);
        loginPageSBBOL.textFieldLogin.setValue("bus-anar");
        loginPageSBBOL.textFieldPassword.setValue("123456");
        loginPageSBBOL.buttonNext.click();
        $x("//*[@data-test-id='smsCode--input']").setValue("111111");

        sleep(10000);
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWebDriver();
    }

    @Test
    @DisplayName("Disconnect B2B with mainUser")
    void turnOffBySbbolForMp() {
        // маин юзер для отключения Моментальных платежей
        Selenide.open(url);
        loginPage.buttonLogInBySBBOL.click();
        loginPageSBBOL.textFieldLogin.setValue("test_user_inn3277300909_06");
        loginPageSBBOL.textFieldPassword.setValue("123456");
        loginPageSBBOL.buttonNext.click();
        sleep(5000);
        sberCRM.navBarFormMarketPlace.click();
        $("li:nth-child(7) div:nth-child(1) div:nth-child(2) p:nth-child(1)").click();
        $(By.xpath("//p[contains(text(),'Моментальные платежи B2B')]")).click();
        $(By.xpath("//span[contains(text(),'Отключить')]")).click();
        sleep(5000);
        refresh();
        sleep(5000);
        $(By.xpath("//span[contains(text(),'Установить')]")).shouldBe(Condition.visible);

        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWebDriver();
    }
}
