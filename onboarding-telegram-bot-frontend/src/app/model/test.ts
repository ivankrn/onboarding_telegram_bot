import { Article } from "./article";
import { ArticleTopic } from "./article-topic";
import { TestQuestion } from "./test-question";

export interface Test {
    id: number;
    topic: ArticleTopic;
    article: Article;
    title: string;
    description: string;
    questions: TestQuestion[];
    createdAt: Date;
}