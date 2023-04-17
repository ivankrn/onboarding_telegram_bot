package com.ppteam.onboardingtelegrambot.components;

import com.ppteam.onboardingtelegrambot.CallbackQueryCommand;
import com.ppteam.onboardingtelegrambot.dto.*;
import org.springframework.data.domain.Page;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Buttons {

    public static InlineKeyboardMarkup startBotMarkup() {
        InlineKeyboardButton browseArticlesButton = new InlineKeyboardButton("Почитать статьи");
        browseArticlesButton.setCallbackData(CallbackQueryCommand.GET_TOPICS + " " + CallbackQueryCommand.ARTICLE + " "
                + CallbackQueryCommand.PAGE + " 0");
        InlineKeyboardButton browseTestsButton = new InlineKeyboardButton("Порешать тесты");
        browseTestsButton.setCallbackData(CallbackQueryCommand.GET_TOPICS + " " + CallbackQueryCommand.TEST + " "
                + CallbackQueryCommand.PAGE + " 0");
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

    public static InlineKeyboardMarkup topicChoiceMarkup(List<ArticleTopicDto> topics, boolean isTestBrowsingMode) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        topics.forEach(topic -> {
            InlineKeyboardButton button = new InlineKeyboardButton(topic.getName());
            if (isTestBrowsingMode) {
                button.setCallbackData(CallbackQueryCommand.BROWSE_TESTS_BY_TOPIC_ID + " " + topic.getId() + " "
                        + CallbackQueryCommand.PAGE + " 0");
            } else {
                button.setCallbackData(CallbackQueryCommand.BROWSE_ARTICLES_BY_TOPIC_ID + " " + topic.getId() + " "
                        + CallbackQueryCommand.PAGE + " 0");
            }
            rows.add(List.of(button));
        });
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup articleChoiceMarkup(Page<ArticleDto> articles, long topicId) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        articles.get().forEach(article -> {
            InlineKeyboardButton button = new InlineKeyboardButton(article.getTitle());
            button.setCallbackData(CallbackQueryCommand.BROWSE_ARTICLE_BY_ID + " " + article.getId());
            rows.add(List.of(button));
        });
        List<InlineKeyboardButton> pagination = new ArrayList<>(2);
        int currentPageNumber = articles.getPageable().getPageNumber();
        if (currentPageNumber > 0) {
            InlineKeyboardButton previousPageButton = new InlineKeyboardButton("<");
            previousPageButton.setCallbackData(CallbackQueryCommand.BROWSE_ARTICLES_BY_TOPIC_ID + " " + topicId + " "
                    + CallbackQueryCommand.PAGE + " " + (currentPageNumber - 1));
            pagination.add(previousPageButton);
        }
        if (currentPageNumber < articles.getTotalPages() - 1) {
            InlineKeyboardButton nextPageButton = new InlineKeyboardButton(">");
            nextPageButton.setCallbackData(CallbackQueryCommand.BROWSE_ARTICLES_BY_TOPIC_ID + " " + topicId + " "
                    + CallbackQueryCommand.PAGE + " " + (currentPageNumber + 1));
            pagination.add(nextPageButton);
        }
        rows.add(pagination);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup testChoiceMarkup(Page<TestDto> tests, long topicId) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        tests.get().forEach(test -> {
            InlineKeyboardButton button = new InlineKeyboardButton(test.getTitle());
            button.setCallbackData(CallbackQueryCommand.BEGIN_TEST_BY_ID + " " + test.getId());
            rows.add(List.of(button));
        });
        List<InlineKeyboardButton> pagination = new ArrayList<>(2);
        int currentPageNumber = tests.getPageable().getPageNumber();
        if (currentPageNumber > 0) {
            InlineKeyboardButton previousPageButton = new InlineKeyboardButton("<");
            previousPageButton.setCallbackData(CallbackQueryCommand.BROWSE_TESTS_BY_TOPIC_ID + " " + topicId + " "
                    + CallbackQueryCommand.PAGE + " " + (currentPageNumber - 1));
            pagination.add(previousPageButton);
        }
        if (currentPageNumber < tests.getTotalPages() - 1) {
            InlineKeyboardButton nextPageButton = new InlineKeyboardButton(">");
            nextPageButton.setCallbackData(CallbackQueryCommand.BROWSE_TESTS_BY_TOPIC_ID + " " + topicId + " "
                    + CallbackQueryCommand.PAGE + " " + (currentPageNumber + 1));
            pagination.add(nextPageButton);
        }
        rows.add(pagination);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup articleRatingMarkup(long articleId) {
        List<InlineKeyboardButton> row = new ArrayList<>(5);
        for (int rating = 1; rating <= 5; rating++) {
            InlineKeyboardButton rateButton = new InlineKeyboardButton(String.valueOf(rating));
            rateButton.setCallbackData(CallbackQueryCommand.RATE_ARTICLE_BY_ID + " " + articleId + " " + rating);
            row.add(rateButton);
        }
        List<List<InlineKeyboardButton>> rows = List.of(row);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup offerTestMarkup(long testId) {
        InlineKeyboardButton beginTestButton = new InlineKeyboardButton("Начать тест");
        beginTestButton.setCallbackData(CallbackQueryCommand.BEGIN_TEST_BY_ID + " " + testId);
        List<List<InlineKeyboardButton>> rows = List.of(List.of(beginTestButton));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup testAnswerChoiceMarkup(TestQuestionFullDto question) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (TestAnswerDto answer : question.getAnswers()) {
            InlineKeyboardButton button = new InlineKeyboardButton(answer.getAnswer());
            button.setCallbackData(CallbackQueryCommand.CHOOSE_FOR_QUESTION_WITH_ID + " " + question.getId() + " "
                    + CallbackQueryCommand.ANSWER + " " + answer.getId());
            rows.add(List.of(button));
        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }
}
