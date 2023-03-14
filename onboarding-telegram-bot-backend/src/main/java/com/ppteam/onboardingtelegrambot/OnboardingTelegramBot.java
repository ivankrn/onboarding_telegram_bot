package com.ppteam.onboardingtelegrambot;

import com.ppteam.onboardingtelegrambot.components.BotCommands;
import com.ppteam.onboardingtelegrambot.components.Buttons;
import com.ppteam.onboardingtelegrambot.config.BotConfig;
import com.ppteam.onboardingtelegrambot.database.Article;
import com.ppteam.onboardingtelegrambot.database.ArticleRepository;
import com.ppteam.onboardingtelegrambot.database.ArticleTopic;
import com.ppteam.onboardingtelegrambot.database.ArticleTopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class OnboardingTelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleTopicRepository articleTopicRepository;

    public OnboardingTelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        try {
            this.execute(new SetMyCommands(BotCommands.COMMAND_LIST, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = 0;
        String receivedMessage;
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                processAnswer(receivedMessage, chatId);
            }
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            receivedMessage = update.getCallbackQuery().getData();
            processAnswer(receivedMessage, chatId);
        }
    }

    private void startBot(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Что вы хотите сделать?");
        message.setReplyMarkup(Buttons.startBotMarkup());
        executeMessageWithLogging(message);
    }

    private void processAnswer(String receivedMessage, long chatId) {
        String command = receivedMessage;
        if (receivedMessage.split(" ").length > 1) {
            command = receivedMessage.split(" ")[0];
        }
        switch (command) {
            case CallbackQueryCommand.START:
                startBot(chatId);
                break;
            case CallbackQueryCommand.HELP:
                sendText(chatId, BotCommands.HELP_TEXT);
                break;
            case CallbackQueryCommand.ADMIN_PANEL:
                sendAdminMenu(chatId);
                break;
            case CallbackQueryCommand.GET_TOPICS:
                int pageNumber = Integer.parseInt(receivedMessage.split(" ")[2]);
                sendTopicChoiceMenu(chatId, pageNumber);
                break;
            case CallbackQueryCommand.BROWSE_ARTICLES_BY_TOPIC_ID:
                int topicId = Integer.parseInt(receivedMessage.split(" ")[1]);
                pageNumber = Integer.parseInt(receivedMessage.split(" ")[3]);
                sendArticlesByTopicId(chatId, pageNumber, topicId);
                break;
            case CallbackQueryCommand.BROWSE_ARTICLE_BY_ID:
                int articleId = Integer.parseInt(receivedMessage.split(" ")[1]);
                sendArticleById(chatId, articleId);
                break;
        }
    }

    private void sendTopicChoiceMenu(long chatId, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 10);
        Page<ArticleTopic> articleTopics = articleTopicRepository.findAll(page);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите тему:");
        message.setReplyMarkup(Buttons.topicChoiceMarkup(articleTopics));
        executeMessageWithLogging(message);
    }

    private void sendArticlesByTopicId(long chatId, int pageNumber, int topicId) {
        Pageable page = PageRequest.of(pageNumber, 10);
        Page<Article> articles = articleRepository.findByTopicId(topicId, page);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите статью:");
        message.setReplyMarkup(Buttons.articleChoiceMarkup(articles, topicId));
        executeMessageWithLogging(message);
    }

    private void sendArticleById(long chatId, int articleId) {
        Article article = articleRepository.findById(articleId);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(formatArticle(article));
        executeMessageWithLogging(message);
    }

    private String formatArticle(Article article) {
        StringBuilder sb = new StringBuilder();
        sb.append("\u2139 " + article.getTitle() + "\n\n");
        sb.append(article.getContent() + "\n\n");
        if (article.getUsefulLinks() != null && !article.getUsefulLinks().isBlank()) {
            sb.append("\uD83D\uDCD5 Полезные ссылки:\n" + article.getUsefulLinks() + "\n\n");
        }
        if (article.getTestLink() != null && !article.getTestLink().isBlank()) {
            sb.append("\uD83D\uDCDD Ссылка на тест:\n" + article.getTestLink() + "\n\n");
        }
        sb.append("\uD83D\uDCC5 Дата создания статьи:\n" + article.getCreatedOn().format(DateTimeFormatter.ISO_LOCAL_DATE));
        return sb.toString();
    }

    private void sendAdminMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setText("Здесь будет ссылка на админ-панель");
        message.setChatId(chatId);
        executeMessageWithLogging(message);
    }

    private void sendText(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        executeMessageWithLogging(message);
    }

    /**
     * Выполняет сообщение Telegram с логированием.
     *
     * @param message Сообщение
     */
    private void executeMessageWithLogging(BotApiMethod<?> message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
