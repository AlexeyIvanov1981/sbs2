package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class SberCRM {

    private final String URL_IFT = "https://app-ift.sbercrm.com/#/login";

    public SelenideElement
            buttonSubmit = $("button[type='submit']"),
            buttonChooseLanguage = $("button[class='MuiButtonBase-root MuiIconButton-root']"),
            buttonRUSLanguage = $x("//*[contains(text(),'Русский')]"),
            textFieldUserName = $("input[name='username']"),
            textFieldPassword = $("input[name='password']"),
            buttonLogInSberBusinessId = $x("//span[contains(text(), 'Войти по СберБизнес ID')]"),
            navBarFormMarketPlace = $x("//*[@title='Маркетплейс']"),
            buttonPaymentMethods = $x("//*[contains(text(),'Способы оплаты')]"),
            buttonTicketB2B = $x("//p[contains(text(),'Моментальные платежи B2B')]"),
            buttonConnectB2B = $x("//span[contains(text(),'Установить')]"),
            buttonOkForCookies = $x("//*[@class='jss93']");

    public void openMarketPlace() {
        navBarFormMarketPlace.click();
    }

    public void openPaymentMethods() {
        buttonPaymentMethods.click();
    }

    public void openSberCrmLoginPage() {
        Selenide.open(URL_IFT);
        buttonOkForCookies.shouldBe(Condition.visible, Duration.ofSeconds(8000)).click();

    }

    public void fillUserName(String userName) {
        textFieldUserName.setValue(userName);
    }

    public void fillPassword(String password) {
        textFieldPassword.setValue(password);
    }

    public void signIn() {
        buttonSubmit.click();
    }

    public void creatingAnInvoiceForBus_Anar(String sum) {

        Selenide.$x("//textarea[@placeholder='...']")
                .shouldBe(Condition.visible, Duration.ofSeconds(9000))
                .setValue("Demo test for IFT&HF");
        Selenide.$x("//*[@name='invoice_type$erp']").hover().setValue("Исходящий");
        Selenide.$x("//*[@class='MuiAutocomplete-listbox']//*[.='Исходящий']").click();
        Selenide.$x("//*[@name='bank_details$erp']").click();
        // выбор реквизитов моей компании
        Selenide.$x("//*[@id='autocomplete-bank_details$erp-option-1']").click();
        Selenide.$x("//*[@name='organization$erp']").click();
        // выбор реквизитов плательщика
        Selenide.$x("//*[@id='autocomplete-organization$erp-option-3']/div").click();
        Selenide.$x("//*[@name='total_sum$erp']").hover();
        Selenide.$x("//*[@name='total_sum$erp']/following-sibling::*//*[@title='Отключить расчет по формуле']")
                .click();
        Selenide.$x("//span[contains(text(),'ОК')]").click();
        Selenide.$x("//*[@name='total_sum$erp']").setValue(sum);
        Selenide.$x("//*[@class='MuiCollapse-container MuiCollapse-entered']")
                .scrollIntoView("true");
        Selenide.$x("//*[@name='vat_rate$erp']").hover().click();
        Selenide.$x("//*[@class='MuiAutocomplete-listbox']//*[.='0%']").click();
        Selenide.$x("//*[@name='total_with_vat$erp']").hover();
        Selenide.$x("//*[@name='total_with_vat$erp']/following-sibling::*//*[@title='Отключить расчет по формуле']")
                .click();
        Selenide.$x("//span[contains(text(),'ОК')]").click();
        Selenide.$x("//*[@name='total_with_vat$erp']").setValue(sum);
        Selenide.$x("//span[contains(text(),'Сохранить и перейти')]").click();
    }

    public void openInvoice() {
        $(By.xpath("//span[contains(text(),'Управление предприятием')]"))
                .shouldBe(Condition.visible, Duration.ofSeconds(9000))
                .click();
        $(By.xpath("//span[contains(text(),'Счета')]")).click();
        $(By.xpath("//span[contains(text(),'Новая запись')]")).click();
    }
}

