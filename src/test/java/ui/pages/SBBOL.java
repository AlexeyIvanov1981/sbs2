package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class SBBOL {
    public SelenideElement
            textFieldLogin = $("input[placeholder='Логин']"),
            textFieldPassword = $("input[placeholder='Пароль']"),
            buttonNext = $("button[data-test-id='login-submit--button']"),
            buttonAccept = $x("//*[@data-test-id='offer-accept--button']"),
            textFieldSmsCode = $x("//*[@data-test-id='smsCode--input']"),
            buttonCloseFirstAlert = $x("//*[@id='app']/div/div[3]/div/div[1]/div"),
            buttonCloseSecondAlert = $x("//*[@id='app']/div/div[3]/div/div/div");


//    $x("//*[@id='app']/div/div[3]/div/div[1]/div").click();
//    $x("//*[@id='app']/div/div[3]/div/div/div").click();

    public void loginSbbol(String login, String password) {
        buttonCloseFirstAlert.click();
        buttonCloseSecondAlert.click();
        textFieldLogin.setValue(login);
        textFieldPassword.setValue(password);
        buttonNext.click();
    }
}
