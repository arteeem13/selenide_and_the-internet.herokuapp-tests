package com.github.selenide;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTest {

    @DisplayName("Поиск страницы с заголовком SoftAssertions на странице Wikis проекта Selenide в Github")
    @Test
    void checkExistSoftAssertionsInWiki() {

        open("https://github.com/");
        $(".js-site-search-form").click();
        $(".header-search-input").setValue("selenide").pressEnter();
        $(byText("Wikis")).click();

        String titleSearch = "SoftAssertions";

        // не пойму почему, но без положительной проверки возникает ошибка поиска "selector":"#wiki_search_results"
        // решил просто проверить заголовок всей страницы

        $("body").shouldHave(text("wiki results"));

        // в цикле идет поиск страницы со страницей, у которой заголовок titleSearch
        // Цикл останавливается, если заголовок найден, или страница последняя

        int currentTitleIndex = 0;
        String currentTitleName;

        do {

            // если кнопка Next недоступна, останавливаем выполнение цикла
            if ($(".next_page.disabled").exists())
                break;

            // переходим на следующую страницу, если нужного заголовка нет на текущей
             if (currentTitleIndex == $("#wiki_search_results").findAll(".text-normal").size()) {
                 $(".next_page").click();
                 sleep(10000); // гитхаб ругается, при быстром переходе по страницам
                 currentTitleIndex = 0;
             }

             currentTitleName = $("#wiki_search_results").$(".text-normal", currentTitleIndex).getText();
             currentTitleIndex++;

        } while (!(currentTitleName.equals(titleSearch)));

        $("#wiki_search_results").findAll(".text-normal").should(itemWithText(titleSearch));
    }
}
