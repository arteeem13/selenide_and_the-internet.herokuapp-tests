package com.andreev.githubSelenideTests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.andreev.githubSelenideTests.WikiPageSearch.*;

@DisplayName("Проверки для проекта Selenide на странице Wikis в Github")
public class SelenideTest {

    @BeforeEach
    void beforeEach(){
        Configuration.browser = "CHROME";
        Configuration.browserSize = "1920x1080";
        baseUrl = "https://the-internet.herokuapp.com/drag_and_drop";
    }

    @AfterEach
    void afterEach(){
        setCurrentTitleIndex(0);
        closeWebDriver();
    }

    @DisplayName("Страница с заголовком SoftAssertions есть на странице Wikis проекта Selenide в Github")
    @Test
    void checkExistSoftAssertionsInWiki() {

        open("https://github.com/");
        $(".js-site-search-form").click();
        $(".header-search-input").setValue("selenide").pressEnter();
        $(".menu").$(byText("Wikis")).click();
        sleep(2000);

        String titleSearch = "SoftAssertions";

        // не пойму почему, но без положительной проверки возникает ошибка поиска "selector":"#wiki_search_results"
        // решил просто проверить заголовок всей страницы

        $("body").shouldHave(text("wiki results"));
        searchWikiPageByProjectName(titleSearch);
        $("#wiki_search_results").findAll(".text-normal").should(itemWithText(titleSearch));
    }

    @DisplayName("Есть пример кода для JUnit5 в проекте SoftAssertions")
    @Test
    void checkJUnit5CodeInSoftAssertions() {

        open("https://github.com/");
        $(".js-site-search-form").click();
        $(".header-search-input").setValue("selenide").pressEnter();
        $(".menu").$(byText("Wikis")).click();
        sleep(2000);

        String titleSearch = "SoftAssertions";

        $("body").shouldHave(text("wiki results"));
        searchWikiPageByProjectName(titleSearch);
        $("#wiki_search_results").$(byTitle(titleSearch)).click();

        $(".markdown-body").shouldHave(text("Using JUnit5 extend test class:"));
        $(".markdown-body").shouldHave(text("@ExtendWith({SoftAssertsExtension.class})\n" +
                "class Tests {\n" +
                "  @Test\n" +
                "  void test() {\n" +
                "    Configuration.assertionMode = SOFT;\n" +
                "    open(\"page.html\");\n" +
                "\n" +
                "    $(\"#first\").should(visible).click();\n" +
                "    $(\"#second\").should(visible).click();\n" +
                "  }\n" +
                "}"));
    }
}