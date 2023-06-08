package com.ppteam.onboardingtelegrambot;

import java.util.Set;

public class CallbackQueryCommand {
    public static final String SEPARATOR = " ";
    public static final String START = "/start";
    public static final String HELP = "/help";
    public static final String ADMIN_PANEL = "/admin";
    public static final String BROWSE_ARTICLES_BY_TOPIC_ID = "/browse_articles_by_topic_id";
    public static final String BROWSE_ARTICLE_BY_ID = "/browse_article_by_id";
    public static final String RATE_ARTICLE_BY_ID = "/rate_article_by_id";
    public static final String GET_TOPICS = "/get_topics";
    public static final String BROWSE_TESTS_BY_TOPIC_ID = "/browse_tests_by_topic_id";
    public static final String BEGIN_TEST_BY_ID = "/begin_test_by_id";
    public static final String CHOOSE_FOR_QUESTION_WITH_ID = "/choose_for_question";
    public static final String MULTIPLE_CHOOSE_FOR_QUESTION_WITH_ID = "/multiple_choose_for_question";
    public static final String SELECT_MULTIPLE_FOR_QUESTION_WITH_ID = "/select_multiple_for_question";
    public static final String SHOW_CORRECT_ANSWERS_FOR_TEST_WITH_ID = "/show_correct_answers_for_test";
    public static final String PAGE = "page";
    public static final String TEST = "test";
    public static final String ARTICLE = "article";
    public static final String ANSWER = "answer";

    public static String getTopicsForArticles() {
        return CallbackQueryCommand.GET_TOPICS + SEPARATOR + CallbackQueryCommand.ARTICLE;
    }

    public static String getTopicsForTests() {
        return CallbackQueryCommand.GET_TOPICS + SEPARATOR + CallbackQueryCommand.TEST;
    }

    public static String browseArticlesByTopic(long topicId, int pageNumber) {
        return CallbackQueryCommand.BROWSE_ARTICLES_BY_TOPIC_ID + SEPARATOR + topicId + SEPARATOR + PAGE + SEPARATOR
                + pageNumber;
    }

    public static String browseTestsByTopic(long topicId, int pageNumber) {
        return CallbackQueryCommand.BROWSE_TESTS_BY_TOPIC_ID + SEPARATOR + topicId + SEPARATOR + PAGE + SEPARATOR
                + pageNumber;
    }

    public static String browseArticle(long articleId) {
        return CallbackQueryCommand.BROWSE_ARTICLE_BY_ID + SEPARATOR + articleId;
    }

    public static String beginTest(long testId) {
        return CallbackQueryCommand.BEGIN_TEST_BY_ID + SEPARATOR + testId;
    }

    public static String rateArticle(long articleId, int rating) {
        return CallbackQueryCommand.RATE_ARTICLE_BY_ID + SEPARATOR + articleId + SEPARATOR + rating;
    }

    public static String chooseForQuestion(long questionId, long answerId) {
        return CHOOSE_FOR_QUESTION_WITH_ID + SEPARATOR + questionId + SEPARATOR + ANSWER + SEPARATOR + answerId;
    }

    public static String chooseMultipleForQuestion(long questionId) {
        return MULTIPLE_CHOOSE_FOR_QUESTION_WITH_ID + SEPARATOR + questionId + SEPARATOR + ANSWER;
    }

    public static String chooseMultipleForQuestion(long questionId, Set<Long> answersIds) {
        StringBuilder sb = new StringBuilder(chooseMultipleForQuestion(questionId));
        answersIds.forEach(answerId -> {
            sb.append(SEPARATOR);
            sb.append(answerId);
        });
        return sb.toString();
    }

    public static String selectMultipleForQuestion(long questionId, long answerId) {
        return SELECT_MULTIPLE_FOR_QUESTION_WITH_ID + SEPARATOR + questionId + SEPARATOR + ANSWER + SEPARATOR + answerId;
    }

    public static String showCorrectAnswersForTest(long testId) {
        return SHOW_CORRECT_ANSWERS_FOR_TEST_WITH_ID + SEPARATOR + testId;
    }
}
