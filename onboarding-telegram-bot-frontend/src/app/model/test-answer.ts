import { TestQuestion } from "./test-question";

export interface TestAnswer {
    id: number;
    testQuestion: TestQuestion;
    answer: string;
    isCorrect: boolean;
}