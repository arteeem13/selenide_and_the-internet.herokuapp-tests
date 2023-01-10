package com.andreev.theInternetHerokuappTests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

@DisplayName("Проверки перемещения прямоугольника через Selenide.actions() и Selenide.dragAndDropTo()")
public class DragAndDropTests {

    @BeforeEach
    void beforeEach(){
        Configuration.browser = "CHROME";
        Configuration.browserSize = "1920x1080";
        baseUrl = "https://the-internet.herokuapp.com/drag_and_drop";
    }

    @AfterEach
    void afterEach(){
        closeWebDriver();
    }

    @Nested
    @DisplayName("Прямоугольники меняются местами при использовании Selenide.actions()")
    @Test
    void changeRectanglesWithActionsTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open(baseUrl);

        // проверяем начальное положение прямоугольников A и В
        $("#columns").$("#column-a").shouldHave(text("A"));
        $("#columns").$("#column-b").shouldHave(text("B"));

        sleep(2000);

        actions().moveToElement($("#column-a")).clickAndHold()
                .moveToElement($("#column-b")).release().build().perform();

        sleep(2000);

        // проверяем положение прямоугольников A и В после смены мест
        $("#columns").$("#column-a").shouldHave(text("B"));
        $("#columns").$("#column-b").shouldHave(text("A"));
    }

    @Nested
    @DisplayName("Прямоугольники меняются местами при использовании Selenide.dragAndDropTo()")
    @Test
    void changeRectanglesWithDragAndDropTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open(baseUrl);

        // проверяем начальное положение прямоугольников A и В
        $("#columns").$("#column-a").shouldHave(text("A"));
        $("#columns").$("#column-b").shouldHave(text("B"));

        sleep(2000);

        $("#column-a").dragAndDropTo($("#column-b"));

        sleep(2000);

        // проверяем положение прямоугольников A и В после смены мест
        $("#columns").$("#column-a").shouldHave(text("B"));
        $("#columns").$("#column-b").shouldHave(text("A"));
    }
}
