package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import ui.pages.SBBOL;
import ui.pages.SberCRM;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class NewTests {

    String newUrl;

    private final String testUserInnLogin = "test_user_inn3277300909_06";
    private final String testUserInnPassword = "123456";
    SBBOL sbbol = new SBBOL();
    SberCRM sberCRM = new SberCRM();


    @BeforeAll
    static void browserSettings() {
        Configuration.browser = "FIREFOX";
        Configuration.browserSize = "1900x1070";
    }

    @Test
    @DisplayName("remove the authorization agreement")
    void removeTheAuthorizationAgreement() {
        // удаления соглашения для правильной работы остальных тестов БУДЕТ ЧАСТО ПАДАТЬ!!!!
        sberCRM.openSberCrmLoginPage();
        sberCRM.buttonLogInSberBusinessId.click();
        sbbol.loginSbbol(testUserInnLogin, testUserInnPassword);
        sbbol.buttonAccept.click();
        sbbol.textFieldSmsCode.setValue("111111");
    }

    @Test
    @Disabled
    void checkAddMP() {
        // НЕ маин юзер для подключения Моментальных платежей
        sberCRM.openSberCrmLoginPage();
        sleep(2000);
        sberCRM.fillUserName("aedivanov@sberbank.ru");
        sberCRM.fillPassword("Lelikus1981asd");
        sberCRM.buttonChooseLanguage.click();
        sberCRM.buttonRUSLanguage.click();
        sberCRM.signIn();
        sleep(3000);
        sberCRM.navBarFormMarketPlace.click();
        sberCRM.buttonPaymentMethods.click();
        sleep(2000);
        sberCRM.buttonTicketB2B.click();
        sleep(2000);
        sberCRM.buttonConnectB2B.click();
        sleep(2000);
        //todo
        //refresh();
    }

    @Test
    @DisplayName("Connect B2B with mainUser")
    void connectMp() {
        // маин юзер для подключения Моментальных платежей
        sberCRM.openSberCrmLoginPage();
        sberCRM.buttonLogInSberBusinessId.click();
        sbbol.loginSbbol(testUserInnLogin, testUserInnPassword);
        sleep(4000);
        sberCRM.openMarketPlace();
        sberCRM.openPaymentMethods();
        sberCRM.buttonTicketB2B.click();
        $(By.xpath("//*[contains(text(),'Установить')]")).click();
        sleep(3000);
        refresh();
        sleep(3000);
        $(By.xpath("//*[contains(text(),'Подключен')]"))
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Подключен"));

        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWebDriver();
    }

    @Test
    @DisplayName("Verification of link creation and account creation for Instant Payments service")
    void checkMpLink() {

        // Авторизация через СББОЛ
        sberCRM.openSberCrmLoginPage();
        sberCRM.buttonLogInSberBusinessId.click();
        sbbol.loginSbbol(testUserInnLogin, testUserInnPassword);
//        loginPageSBBOL.buttonAccept.click();
//        loginPageSBBOL.TextFieldSmsCode.setValue("111111");

        // открыть счет
        sberCRM.openInvoice();

        // создать счет
        sberCRM.creatingAnInvoiceForBus_Anar("10000");
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
        sbbol.loginSbbol("bus-anar", "123456");
        sbbol.textFieldSmsCode.setValue("111111");
        sleep(10000);

        // создание платежного поручения
        $x("//*[contains(text(), 'Показать детали платежа')]").click();
        $x("//*[@data-test-id='paymentAmount__purpose--label']").shouldBe(Condition.visible);
        $x("//*[@data-test-id='__requestOTP--button']").click();
        sleep(2000);
        $x("//*[@data-test-id='__otp--input']").setValue("11111").pressEnter();
        sleep(2000);
        $x("//*[@data-test-id='PaymentCreatorDetails__title']").shouldBe(Condition.visible);
        $x("//*[contains(text(),'После положительного ответа банка документ будет " +
                "переведён в статус \"Исполнен\"')]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("После положительного ответа банка документ будет переведён в статус \"Исполнен\""));
        /*
        или для проверки можно заменить предыдущую строку
        $x("//*[@data-test-id='PaymentCreatorDetails__text']").shouldBe(Condition.visible);
        */
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWebDriver();
    }

    @Test
    @Disabled
    @DisplayName("Connect and disconnect KVK with mainUser")
    void registerBySbbolForKvk() {
        // маин юзер для подключения Кредита в корзине
        sberCRM.openSberCrmLoginPage();
        sberCRM.buttonLogInSberBusinessId.click();
        sbbol.loginSbbol(testUserInnLogin, testUserInnPassword);
        sleep(5000);
        sberCRM.navBarFormMarketPlace.click();
        // нажатие на кнопку "Способы оплаты"
        sberCRM.buttonPaymentMethods.click();
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
    @DisplayName("Disconnect B2B with mainUser")
    void disconnectMp() {
        // маин юзер для отключения Моментальных платежей
        sberCRM.openSberCrmLoginPage();
        sberCRM.buttonLogInSberBusinessId.click();
        sbbol.loginSbbol(testUserInnLogin, testUserInnPassword);
        sleep(9000);
        sberCRM.navBarFormMarketPlace.click();
        sberCRM.buttonPaymentMethods.click();
        sberCRM.buttonTicketB2B.click();
        $(By.xpath("//*[contains(text(),'Отключить')]")).click();
        sleep(4000);
        refresh();
        sleep(6000);
        $x("//*[contains(text(),'Установить')]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Установить"));

        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWebDriver();
    }
}
