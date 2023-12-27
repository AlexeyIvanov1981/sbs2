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

import static com.codeborne.selenide.Selenide.*;

public class NewTests {

    private final String urlIFT = "https://app-ift.sbercrm.com/#/login";
    String newUrl;

    private final String testUserInnLogin = "test_user_inn3277300909_06";
    private final String testUserInnPassword = "123456";
    LoginPageSberCRM loginPage = new LoginPageSberCRM();
    LoginPageSBBOL loginPageSBBOL = new LoginPageSBBOL();
    SberCRM sberCRM = new SberCRM();

    // executeJavaScript("window.open(\"http://www.google.com\");"); новая вкладка

    @BeforeAll
    static void browserSettings() {
        Configuration.browser = "FIREFOX";
//        Configuration.browserVersion = "114";
        Configuration.browserSize = "1900x1070";
    }

    @Test
    @Disabled
    void checkAddMP() {
        // НЕ маин юзер для подключения Моментальных платежей
        Selenide.open(urlIFT);
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
        Selenide.open(urlIFT);
        loginPage.buttonLogInBySBBOL.click();
        loginPageSBBOL.textFieldLogin.setValue(testUserInnLogin);
        loginPageSBBOL.textFieldPassword.setValue(testUserInnPassword);
        loginPageSBBOL.buttonNext.click();
        sleep(5000);
        sberCRM.navBarFormMarketPlace.click();
        // нажатие на кнопку "Способы оплаты"
        sberCRM.buttonPaymentMethods.click();
        /*
        или такой локатор (необходимо проверить)
        $x("//*[contains(text(),'Способы оплаты')]").click();
        */
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
        Selenide.open(urlIFT);
        loginPage.buttonLogInBySBBOL.click();
        loginPageSBBOL.textFieldLogin.setValue(testUserInnLogin);
        loginPageSBBOL.textFieldPassword.setValue(testUserInnPassword);
        loginPageSBBOL.buttonNext.click();
        sleep(4000);
        sberCRM.navBarFormMarketPlace.click();
        sberCRM.buttonPaymentMethods.click();
        sberCRM.buttonB2B.click();
        $(By.xpath("//*[contains(text(),'Установить')]")).click();
        sleep(3000);
        refresh();
        sleep(3000);
        $(By.xpath("//*[contains(text(),'Подключен')]"))
                .shouldBe(Condition.visible);

        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWebDriver();
    }

    @Test
    @DisplayName("Verification of link creation and account creation for Instant Payments service")
    void checkMpLink() {

        // Авторизация через СББОЛ
        Selenide.open(urlIFT);
        $x("//*[contains(text(),'ОК')]").click();
        loginPage.buttonLogInBySBBOL.click();
        loginPageSBBOL.textFieldLogin.setValue(testUserInnLogin);
        loginPageSBBOL.textFieldPassword.setValue(testUserInnPassword);
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
        sleep(6000);

        // Создание ссылки на оплату
        $x("//span[contains(text(),'Ссылка на оплату')]").click();
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

        // создание платежного поручения
        $x("//*[contains(text(), 'Показать детали платежа')]").click();
        $x("//*[@data-test-id='paymentAmount__purpose--label']").shouldBe(Condition.visible);
        $x("//*[@data-test-id='__requestOTP--button']").click();
        sleep(2000);
        $x("//*[@data-test-id='__otp--input']").setValue("11111").pressEnter();
        sleep(2000);
        $x("//*[@data-test-id='PaymentCreatorDetails__title']").shouldBe(Condition.visible);
        $x("//*[contains(text(),'После положительного ответа банка документ будет переведён в статус \"Исполнен\"')]")
                .shouldBe(Condition.visible);
        /*
        или для проверки можно заменить предыдущую строку
        $x("//*[@data-test-id='PaymentCreatorDetails__text']").shouldBe(Condition.visible);
        */
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWebDriver();
    }

    @Test
    @DisplayName("Disconnect B2B with mainUser")
    void turnOffBySbbolForMp() {
        // маин юзер для отключения Моментальных платежей
        Selenide.open(urlIFT);
        loginPage.buttonLogInBySBBOL.click();
        loginPageSBBOL.textFieldLogin.setValue(testUserInnLogin);
        loginPageSBBOL.textFieldPassword.setValue(testUserInnPassword);
        loginPageSBBOL.buttonNext.click();
        sleep(5000);
        sberCRM.navBarFormMarketPlace.click();
        sberCRM.buttonPaymentMethods.click();
        sberCRM.buttonB2B.click();
        $(By.xpath("//*[contains(text(),'Отключить')]")).click();
        sleep(2000);
        refresh();
        sleep(5000);
        $(By.xpath("//*[contains(text(),'Установить')]")).shouldBe(Condition.visible);

        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWebDriver();
    }
}
