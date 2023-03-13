package com.ppteam.onboardingtelegrambot.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> COMMAND_LIST = List.of(
            new BotCommand("/start", "Запустить бота"),
            new BotCommand("/help", "Меню помощи")
    );

    String HELP_TEXT = "Данный бот поможет вам адаптироваться к новому окружению и коллективу. "
            + "Вам доступны следующие команды:\n\n"
            + "/start - запустить бота\n"
            + "/help - вывести меню помощи";
}
