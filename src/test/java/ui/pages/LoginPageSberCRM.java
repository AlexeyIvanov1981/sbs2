package ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginPageSberCRM {
    public SelenideElement buttonSubmit = $("button[type='submit']");
    public SelenideElement buttonChooseLanguage = $("button[class='MuiButtonBase-root MuiIconButton-root']");
    public SelenideElement buttonRUSLanguage = $("//div[@class='MuiPaper-root MuiMenu-paper MuiPopover-paper " +
            "MuiPaper-elevation8 MuiPaper-rounded']//ul//li[1]");
    //public SelenideElement buttonRUSLanguage = $("li.MuiButtonBase-root:nth-child(1) > span:nth-child(2)");
    //public SelenideElement buttonRUSLanguage = $("//span[contains(text(),'Русский')]");
    public SelenideElement textFieldUserName = $("input[name='username']");
    public SelenideElement textFieldPassword = $("input[name='password']");
    public SelenideElement buttonLogInBySBBOL = $(By.xpath("//span[contains(text()," +
            "'Войти по СберБизнес ID')]"));
}
