import { Test } from "./test";
import { TestAnswer } from "./test-answer";

export interface TestQuestion {
    id: number;
    test: Test;
    question: string;
    answers: TestAnswer[];
}