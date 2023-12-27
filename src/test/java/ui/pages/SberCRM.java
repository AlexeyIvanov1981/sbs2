package ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class SberCRM {
    public SelenideElement navBarFormMarketPlace = $x("//*[@title='Маркетплейс']");
    public SelenideElement buttonPaymentMethods = $x("//*[contains(text(),'Способы оплаты')]");
    public SelenideElement buttonB2B = $(By.xpath("//p[contains(text(),'Моментальные платежи B2B')]"));
}

