package com.ppteam.onboardingtelegrambot.components;

import com.ppteam.onboardingtelegrambot.CallbackQueryCommand;
import com.ppteam.onboardingtelegrambot.database.*;
import org.springframework.data.domain.Page;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Buttons {

    public static InlineKeyboardMarkup startBotMarkup() {
        InlineKeyboardButton browseArticlesButton = new InlineKeyboardButton("Почитать статьи");
        browseArticlesButton.setCallbackData(CallbackQueryCommand.GET_TOPICS + " article page 0");
        InlineKeyboardButton browseTestsButton = new InlineKeyboardButton("Порешать тесты");
        browseTestsButton.setCallbackData(CallbackQueryCommand.GET_TOPICS + " test page 0");
        InlineKeyboardButton adminPanelButton = new InlineKeyboardButton("Панель управления");
        adminPanelButton.setCallbackData(CallbackQueryCommand.ADMIN_PANEL);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(List.of(browseArticlesButton));
        rows.add(List.of(browseTestsButton));
        rows.add(List.of(adminPanelButton));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup topicChoiceMarkup(Page<ArticleTopic> topics, boolean isTestBrowsingMode) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        topics.get().forEach(topic -> {
            InlineKeyboardButton button = new InlineKeyboardButton(topic.getName());
            if (isTestBrowsingMode) {
                button.setCallbackData(CallbackQueryCommand.BROWSE_TESTS_BY_TOPIC_ID + " " + topic.getId() + " page 0");
            } else {
                button.setCallbackData(CallbackQueryCommand.BROWSE_ARTICLES_BY_TOPIC_ID + " " + topic.getId() + " page 0");
            }
            rows.add(List.of(button));
        });
        List<InlineKeyboardButton> pagination = new ArrayList<>(2);
        int currentPageNumber = topics.getPageable().getPageNumber();
        if (currentPageNumber > 0) {
            InlineKeyboardButton previousPageButton = new InlineKeyboardButton("<");
            previousPageButton.setCallbackData(CallbackQueryCommand.GET_TOPICS + " page " + (currentPageNumber - 1));
            pagination.add(previousPageButton);
        }
        if (currentPageNumber < topics.getTotalPages() - 1) {
            InlineKeyboardButton nextPageButton = new InlineKeyboardButton(">");
            nextPageButton.setCallbackData(CallbackQueryCommand.GET_TOPICS + " page " + (currentPageNumber + 1));
            pagination.add(nextPageButton);
        }
        rows.add(pagination);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup materialChoiceMarkup(Page<? extends Material> materials, int topicId, boolean isTestBrowsingMode) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        materials.get().forEach(material -> {
            InlineKeyboardButton button = new InlineKeyboardButton(material.getTitle());
            if (isTestBrowsingMode) {
                button.setCallbackData(CallbackQueryCommand.BEGIN_TEST_BY_ID + " " + material.getId());
            } else {
                button.setCallbackData(CallbackQueryCommand.BROWSE_ARTICLE_BY_ID + " " + material.getId());
            }
            rows.add(List.of(button));
        });
        List<InlineKeyboardButton> pagination = new ArrayList<>(2);
        int currentPageNumber = materials.getPageable().getPageNumber();
        if (currentPageNumber > 0) {
            InlineKeyboardButton previousPageButton = new InlineKeyboardButton("<");
            if (isTestBrowsingMode) {
                previousPageButton.setCallbackData(CallbackQueryCommand.BROWSE_TESTS_BY_TOPIC_ID + " " + topicId + " page " + (currentPageNumber - 1));
            } else {
                previousPageButton.setCallbackData(CallbackQueryCommand.BROWSE_ARTICLES_BY_TOPIC_ID + " " + topicId + " page " + (currentPageNumber - 1));
            }
            pagination.add(previousPageButton);
        }
        if (currentPageNumber < materials.getTotalPages() - 1) {
            InlineKeyboardButton nextPageButton = new InlineKeyboardButton(">");
            if (isTestBrowsingMode) {
                nextPageButton.setCallbackData(CallbackQueryCommand.BROWSE_TESTS_BY_TOPIC_ID + " " + topicId + " page " + (currentPageNumber + 1));
            } else {
                nextPageButton.setCallbackData(CallbackQueryCommand.BROWSE_ARTICLES_BY_TOPIC_ID + " " + topicId + " page " + (currentPageNumber + 1));
            }
            pagination.add(nextPageButton);
        }
        rows.add(pagination);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup articleWithAttachedTestMarkup(Article article) {
        InlineKeyboardButton beginTestButton = new InlineKeyboardButton("Начать тест");
        beginTestButton.setCallbackData(CallbackQueryCommand.BEGIN_TEST_BY_ID + " " + article.getTest().getId());
        List<List<InlineKeyboardButton>> rows = List.of(List.of(beginTestButton));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup testAnswerChoiceMarkup(Set<TestAnswer> answers, long questionId) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (TestAnswer answer : answers) {
            InlineKeyboardButton button = new InlineKeyboardButton(answer.getAnswer());
            button.setCallbackData(CallbackQueryCommand.CHOOSE_FOR_QUESTION_WITH_ID + " " + questionId + " answer " + answer.getId());
            rows.add(List.of(button));
        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }
}
