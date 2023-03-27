import { ArticleTopic } from "./article-topic";

export class Article {
    id: number;
    topic: ArticleTopic;
    title: string;
    content: string;
    usefulLinks: string;
    testLink: string;
    createdAt: Date;
}
