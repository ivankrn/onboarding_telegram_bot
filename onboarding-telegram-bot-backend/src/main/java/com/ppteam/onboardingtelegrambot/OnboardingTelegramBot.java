package com.ppteam.onboardingtelegrambot;

import com.ppteam.onboardingtelegrambot.components.BotCommands;
import com.ppteam.onboardingtelegrambot.components.Buttons;
import com.ppteam.onboardingtelegrambot.components.CommandUtil;
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
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

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
        int messageId = 0;
        InlineKeyboardMarkup previousMarkup;
        String receivedMessage;
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            messageId = update.getMessage().getMessageId();
            previousMarkup = update.getMessage().getReplyMarkup();
            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                processAnswer(receivedMessage, chatId, userId, messageId, previousMarkup);
            }
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            messageId = update.getCallbackQuery().getMessage().getMessageId();
            previousMarkup = update.getCallbackQuery().getMessage().getReplyMarkup();
            receivedMessage = update.getCallbackQuery().getData();
            processAnswer(receivedMessage, chatId, userId, messageId, previousMarkup);
        }
    }

    private void startBot(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Что вы хотите сделать?");
        message.setReplyMarkup(Buttons.startBotMarkup());
        executeMessageWithLogging(message);
    }

    private void processAnswer(String receivedMessage, long chatId, long userId, int messageId,
                               InlineKeyboardMarkup previousMarkup) {
        String[] message = CommandUtil.split(receivedMessage);
        String command = CommandUtil.parseCommand(message);
        if (!command.equals(CallbackQueryCommand.CHOOSE_FOR_QUESTION_WITH_ID)
                && !command.equals(CallbackQueryCommand.MULTIPLE_CHOOSE_FOR_QUESTION_WITH_ID)
                && !command.equals(CallbackQueryCommand.SELECT_MULTIPLE_FOR_QUESTION_WITH_ID)
                && testSessionService.hasActiveTestSession(userId)) {
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
                boolean isTestBrowsingMode = CommandUtil.parseTestBrowsingMode(message);
                sendTopicChoiceMenu(chatId, isTestBrowsingMode);
                break;
            case CallbackQueryCommand.BROWSE_ARTICLES_BY_TOPIC_ID:
                long topicId = CommandUtil.parseTopicId(message);
                int pageNumber = CommandUtil.parsePageNumber(message);
                sendMaterialsByTopicId(chatId, pageNumber, topicId, false);
                break;
            case CallbackQueryCommand.BROWSE_TESTS_BY_TOPIC_ID:
                topicId = CommandUtil.parseTopicId(message);
                pageNumber = CommandUtil.parsePageNumber(message);
                sendMaterialsByTopicId(chatId, pageNumber, topicId, true);
                break;
            case CallbackQueryCommand.BROWSE_ARTICLE_BY_ID:
                long articleId = CommandUtil.parseArticleId(message);
                sendArticleById(chatId, articleId);
                break;
            case CallbackQueryCommand.RATE_ARTICLE_BY_ID:
                articleId = CommandUtil.parseArticleId(message);
                int rating = CommandUtil.parseRating(message);
                rateArticleById(chatId, articleId, rating);
                break;
            case CallbackQueryCommand.BEGIN_TEST_BY_ID:
                long testId = CommandUtil.parseTestId(message);
                beginTestById(chatId, userId, testId);
                break;
            case CallbackQueryCommand.CHOOSE_FOR_QUESTION_WITH_ID:
                long questionId = CommandUtil.parseQuestionId(message);
                long answerId = CommandUtil.parseAnswerId(message);
                processTestSingleAnswer(chatId, userId, questionId, answerId);
                break;
            case CallbackQueryCommand.MULTIPLE_CHOOSE_FOR_QUESTION_WITH_ID:
                questionId = CommandUtil.parseQuestionId(message);
                Set<Long> answersIds = CommandUtil.parseAnswersIds(message);
                processTestMultipleAnswers(chatId, userId, questionId, answersIds);
                break;
            case CallbackQueryCommand.SELECT_MULTIPLE_FOR_QUESTION_WITH_ID:
                long lastSelectedAnswerId = CommandUtil.parseAnswerId(message);
                editMultipleAnswersSelection(chatId, messageId, previousMarkup, lastSelectedAnswerId);
                break;
            case CallbackQueryCommand.SHOW_CORRECT_ANSWERS_FOR_TEST_WITH_ID:
                testId = CommandUtil.parseTestId(message);
                showCorrectAnswersForTest(chatId, testId);
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
        sendArticleActionsMenu(chatId, article);
    }

    private void sendArticleActionsMenu(long chatId, ArticleDto article) {
        if (article.getTestId() != null) {
            offerTestById(chatId, article.getTestId());
        }
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Доступные действия:");
        message.setReplyMarkup(Buttons.articleActionsMenuMarkup(article));
        executeMessageWithLogging(message);
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
        TestQuestionDto question = questions.get(0);
        sendTestQuestion(chatId, testQuestionService.findByIdWithAnswers(question.getId()));
    }

    private void processTestSingleAnswer(long chatId, long userId, long questionId, long answerId) {
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
        if (passedQuestions.size() < questions.size()) {
            sendNextTestQuestion(chatId, questions, passedQuestions);
        } else {
            endTest(chatId, session);
        }
    }

    private void processTestMultipleAnswers(long chatId, long userId, long questionId, Set<Long> answersIds) {
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
            Set<TestAnswerDto> correctAnswers = testAnswerService.getCorrectAnswersForQuestionId(previousQuestion.getId());
            if (isUserAnswersCorrect(correctAnswers, answersIds)) {
                testSessionService.increaseScore(session.getUserId());
                session.setScore(session.getScore() + 1);
            }
            TestSessionPassedQuestionDto passedQuestion = new TestSessionPassedQuestionDto();
            passedQuestion.setQuestionId(previousQuestion.getId());
            passedQuestion.setTestSession(session);
            testSessionPassedQuestionService.save(passedQuestion);
            passedQuestions.add(passedQuestion);
        }
        if (passedQuestions.size() < questions.size()) {
            sendNextTestQuestion(chatId, questions, passedQuestions);
        } else {
            endTest(chatId, session);
        }
    }

    private void sendNextTestQuestion(long chatId, List<TestQuestionDto> questions,
                                      List<TestSessionPassedQuestionDto> passedQuestions) {
        TestQuestionDto nextQuestion = getNextUnansweredQuestion(questions, passedQuestions);
        sendTestQuestion(chatId, testQuestionService.findByIdWithAnswers(nextQuestion.getId()));
    }

    private void sendTestQuestion(long chatId, TestQuestionFullDto question) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(formatTestQuestion(question));
        if (isQuestionWithMultipleCorrectAnswers(question)) {
            message.setReplyMarkup(Buttons.testAnswerMultipleChoiceMarkup(question));
        } else {
            message.setReplyMarkup(Buttons.testAnswerSingleChoiceMarkup(question));
        }
        executeMessageWithLogging(message);
    }

    private boolean isQuestionWithMultipleCorrectAnswers(TestQuestionFullDto testQuestion) {
        return testQuestion.getAnswers().stream().filter(TestAnswerDto::isCorrect).toList().size() > 1;
    }

    private void editMultipleAnswersSelection(long chatId, int messageId, InlineKeyboardMarkup previousMarkup,
                                              long lastSelectedAnswerId) {
        EditMessageReplyMarkup newMessage = new EditMessageReplyMarkup();
        newMessage.setChatId(chatId);
        newMessage.setMessageId(messageId);
        newMessage.setReplyMarkup(Buttons.editTestAnswerMultipleChoiceMarkup(previousMarkup, lastSelectedAnswerId));
        executeMessageWithLogging(newMessage);
    }

    private String formatTestQuestion(TestQuestionFullDto question) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(question.getQuestion());
        stringBuilder.append("\n\n");
        stringBuilder.append("\u2705 Варианты ответа");
        if (isQuestionWithMultipleCorrectAnswers(question)) {
            stringBuilder.append(" (выберите несколько)");
        }
        stringBuilder.append(":\n\n");
        for (int i = 0; i < question.getAnswers().size(); i++) {
            stringBuilder.append(i + 1);
            stringBuilder.append(") ");
            stringBuilder.append(question.getAnswers().get(i).getAnswer());
            stringBuilder.append("\n\n");
        }
        return stringBuilder.toString();
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

    private boolean isUserAnswersCorrect(Set<TestAnswerDto> correctAnswers, Set<Long> userAnswersIds) {
        int userCorrectAnswersCount = 0;
        for (long userAnswerId : userAnswersIds) {
            for (TestAnswerDto correctAnswer : correctAnswers) {
                if (userAnswerId == correctAnswer.getId()) {
                    userCorrectAnswersCount++;
                    break;
                }
            }
        }
        return correctAnswers.size() == userCorrectAnswersCount;
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

    private void endTest(long chatId, TestSessionDto session) {
        sendTestResults(chatId, session);
        testSessionService.deleteByUserId(session.getUserId());
        testStatisticService.updateForTest(session.getTestId(), session.getScore());
    }

    private void sendTestResults(long chatId, TestSessionDto session) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Ваш счет: " + session.getScore());
        executeMessageWithLogging(message);
        sendTestActionsMenu(chatId, session);
    }

    private void sendTestActionsMenu(long chatId, TestSessionDto session) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Доступные действия:");
        TestDto test = testService.findById(session.getTestId());
        message.setReplyMarkup(Buttons.testActionsMenuMarkup(test));
        executeMessageWithLogging(message);
    }

    private void showCorrectAnswersForTest(long chatId, long testId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        TestFullDto test = testService.findByIdWithQuestionsAndAnswers(testId);
        message.setText(formatQuestions(test.getQuestions()));
        executeMessageWithLogging(message);
    }

    private String formatQuestions(List<TestQuestionFullDto> questions) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < questions.size(); i++) {
            TestQuestionFullDto question = questions.get(i);
            sb.append(i + 1);
            sb.append(") ");
            sb.append(question.getQuestion());
            sb.append("\n");
            if (isQuestionWithMultipleCorrectAnswers(question)) {
                sb.append("Правильные ответы:");
            } else {
                sb.append("Правильный ответ:");
            }
            sb.append("\n");
            for (int j = 0; j < question.getAnswers().size(); j++) {
                TestAnswerDto answer = questions.get(i).getAnswers().get(j);
                if (answer.isCorrect()) {
                    sb.append("\t");
                    sb.append(j + 1);
                    sb.append(") ");
                    sb.append(answer.getAnswer());
                    sb.append("\n");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
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
