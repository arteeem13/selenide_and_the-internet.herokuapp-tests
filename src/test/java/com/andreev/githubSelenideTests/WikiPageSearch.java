package com.andreev.githubSelenideTests;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class WikiPageSearch {

    private static Integer currentTitleIndex = 0;

    public static void searchWikiPageByProjectName(String projectNameOnWiki) {

        // в цикле идет поиск страницы со страницей, у которой заголовок titleSearch
        // Цикл останавливается, если заголовок найден, или страница последняя

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

        } while (!(currentTitleName.equals(projectNameOnWiki)));
    }

    public static Integer getCurrentTitleIndex() {
        return currentTitleIndex;
    }

    public static void setCurrentTitleIndex(Integer currentTitleIndex) {
        WikiPageSearch.currentTitleIndex = currentTitleIndex;
    }
}
