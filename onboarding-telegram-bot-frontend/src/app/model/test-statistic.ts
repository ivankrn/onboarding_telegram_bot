import { Test } from "./test";

export interface TestStatistic {
    id: number;
    test: Test;
    correctAnswersCount: number;
    totalAnswersCount: number;
    totalAttemptsCount: number;
}