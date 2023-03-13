package com.ppteam.onboardingtelegrambot.components;

import com.ppteam.onboardingtelegrambot.CallbackQueryCommand;
import com.ppteam.onboardingtelegrambot.database.ArticleTopic;
import org.springframework.data.domain.Page;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Buttons {

    public static InlineKeyboardMarkup startBotMarkup() {
        InlineKeyboardButton browseArticleButton = new InlineKeyboardButton("Почитать статьи");
        browseArticleButton.setCallbackData(CallbackQueryCommand.GET_TOPICS + " page 0");
        InlineKeyboardButton adminPanelButton = new InlineKeyboardButton("Панель управления");
        adminPanelButton.setCallbackData(CallbackQueryCommand.ADMIN_PANEL);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(List.of(browseArticleButton));
        rows.add(List.of(adminPanelButton));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup topicChoiceMarkup(Page<ArticleTopic> topics) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        topics.get().forEach(topic -> {
            InlineKeyboardButton button = new InlineKeyboardButton(topic.getName());
            button.setCallbackData(CallbackQueryCommand.BROWSE_ARTICLES_BY_TOPIC + " " + topic.getId());
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

    public static InlineKeyboardMarkup adminPanelMarkup() {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        InlineKeyboardButton addArticleButton = new InlineKeyboardButton("Добавить статью");
        addArticleButton.setCallbackData(CallbackQueryCommand.ADD_ARTICLE);
        InlineKeyboardButton deleteArticleButton = new InlineKeyboardButton("Удалить статью");
        deleteArticleButton.setCallbackData(CallbackQueryCommand.DELETE_ARTICLE);
        InlineKeyboardButton addArticleTopicButton = new InlineKeyboardButton("Добавить тему");
        addArticleTopicButton.setCallbackData(CallbackQueryCommand.ADD_TOPIC);
        InlineKeyboardButton deleteArticleTopicButton = new InlineKeyboardButton("Удалить тему");
        deleteArticleTopicButton.setCallbackData(CallbackQueryCommand.DELETE_TOPIC);
        rows.add(List.of(addArticleButton));
        rows.add(List.of(deleteArticleButton));
        rows.add(List.of(addArticleTopicButton));
        rows.add(List.of(deleteArticleTopicButton));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }
}
