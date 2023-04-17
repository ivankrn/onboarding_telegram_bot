import { Article } from "./article";

export class ArticleStatistic {
    id: number;
    article: Article;
    ratingsSum: number;
    totalRatingsCount: number;
}