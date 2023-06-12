package com.ppteam.onboardingtelegrambot.components;

import com.ppteam.onboardingtelegrambot.CallbackQueryCommand;
import com.ppteam.onboardingtelegrambot.dto.*;
import org.aspectj.weaver.ast.Call;
import org.springframework.data.domain.Page;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Buttons {


    public static final String SEND_MULTIPLE_CHOICE_TEXT = "Отправить выбранные ответы";
    private static final String uncheckedIcon = "\u2610";
    private static final String checkedIcon = "\u2611";

    public static InlineKeyboardMarkup startBotMarkup() {
        InlineKeyboardButton browseArticlesButton = new InlineKeyboardButton("Почитать статьи");
        browseArticlesButton.setCallbackData(CallbackQueryCommand.getTopicsForArticles());
        InlineKeyboardButton browseTestsButton = new InlineKeyboardButton("Порешать тесты");
        browseTestsButton.setCallbackData(CallbackQueryCommand.getTopicsForTests());
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(List.of(browseArticlesButton));
        rows.add(List.of(browseTestsButton));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup topicChoiceMarkup(List<ArticleTopicDto> topics, boolean isTestBrowsingMode) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        topics.forEach(topic -> {
            InlineKeyboardButton button = new InlineKeyboardButton(topic.getName());
            if (isTestBrowsingMode) {
                button.setCallbackData(CallbackQueryCommand.browseTestsByTopic(topic.getId(), 0));
            } else {
                button.setCallbackData(CallbackQueryCommand.browseArticlesByTopic(topic.getId(), 0));
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
            button.setCallbackData(CallbackQueryCommand.browseArticle(article.getId()));
            rows.add(List.of(button));
        });
        List<InlineKeyboardButton> pagination = new ArrayList<>(2);
        int currentPageNumber = articles.getPageable().getPageNumber();
        if (currentPageNumber > 0) {
            InlineKeyboardButton previousPageButton = new InlineKeyboardButton("<");
            previousPageButton.setCallbackData(
                    CallbackQueryCommand.browseArticlesByTopic(topicId, currentPageNumber - 1));
            pagination.add(previousPageButton);
        }
        if (currentPageNumber < articles.getTotalPages() - 1) {
            InlineKeyboardButton nextPageButton = new InlineKeyboardButton(">");
            nextPageButton.setCallbackData(
                    CallbackQueryCommand.browseArticlesByTopic(topicId, currentPageNumber + 1));
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
            button.setCallbackData(CallbackQueryCommand.beginTest(test.getId()));
            rows.add(List.of(button));
        });
        List<InlineKeyboardButton> pagination = new ArrayList<>(2);
        int currentPageNumber = tests.getPageable().getPageNumber();
        if (currentPageNumber > 0) {
            InlineKeyboardButton previousPageButton = new InlineKeyboardButton("<");
            previousPageButton.setCallbackData(
                    CallbackQueryCommand.browseTestsByTopic(topicId, currentPageNumber - 1));
            pagination.add(previousPageButton);
        }
        if (currentPageNumber < tests.getTotalPages() - 1) {
            InlineKeyboardButton nextPageButton = new InlineKeyboardButton(">");
            nextPageButton.setCallbackData(
                    CallbackQueryCommand.browseTestsByTopic(topicId, currentPageNumber + 1));
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
            rateButton.setCallbackData(CallbackQueryCommand.rateArticle(articleId, rating));
            row.add(rateButton);
        }
        List<List<InlineKeyboardButton>> rows = List.of(row);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup articleActionsMenuMarkup(ArticleDto article) {
        InlineKeyboardButton showSameTopicArticlesButton = new InlineKeyboardButton("Показать статьи по теме");
        showSameTopicArticlesButton.setCallbackData(
                CallbackQueryCommand.browseArticlesByTopic(article.getTopic().getId(), 0));
        InlineKeyboardButton backButton = getReturnBackButton();
        List<List<InlineKeyboardButton>> rows = List.of(List.of(showSameTopicArticlesButton), List.of(backButton));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup offerTestMarkup(long testId) {
        InlineKeyboardButton beginTestButton = new InlineKeyboardButton("Начать тест");
        beginTestButton.setCallbackData(CallbackQueryCommand.beginTest(testId));
        List<List<InlineKeyboardButton>> rows = List.of(List.of(beginTestButton));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup testAnswerSingleChoiceMarkup(TestQuestionFullDto question) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (int i = 0; i < question.getAnswers().size(); i++) {
            InlineKeyboardButton button = new InlineKeyboardButton(String.valueOf(i + 1));
            button.setCallbackData(CallbackQueryCommand.chooseForQuestion(question.getId(),
                    question.getAnswers().get(i).getId()));
            rows.add(List.of(button));
        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup testAnswerMultipleChoiceMarkup(TestQuestionFullDto question) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (int i = 0; i < question.getAnswers().size(); i++) {
            InlineKeyboardButton button = new InlineKeyboardButton(uncheckedIcon + " " + (i + 1));
            button.setCallbackData(CallbackQueryCommand.selectMultipleForQuestion(question.getId(),
                    question.getAnswers().get(i).getId()));
            rows.add(List.of(button));
        }
        InlineKeyboardButton sendButton = new InlineKeyboardButton(SEND_MULTIPLE_CHOICE_TEXT);
        sendButton.setCallbackData(CallbackQueryCommand.chooseMultipleForQuestion(question.getId()));
        rows.add(List.of(sendButton));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup editTestAnswerMultipleChoiceMarkup(InlineKeyboardMarkup previousMarkup,
                                                                          long lastSelectedAnswerId) {
        List<List<InlineKeyboardButton>> previousKeyboard = previousMarkup.getKeyboard();
        List<List<InlineKeyboardButton>> newKeyboard = copyKeyboard(previousKeyboard);
        for (List<InlineKeyboardButton> row : newKeyboard) {
            InlineKeyboardButton answerButton = row.get(0);
            if (answerButton.getText().equals(Buttons.SEND_MULTIPLE_CHOICE_TEXT)) {
                continue;
            }
            String[] buttonCallbackData = CommandUtil.split(answerButton.getCallbackData());
            long buttonAnswerId = CommandUtil.parseAnswerId(buttonCallbackData);
            if (buttonAnswerId == lastSelectedAnswerId) {
                if (answerButton.getText().contains(checkedIcon)) {
                    answerButton.setText(answerButton.getText().replace(checkedIcon, uncheckedIcon));
                } else {
                    answerButton.setText(answerButton.getText().replace(uncheckedIcon, checkedIcon));
                }
                break;
            }
        }
        List<InlineKeyboardButton> selectedButtons = new ArrayList<>();
        newKeyboard.forEach(row -> selectedButtons.addAll(getButtonsContainingString(row, checkedIcon)));
        Set<Long> selectedAnswersIds = new HashSet<>();
        selectedButtons.forEach(button -> {
            String[] buttonCallbackData = CommandUtil.split(button.getCallbackData());
            long buttonAnswerId = CommandUtil.parseAnswerId(buttonCallbackData);
            selectedAnswersIds.add(buttonAnswerId);
        });
        for (List<InlineKeyboardButton> row : newKeyboard) {
            InlineKeyboardButton button = row.get(0);
            if (button.getText().equals(Buttons.SEND_MULTIPLE_CHOICE_TEXT)) {
                String[] buttonCallbackData = CommandUtil.split(button.getCallbackData());
                long questionId = CommandUtil.parseQuestionId(buttonCallbackData);
                String newCallbackData = CallbackQueryCommand.chooseMultipleForQuestion(questionId, selectedAnswersIds);
                button.setCallbackData(newCallbackData);
                break;
            }
        }
        return new InlineKeyboardMarkup(newKeyboard);
    }

    public static InlineKeyboardMarkup testActionsMenuMarkup(TestDto test) {
        InlineKeyboardButton beginTestAgainButton = new InlineKeyboardButton("Начать тест заново");
        beginTestAgainButton.setCallbackData(CallbackQueryCommand.beginTest(test.getId()));
        InlineKeyboardButton showSameTopicTestsButton = new InlineKeyboardButton("Показать тесты по теме");
        showSameTopicTestsButton.setCallbackData(
                CallbackQueryCommand.browseArticlesByTopic(test.getTopic().getId(), 0));
        InlineKeyboardButton showCorrectAnswersButton = new InlineKeyboardButton("Показать правильные ответы");
        showCorrectAnswersButton.setCallbackData(CallbackQueryCommand.showCorrectAnswersForTest(test.getId()));
        InlineKeyboardButton backButton = getReturnBackButton();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>(4);
        rows.add(List.of(beginTestAgainButton));
        rows.add(List.of(showSameTopicTestsButton));
        rows.add(List.of(showCorrectAnswersButton));
        rows.add(List.of(backButton));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    private static InlineKeyboardButton getReturnBackButton() {
        InlineKeyboardButton backButton = new InlineKeyboardButton("\u21A9 Назад");
        backButton.setCallbackData(CallbackQueryCommand.START);
        return backButton;
    }

    private static List<InlineKeyboardButton> getButtonsContainingString(List<InlineKeyboardButton> buttons, String str) {
        return buttons.stream().filter(button -> button.getText().contains(str)).map(Buttons::copyButton).toList();
    }

    private static List<List<InlineKeyboardButton>> copyKeyboard(List<List<InlineKeyboardButton>> keyboard) {
        List<List<InlineKeyboardButton>> newKeyboard = new ArrayList<>(keyboard.size());
        for (int i = 0; i < keyboard.size(); i++) {
            List<InlineKeyboardButton> row = keyboard.get(i);
            newKeyboard.add(new ArrayList<>(row.size()));
            for (InlineKeyboardButton button : row) {
                newKeyboard.get(i).add(copyButton(button));
            }
        }
        return newKeyboard;
    }

    private static InlineKeyboardButton copyButton(InlineKeyboardButton button) {
        InlineKeyboardButton newButton = new InlineKeyboardButton(button.getText());
        newButton.setCallbackData(button.getCallbackData());
        return newButton;
    }
}
