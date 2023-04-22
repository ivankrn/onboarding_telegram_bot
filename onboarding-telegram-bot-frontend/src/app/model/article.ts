import { ArticleTopic } from "./article-topic";
import { Test } from "./test";

export interface Article {
    id: number;
    topic: ArticleTopic;
    title: string;
    content: string;
    usefulLinks: string;
    test?: Test;
    createdAt: Date;
}
