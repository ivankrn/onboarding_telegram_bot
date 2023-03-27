import { Component, OnInit } from '@angular/core';
import { ArticleService } from 'src/app/service/article.service';
import { TestService } from 'src/app/service/test.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  articleTotalCount: number;
  testTotalCount: number;

  constructor(private articleService: ArticleService, private testService: TestService) { }

  ngOnInit(): void {
    this.updateCounts();
  }

  updateCounts() {
    this.articleService.count().subscribe(data => this.articleTotalCount = data);
    this.testService.count().subscribe(data => this.testTotalCount = data);
  }

}
