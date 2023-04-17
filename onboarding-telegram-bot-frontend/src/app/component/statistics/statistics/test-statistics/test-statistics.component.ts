import { Component, OnInit } from '@angular/core';
import { TestStatistic } from 'src/app/model/test-statistic';
import { TestStatisticService } from 'src/app/service/test-statistic.service';

@Component({
  selector: 'app-test-statistics',
  templateUrl: './test-statistics.component.html',
  styleUrls: ['./test-statistics.component.css']
})
export class TestStatisticsComponent implements OnInit {

  currentPage: number = 0;
  totalElements: number = 0;
  showLimit: number = 10;

  statistics: TestStatistic[];

  constructor(private testStatisticService: TestStatisticService) { }

  ngOnInit(): void {
    this.updateList({page: this.currentPage, size: this.showLimit});
  }

  updateList(request: any) {
    this.testStatisticService.findAll(request).subscribe(data => {
      this.statistics = data["content"];
      this.totalElements = data["totalElements"];
    });
  }

  changePage(pageNumber: number) {
    this.currentPage = pageNumber - 1;
    this.updateList({page: this.currentPage, size: this.showLimit});
  }

}
