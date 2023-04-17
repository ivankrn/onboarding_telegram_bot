import { Component, OnInit } from '@angular/core';
import { ArticleStatistic } from 'src/app/model/article-statistic';
import { ArticleStatisticService } from 'src/app/service/article-statistic.service';

@Component({
  selector: 'app-article-statistics',
  templateUrl: './article-statistics.component.html',
  styleUrls: ['./article-statistics.component.css']
})
export class ArticleStatisticsComponent implements OnInit {

  currentPage: number = 0;
  totalElements: number = 0;
  showLimit: number = 10;

  statistics: ArticleStatistic[];

  constructor(private articleStatisticService: ArticleStatisticService) { }

  ngOnInit(): void {
    this.updateList({page: this.currentPage, size: this.showLimit});
  }

  updateList(request: any) {
    this.articleStatisticService.findAll(request).subscribe(data => {
      this.statistics = data["content"];
      this.totalElements = data["totalElements"];
    });
  }

  changePage(pageNumber: number) {
    this.currentPage = pageNumber - 1;
    this.updateList({page: this.currentPage, size: this.showLimit});
  }

}
