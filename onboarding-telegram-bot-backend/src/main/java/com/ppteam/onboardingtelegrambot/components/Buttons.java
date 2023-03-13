package com.ppteam.onboardingtelegrambot.components;

import com.ppteam.onboardingtelegrambot.CallbackQueryCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Buttons {

    public static InlineKeyboardMarkup startBotMarkup() {
        InlineKeyboardButton browseArticleButton = new InlineKeyboardButton("Почитать статьи");
        browseArticleButton.setCallbackData(CallbackQueryCommand.BROWSE_ARTICLE);
        InlineKeyboardButton addArticleButton = new InlineKeyboardButton("Добавить статью");
        addArticleButton.setCallbackData(CallbackQueryCommand.ADD_ARTICLE);
        InlineKeyboardButton deleteArticleButton = new InlineKeyboardButton("Удалить статью");
        deleteArticleButton.setCallbackData(CallbackQueryCommand.DELETE_ARTICLE);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(List.of(browseArticleButton));
        rows.add(List.of(addArticleButton));
        rows.add(List.of(deleteArticleButton));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }
}
