import { Component, OnInit } from '@angular/core';
import { TestStatistic } from 'src/app/model/test-statistic';
import { ArticleService } from 'src/app/service/article.service';
import { TestStatisticService } from 'src/app/service/test-statistic.service';
import { TestService } from 'src/app/service/test.service';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  currentPage: number = 0;
  totalElements: number = 0;
  showLimit: number = 10;

  statistics: TestStatistic[];
  articleTotalCount: number;
  testTotalCount: number;

  constructor(private articleService: ArticleService, private testService: TestService, 
    private testStatisticService: TestStatisticService) { }

  ngOnInit(): void {
    this.updateList({page: this.currentPage, size: this.showLimit});
    this.updateCounts();
  }

  updateList(request: any) {
    this.testStatisticService.findAll(request).subscribe(data => {
      this.statistics = data["content"];
      this.totalElements = data["totalElements"];
    })
  }

  updateCounts() {
    this.articleService.count().subscribe(data => this.articleTotalCount = data);
    this.testService.count().subscribe(data => this.testTotalCount = data);
  }

  changePage(pageNumber: number) {
    this.currentPage = pageNumber - 1;
    this.updateList({page: this.currentPage, size: this.showLimit});
  }
}
