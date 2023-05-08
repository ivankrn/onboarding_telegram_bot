import { ArticleTopic } from "./article-topic";

export interface Article {
    id: number;
    topic: ArticleTopic;
    title: string;
    content: string;
    usefulLinks: string;
    testId?: number;
    createdAt: Date;
}
