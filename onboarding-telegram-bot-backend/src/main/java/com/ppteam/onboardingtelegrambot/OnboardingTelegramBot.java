package com.ppteam.onboardingtelegrambot;

import com.ppteam.onboardingtelegrambot.components.BotCommands;
import com.ppteam.onboardingtelegrambot.components.Buttons;
import com.ppteam.onboardingtelegrambot.config.BotConfig;
import com.ppteam.onboardingtelegrambot.database.*;
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
import java.util.Set;

@Component
@Slf4j
public class OnboardingTelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestSessionRepository testSessionRepository;
    @Autowired
    private TestSessionPassedQuestionRepository testSessionPassedQuestionRepository;
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
        long userId = 0;
        String receivedMessage;
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                processAnswer(receivedMessage, chatId, userId);
            }
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            receivedMessage = update.getCallbackQuery().getData();
            processAnswer(receivedMessage, chatId, userId);
        }
    }

    private void startBot(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Что вы хотите сделать?");
        message.setReplyMarkup(Buttons.startBotMarkup());
        executeMessageWithLogging(message);
    }

    private void processAnswer(String receivedMessage, long chatId, long userId) {
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
                boolean isTestBrowsingMode = receivedMessage.split(" ")[1].equals("test");
                int pageNumber = Integer.parseInt(receivedMessage.split(" ")[3]);
                sendTopicChoiceMenu(chatId, pageNumber, isTestBrowsingMode);
                break;
            case CallbackQueryCommand.BROWSE_ARTICLES_BY_TOPIC_ID:
                int topicId = Integer.parseInt(receivedMessage.split(" ")[1]);
                pageNumber = Integer.parseInt(receivedMessage.split(" ")[3]);
                sendMaterialsByTopicId(chatId, pageNumber, topicId, false);
                break;
            case CallbackQueryCommand.BROWSE_TESTS_BY_TOPIC_ID:
                topicId = Integer.parseInt(receivedMessage.split(" ")[1]);
                pageNumber = Integer.parseInt(receivedMessage.split(" ")[3]);
                sendMaterialsByTopicId(chatId, pageNumber, topicId, true);
                break;
            case CallbackQueryCommand.BROWSE_ARTICLE_BY_ID:
                int articleId = Integer.parseInt(receivedMessage.split(" ")[1]);
                sendArticleById(chatId, articleId);
                break;
            case CallbackQueryCommand.BEGIN_TEST_BY_ID:
                int testId = Integer.parseInt(receivedMessage.split(" ")[1]);
                beginTestById(chatId, userId, testId);
                break;
            case CallbackQueryCommand.CHOOSE_CORRECT_ANSWER:
                sendTestQuestion(chatId, userId, true);
                break;
            case CallbackQueryCommand.CHOOSE_INCORRECT_ANSWER:
                sendTestQuestion(chatId, userId, false);
                break;
        }
    }

    private void sendTopicChoiceMenu(long chatId, int pageNumber, boolean isTestBrowsingMode) {
        Pageable page = PageRequest.of(pageNumber, 10);
        Page<ArticleTopic> articleTopics = articleTopicRepository.findAll(page);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите тему:");
        message.setReplyMarkup(Buttons.topicChoiceMarkup(articleTopics, isTestBrowsingMode));
        executeMessageWithLogging(message);
    }

    private void sendMaterialsByTopicId(long chatId, int pageNumber, int topicId, boolean isTestBrowsingMode) {
        Pageable page = PageRequest.of(pageNumber, 10);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите статью:");
        if (isTestBrowsingMode) {
            Page<Test> tests = testRepository.findByTopicId(topicId, page);
            message.setReplyMarkup(Buttons.materialChoiceMarkup(tests, topicId, isTestBrowsingMode));
        } else {
            Page<Article> articles = articleRepository.findByTopicId(topicId, page);
            message.setReplyMarkup(Buttons.materialChoiceMarkup(articles, topicId, isTestBrowsingMode));
        }
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

    private void beginTestById(long chatId, long userId, int testId) {
        TestSession session = new TestSession();
        session.setUserId(userId);
        session.setTestId(testId);
        testSessionRepository.save(session);
        sendTestQuestion(chatId, userId, false);
    }

    private void sendTestQuestion(long chatId, long userId, boolean previousAnswerIsCorrect) {
        TestSession session = testSessionRepository.findByUserId(userId);
        if (previousAnswerIsCorrect) {
            session.setScore(session.getScore() + 1);
            testSessionRepository.save(session);
        }
        Test test = testRepository.findById(session.getTestId());
        Set<TestQuestion> questions = test.getQuestions();
        Set<TestSessionPassedQuestion> passedQuestions = session.getPassedQuestions();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (passedQuestions.size() < questions.size()) {
            for (TestQuestion question : questions) {
                if (passedQuestions.stream().noneMatch(pq -> pq.getQuestionId() == question.getId())) {
                    TestSessionPassedQuestion passedQuestion = new TestSessionPassedQuestion();
                    passedQuestion.setQuestionId(question.getId());
                    passedQuestion.setTestSession(session);
                    testSessionPassedQuestionRepository.save(passedQuestion);
                    //Set<TestSessionPassedQuestion> newPassedQuestions = new HashSet<>(passedQuestions);
                    //newPassedQuestions.add(passedQuestion);
                    //session.setPassedQuestions(newPassedQuestions);
                    //session.getPassedQuestions().add(passedQuestion);
                    message.setText(question.getQuestion());
                    message.setReplyMarkup(Buttons.testAnswerChoiceMarkup(question.getAnswers()));
                    //testSessionRepository.save(session);
                    break;
                }
            }
        } else {
            message.setText("Ваш счет: " + session.getScore());
            //testSessionPassedQuestionRepository.deleteByTestSessionId(session.getId());
            testSessionRepository.delete(session);
        }
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
