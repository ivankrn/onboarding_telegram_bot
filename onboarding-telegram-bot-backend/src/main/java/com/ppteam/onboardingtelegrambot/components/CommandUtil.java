package com.ppteam.onboardingtelegrambot.components;

import com.ppteam.onboardingtelegrambot.CallbackQueryCommand;
import org.aspectj.weaver.ast.Call;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandUtil {

    private static final String INVALID_COMMAND = "Invalid command!";

    public static String parseCommand(String[] message) {
        return message[0];
    }

    public static long parseTopicId(String[] message) {
        String command = parseCommand(message);
        if (!command.equals(CallbackQueryCommand.BROWSE_ARTICLES_BY_TOPIC_ID)
                && !command.equals(CallbackQueryCommand.BROWSE_TESTS_BY_TOPIC_ID)) {
            throw new IllegalArgumentException(INVALID_COMMAND);
        }
        return Long.parseLong(message[1]);
    }

    public static boolean parseTestBrowsingMode(String[] message) {
        String command = parseCommand(message);
        if (!command.equals(CallbackQueryCommand.GET_TOPICS)) {
            throw new IllegalArgumentException(INVALID_COMMAND);
        }
        return message[1].equals(CallbackQueryCommand.TEST);
    }

    public static int parsePageNumber(String[] message) {
        String command = parseCommand(message);
        if (!command.equals(CallbackQueryCommand.BROWSE_ARTICLES_BY_TOPIC_ID)
                && !command.equals(CallbackQueryCommand.BROWSE_TESTS_BY_TOPIC_ID)) {
            throw new IllegalArgumentException(INVALID_COMMAND);
        }
        return Integer.parseInt(message[3]);
    }

    public static long parseArticleId(String[] message) {
        String command = parseCommand(message);
        if (!command.equals(CallbackQueryCommand.BROWSE_ARTICLE_BY_ID)
                && !command.equals(CallbackQueryCommand.RATE_ARTICLE_BY_ID)) {
            throw new IllegalArgumentException(INVALID_COMMAND);
        }
        return Long.parseLong(message[1]);
    }

    public static int parseRating(String[] message) {
        String command = parseCommand(message);
        if (!command.equals(CallbackQueryCommand.RATE_ARTICLE_BY_ID)) {
            throw new IllegalArgumentException(INVALID_COMMAND);
        }
        return Integer.parseInt(message[2]);
    }

    public static long parseTestId(String[] message) {
        String command = parseCommand(message);
        if (!command.equals(CallbackQueryCommand.BEGIN_TEST_BY_ID)
                && !command.equals(CallbackQueryCommand.SHOW_CORRECT_ANSWERS_FOR_TEST_WITH_ID)) {
            throw new IllegalArgumentException(INVALID_COMMAND);
        }
        return Long.parseLong(message[1]);
    }

    public static long parseQuestionId(String[] message) {
        String command = parseCommand(message);
        if (!command.equals(CallbackQueryCommand.CHOOSE_FOR_QUESTION_WITH_ID)
                && !command.equals(CallbackQueryCommand.MULTIPLE_CHOOSE_FOR_QUESTION_WITH_ID)
                && !command.equals(CallbackQueryCommand.SELECT_MULTIPLE_FOR_QUESTION_WITH_ID)) {
            throw new IllegalArgumentException(INVALID_COMMAND);
        }
        return Long.parseLong(message[1]);
    }

    public static long parseAnswerId(String[] message) {
        String command = parseCommand(message);
        if (!command.equals(CallbackQueryCommand.CHOOSE_FOR_QUESTION_WITH_ID)
                && !command.equals(CallbackQueryCommand.SELECT_MULTIPLE_FOR_QUESTION_WITH_ID)) {
            throw new IllegalArgumentException(INVALID_COMMAND);
        }
        return Long.parseLong(message[3]);
    }

    public static Set<Long> parseAnswersIds(String[] message) {
        String command = parseCommand(message);
        if (!command.equals(CallbackQueryCommand.MULTIPLE_CHOOSE_FOR_QUESTION_WITH_ID)) {
            throw new IllegalArgumentException(INVALID_COMMAND);
        }
        return Arrays.stream(message).skip(3).map(Long::parseLong)
                .collect(Collectors.toSet());
    }

    public static String[] split(String receivedMessage) {
        return receivedMessage.split(CallbackQueryCommand.SEPARATOR);
    }
}
