import { Test } from "./test";
import { TestAnswer } from "./test-answer";

export class TestQuestion {
    id: number;
    test: Test;
    question: string;
    answers: TestAnswer[];
}