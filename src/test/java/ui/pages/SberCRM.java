package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class SberCRM {
    public SelenideElement navBarFormMarketPlace = $x("//*[name()='path' and contains(@d,'M21.69 5.7')]");
}

