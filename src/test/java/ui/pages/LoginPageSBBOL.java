package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPageSBBOL {
    public SelenideElement textFieldLogin = $("input[placeholder='Логин']");
    public SelenideElement textFieldPassword = $("input[placeholder='Пароль']");
    public SelenideElement buttonNext = $("button[data-test-id='login-submit--button']");
    public SelenideElement buttonAccept = $x("//*[@data-test-id='offer-accept--button']");
    public SelenideElement TextFieldSmsCode = $x("//*[@data-test-id='smsCode--input']");
}
