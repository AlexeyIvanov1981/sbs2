package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import ui.config.ConfigForTests;
import ui.pages.SBBOL;
import ui.pages.SberCRM;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class NewTests extends ConfigForTests {

    String newUrl;

    private final String TEST_USER_FOR_LOGIN_VIA_SBBOL = "test_user_inn3277300909_06";
    private final String TEST_USER_PASSWORD_VIA_SBBOL = "123456";
    private final String SMS_CODE_FOR_LOGIN_VIA_SBBOL = "11111";
    SBBOL sbbol = new SBBOL();
    SberCRM sberCRM = new SberCRM();


    @Test
    @DisplayName("002")
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
        sleep(2000);
        sberCRM.buttonPaymentMethods.click();
        sleep(2000);
        sberCRM.buttonTicketB2B.click();
        sleep(2000);
        sberCRM.buttonConnect.click();
        sleep(2000);
        //todo
        //refresh();
    }

    @Test
    @DisplayName("004 Verification of link creation and account creation for Instant Payments service (MP)")
    void checkMpLink() {

        // С расчетного счета компании (10 000)
        // подключение МП маин юзер для подключения Моментальных платежей
        sberCRM.openSberCrmLoginPage();
        sberCRM.buttonLogInSberBusinessId.click();
        sbbol.loginSbbol(TEST_USER_FOR_LOGIN_VIA_SBBOL, TEST_USER_PASSWORD_VIA_SBBOL);
        sleep(5000);
        sberCRM.connectMp();
        // закрыть окно с сервисами
        sberCRM.buttonClosePaymentMethods.click();
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
        clearCashAndCookies();
        Selenide.closeWebDriver();
        // открытие ссылки
        // авторизация в СББОЛе плательщиком
        open(newUrl);
        sbbol.loginSbbol("bus-anar", "123456");
        sbbol.textFieldSmsCode.setValue(SMS_CODE_FOR_LOGIN_VIA_SBBOL);
        sleep(10000);
        // создание платежного поручения
        $x("//*[contains(text(), 'Показать детали платежа')]").click();
        $x("//*[@data-test-id='paymentAmount__purpose--label']").shouldBe(Condition.visible);
        $x("//*[@data-test-id='__requestOTP--button']").click();
        sleep(2000);
        $x("//*[@data-test-id='__otp--input']").setValue(SMS_CODE_FOR_LOGIN_VIA_SBBOL).pressEnter();
        sleep(2000);
        $x("//*[@data-test-id='PaymentCreatorDetails__title']").shouldBe(Condition.visible);
        $x("//*[contains(text(),'После положительного ответа банка документ будет " +
                "переведён в статус \"Исполнен\"')]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("После положительного ответа банка документ будет переведён в статус \"Исполнен\""));
        clearCashAndCookies();
        Selenide.closeWebDriver();
        // отключение МП
        sberCRM.openSberCrmLoginPage();
        sberCRM.buttonLogInSberBusinessId.click();
        sbbol.loginSbbol(TEST_USER_FOR_LOGIN_VIA_SBBOL, TEST_USER_PASSWORD_VIA_SBBOL);
        sleep(9000);
        sberCRM.disconnectMp();
        clearCashAndCookies();
        Selenide.closeWebDriver();
    }

    @Test
    @DisplayName("006 Verification of link creation and account creation for Instant Payments service (KVK)")
    void checkKvkLink() {

        // Текущий лимит для покупки в рассрочку (1 000 000)
        // Подключить КВК
        sberCRM.openSberCrmLoginPage();
        sberCRM.buttonLogInSberBusinessId.click();
        sbbol.loginSbbol(TEST_USER_FOR_LOGIN_VIA_SBBOL, TEST_USER_PASSWORD_VIA_SBBOL);
        sleep(5000);
        sberCRM.navBarFormMarketPlace.click();
        // нажатие на кнопку "Способы оплаты"
        sberCRM.buttonPaymentMethods.click();
        // выбор тикета "Рассрочка для бизнеса"
        sberCRM.buttonTicketKVK.click();
        sberCRM.buttonConnect.click();
        sleep(3000);
        refresh();
        sleep(3000);
        $(By.xpath("//*[contains(text(),'Подключен')]"))
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Подключен"));
        // закрыть окно с сервисами
        sberCRM.buttonClosePaymentMethods.click();
        // открыть счет
        sberCRM.openInvoice();
        // создать счет
        sberCRM.creatingAnInvoiceForBus_Anar("1000000");
        sleep(6000);
        // Создание ссылки на оплату
        $x("//span[contains(text(),'Ссылка на оплату')]").click();
        newUrl = $x("/html/body/div[3]/div[3]/div/form/div[1]/div/div/div[2]/a")
                .shouldBe(Condition.visible, Duration.ofSeconds(9000))
                .getOwnText();
        clearCashAndCookies();
        Selenide.closeWebDriver();
        // открытие ссылки
        // авторизация в СББОЛе плательщиком
        open(newUrl);
        sbbol.loginSbbol("bus-anar", "123456");
        sbbol.textFieldSmsCode.setValue(SMS_CODE_FOR_LOGIN_VIA_SBBOL);
        sleep(9000);
        // выбор тикета и создание платежного поручения
        $x("//*[@value='CREDIT_LIMIT']").click();
        $x("//*[@id=\"root\"]/div[3]/div/div[2]").click();
        $x("//*[@type='submit']").click();
        $x("//*[@data-test-id='__requestOTP--button']")
                .shouldHave(Condition.visible, Duration.ofSeconds(10000))
                .click();
        $x("//*[@data-test-id='__otp--input']").setValue(SMS_CODE_FOR_LOGIN_VIA_SBBOL).pressEnter();
        $x("//*[@data-test-id='PaymentCreatorDetails__title']").shouldBe(Condition.visible);
        $x("//*[contains(text(),'После положительного ответа банка документ будет " +
                "переведён в статус \"Исполнен\"')]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("После положительного ответа банка документ будет переведён в статус \"Исполнен\""));
        /*
        или для проверки можно заменить предыдущую строку
        $x("//*[@data-test-id='PaymentCreatorDetails__text']").shouldBe(Condition.visible);
        */
        clearCashAndCookies();
        Selenide.closeWebDriver();
        // Отключение сервиса КВК
        sberCRM.openSberCrmLoginPage();
        sberCRM.buttonLogInSberBusinessId.click();
        sbbol.loginSbbol(TEST_USER_FOR_LOGIN_VIA_SBBOL, TEST_USER_PASSWORD_VIA_SBBOL);
        sleep(9000);
        sberCRM.disconnectMp();
        clearCashAndCookies();
        Selenide.closeWebDriver();
    }

    @Test
    @DisplayName("Проверка подключения сервиса Моментальные Платежи с подключением Договоры МэйнЮзер")
        // Проверка подключения сервиса Моментальные Платежи с подключением Договоры МэйнЮзер
    void CheckingConnectionOfMP_serviceWithoutConnectionContracts() {
        sberCRM.openSberCrmLoginPage();
        sberCRM.buttonLogInSberBusinessId.click();
        sbbol.loginSbbol(TEST_USER_FOR_LOGIN_VIA_SBBOL, TEST_USER_PASSWORD_VIA_SBBOL);
        sleep(6000);
        // подключение сервиса Договоры
        sberCRM.navBarFormMarketPlace.click();
        // Установка ERP успешно выполнена. Необходимо обновить страницу.
        sberCRM.buttonManagementAndAccounting.click();
        sleep(3000);
        $x("//*[contains(text(),'Сервис автоматизации бизнес-процессов компании')]").click();
        sberCRM.buttonConnect.click();
        $x("//*[contains(text(),'Установка ERP успешно выполнена. Необходимо обновить страницу.')]")
                .shouldBe(Condition.visible);
        sleep(5000);
        refresh();
        sberCRM.buttonClosePaymentMethods.click();
        // Подключение МП
        sberCRM.navBarFormMarketPlace.click();
        sberCRM.buttonPaymentMethods.click();
        sberCRM.buttonTicketB2B.click();
        sberCRM.buttonConnect.click();
        sleep(3000);
        refresh();
        // Отключение МП
        sberCRM.buttonDisconnect.click();
        refresh();
        sleep(3000);
        sberCRM.buttonClosePaymentMethods.click();
        // Отключение Договоров
        sberCRM.buttonManagementAndAccounting.click();
        sleep(3000);
        $x("//*[contains(text(),'Сервис автоматизации бизнес-процессов компании')]").click();
        sberCRM.buttonDisconnect.click();
        refresh();
        sberCRM.buttonConnect.should(Condition.visible);
        refresh();
        sleep(3000);
    }
}
