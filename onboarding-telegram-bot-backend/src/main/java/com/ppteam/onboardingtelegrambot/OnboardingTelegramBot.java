package com.ppteam.onboardingtelegrambot;

import com.ppteam.onboardingtelegrambot.components.BotCommands;
import com.ppteam.onboardingtelegrambot.components.Buttons;
import com.ppteam.onboardingtelegrambot.config.BotConfig;
import com.ppteam.onboardingtelegrambot.dto.*;
import com.ppteam.onboardingtelegrambot.service.*;
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
import java.util.List;

@Component
@Slf4j
public class OnboardingTelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TestService testService;
    @Autowired
    private TestQuestionService testQuestionService;
    @Autowired
    private TestAnswerService testAnswerService;
    @Autowired
    private TestSessionService testSessionService;
    @Autowired
    private TestSessionPassedQuestionService testSessionPassedQuestionService;
    @Autowired
    private ArticleTopicService articleTopicService;
    @Autowired
    private TestStatisticService testStatisticService;
    @Autowired
    private ArticleStatisticService articleStatisticService;

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
        if (!command.equals(CallbackQueryCommand.CHOOSE_FOR_QUESTION_WITH_ID)
                && this.testSessionService.hasActiveTestSession(userId)) {
            sendText(chatId, "Пожалуйста, завершите тест, прежде чем использовать меню");
            return;
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
                sendTopicChoiceMenu(chatId, isTestBrowsingMode);
                break;
            case CallbackQueryCommand.BROWSE_ARTICLES_BY_TOPIC_ID:
                long topicId = Integer.parseInt(receivedMessage.split(" ")[1]);
                int pageNumber = Integer.parseInt(receivedMessage.split(" ")[3]);
                sendMaterialsByTopicId(chatId, pageNumber, topicId, false);
                break;
            case CallbackQueryCommand.BROWSE_TESTS_BY_TOPIC_ID:
                topicId = Integer.parseInt(receivedMessage.split(" ")[1]);
                pageNumber = Integer.parseInt(receivedMessage.split(" ")[3]);
                sendMaterialsByTopicId(chatId, pageNumber, topicId, true);
                break;
            case CallbackQueryCommand.BROWSE_ARTICLE_BY_ID:
                long articleId = Integer.parseInt(receivedMessage.split(" ")[1]);
                sendArticleById(chatId, articleId);
                break;
            case CallbackQueryCommand.RATE_ARTICLE_BY_ID:
                articleId = Integer.parseInt(receivedMessage.split(" ")[1]);
                int rating = Integer.parseInt(receivedMessage.split(" ")[2]);
                rateArticleById(chatId, articleId, rating);
                break;
            case CallbackQueryCommand.BEGIN_TEST_BY_ID:
                long testId = Integer.parseInt(receivedMessage.split(" ")[1]);
                beginTestById(chatId, userId, testId);
                break;
            case CallbackQueryCommand.CHOOSE_FOR_QUESTION_WITH_ID:
                long questionId = Integer.parseInt(receivedMessage.split(" ")[1]);
                long answerId = Integer.parseInt(receivedMessage.split(" ")[3]);
                sendTestQuestion(chatId, userId, questionId, answerId);
                break;
        }
    }

    private void sendTopicChoiceMenu(long chatId, boolean isTestBrowsingMode) {
        List<ArticleTopicDto> articleTopics = articleTopicService.findAll();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите тему:");
        message.setReplyMarkup(Buttons.topicChoiceMarkup(articleTopics, isTestBrowsingMode));
        executeMessageWithLogging(message);
    }

    private void sendMaterialsByTopicId(long chatId, int pageNumber, long topicId, boolean isTestBrowsingMode) {
        Pageable page = PageRequest.of(pageNumber, 10);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (isTestBrowsingMode) {
            message.setText("Выберите тест:");
        } else {
            message.setText("Выберите статью:");
        }
        if (isTestBrowsingMode) {
            Page<TestDto> tests = testService.findByTopicId(topicId, page);
            message.setReplyMarkup(Buttons.testChoiceMarkup(tests, topicId));
        } else {
            Page<ArticleDto> articles = articleService.findByTopicId(topicId, page);
            message.setReplyMarkup(Buttons.articleChoiceMarkup(articles, topicId));
        }
        executeMessageWithLogging(message);
    }

    private void sendArticleById(long chatId, long articleId) {
        ArticleDto article = articleService.findById(articleId);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(formatArticle(article));
        message.setReplyMarkup(Buttons.articleRatingMarkup(articleId));
        executeMessageWithLogging(message);
        if (article.getTest() != null) {
            offerTestById(chatId, article.getTest().getId());
        }
    }

    private String formatArticle(ArticleDto article) {
        StringBuilder sb = new StringBuilder();
        sb.append("\u2139 " + article.getTitle() + "\n\n");
        sb.append(article.getContent() + "\n\n");
        if (article.getUsefulLinks() != null && !article.getUsefulLinks().isBlank()) {
            sb.append("\uD83D\uDCD5 Полезные ссылки:\n" + article.getUsefulLinks() + "\n\n");
        }
        sb.append("\uD83D\uDCC5 Дата создания статьи:\n" + article.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n\n");
        sb.append("\u2B50 Пожалуйста, оцените насколько данная статья была Вам полезна (5 - очень полезна, 1 - абсолютно бесполезна):");
        return sb.toString();
    }

    private void rateArticleById(long chatId, long articleId, int rating) {
        articleStatisticService.updateForArticle(articleId, rating);
        sendText(chatId, "Спасибо за оценку!");
    }

    private void offerTestById(long chatId, long testId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("\uD83D\uDCDD Для данной статьи доступен тест, не желаете пройти его?");
        message.setReplyMarkup(Buttons.offerTestMarkup(testId));
        executeMessageWithLogging(message);
    }

    private void sendAdminMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setText("Здесь будет ссылка на админ-панель");
        message.setChatId(chatId);
        executeMessageWithLogging(message);
    }

    private void beginTestById(long chatId, long userId, long testId) {
        testSessionService.createForUserAndTest(userId, testId);
        sendFirstTestQuestion(chatId, userId);
    }

    private void sendFirstTestQuestion(long chatId, long userId) {
        TestSessionDto session = testSessionService.findByUserId(userId);
        List<TestQuestionDto> questions = testQuestionService.findByTestId(session.getTestId());
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (!questions.isEmpty()) {
            TestQuestionDto nextQuestion = questions.get(0);
            message.setText(nextQuestion.getQuestion());
            message.setReplyMarkup(Buttons.testAnswerChoiceMarkup(testQuestionService.findByIdWithAnswers(nextQuestion.getId())));
        } else {
            message.setText("Ваш счет: " + session.getScore());
            testSessionService.deleteByUserId(session.getUserId());
            testStatisticService.updateForTest(session.getTestId(), session.getScore());
        }
        executeMessageWithLogging(message);
    }

    private void sendTestQuestion(long chatId, long userId, long questionId, long answerId) {
        TestSessionDto session = testSessionService.findByUserId(userId);
        List<TestQuestionDto> questions = testQuestionService.findByTestId(session.getTestId());
        List<TestSessionPassedQuestionDto> passedQuestions = testSessionPassedQuestionService.findByUserId(session.getUserId());
        if (testQuestionService.exists(questionId)) {
            if (didUserAnswerQuestionBefore(passedQuestions, questionId)
                    || !doQuestionsContainQuestionWithId(questions, questionId)) {
                sendText(chatId, "Пожалуйста, выберите ответ на текущий вопрос");
                return;
            }
            TestQuestionDto previousQuestion = testQuestionService.findById(questionId);
            TestAnswerDto correctAnswer = testAnswerService.getCorrectAnswerForQuestionId(previousQuestion.getId());
            if (isUserAnswerCorrect(correctAnswer, answerId)) {
                testSessionService.increaseScore(session.getUserId());
                session.setScore(session.getScore() + 1);
            }
            TestSessionPassedQuestionDto passedQuestion = new TestSessionPassedQuestionDto();
            passedQuestion.setQuestionId(previousQuestion.getId());
            passedQuestion.setTestSession(session);
            testSessionPassedQuestionService.save(passedQuestion);
            passedQuestions.add(passedQuestion);
        }
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (passedQuestions.size() < questions.size()) {
            TestQuestionDto nextQuestion = getNextUnansweredQuestion(questions, passedQuestions);
            message.setText(nextQuestion.getQuestion());
            message.setReplyMarkup(Buttons.testAnswerChoiceMarkup(testQuestionService.findByIdWithAnswers(nextQuestion.getId())));
        } else {
            message.setText("Ваш счет: " + session.getScore());
            testSessionService.deleteByUserId(session.getUserId());
            testStatisticService.updateForTest(session.getTestId(), session.getScore());
        }
        executeMessageWithLogging(message);
    }

    private boolean doQuestionsContainQuestionWithId(List<TestQuestionDto> questions, long questionId) {
        return questions.stream().anyMatch(q -> q.getId() == questionId);
    }

    private boolean didUserAnswerQuestionBefore(List<TestSessionPassedQuestionDto> passedQuestions, long questionId) {
        return passedQuestions.stream().anyMatch(pq -> pq.getQuestionId() == questionId);
    }

    private boolean isUserAnswerCorrect(TestAnswerDto correctAnswer, long userAnswerId) {
        return userAnswerId == correctAnswer.getId();
    }

    private TestQuestionDto getNextUnansweredQuestion(List<TestQuestionDto> questions,
                                                          List<TestSessionPassedQuestionDto> passedQuestions) {
        for (TestQuestionDto question : questions) {
            if (passedQuestions.stream().noneMatch(pq -> pq.getQuestionId() == question.getId())) {
                return question;
            }
        }
        return null;
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
