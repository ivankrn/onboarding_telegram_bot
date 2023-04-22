import { Article } from "./article";

export interface ArticleStatistic {
    id: number;
    article: Article;
    ratingsSum: number;
    totalRatingsCount: number;
}