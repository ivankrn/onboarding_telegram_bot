import { Component, OnInit } from '@angular/core';
import { Article } from 'src/app/model/article';
import { ArticleTopic } from 'src/app/model/article-topic';
import { ArticleTopicService } from 'src/app/service/article-topic.service';
import { ArticleService } from 'src/app/service/article.service';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.css']
})
export class ArticleListComponent implements OnInit {

  currentPage: number = 0;
  totalElements: number = 0;
  showLimit: number = 10;

  articles: Article[];
  articleTopics: ArticleTopic[];

  constructor(private articleService: ArticleService, private articleTopicService: ArticleTopicService) {}

  ngOnInit() {
    this.updateList({page: this.currentPage, size: this.showLimit});
    this.updateTopics();
  }

  onDelete(article: Article) {
    this.articleService.delete(article.id).subscribe(() => {
      this.articles = this.articles.filter(item => item.id !== article.id);
    });
  }

  updateList(request: any) {
    this.articleService.findAll(request).subscribe(data => {
      this.articles = data["content"];
      this.totalElements = data["totalElements"];
    });
  }

  updateTopics() {
    this.articleTopicService.findAll().subscribe(data => this.articleTopics = data);
  }

  changePage(pageNumber: number) {
    this.currentPage = pageNumber - 1;
    this.updateList({page: this.currentPage, size: this.showLimit});
  }

}
