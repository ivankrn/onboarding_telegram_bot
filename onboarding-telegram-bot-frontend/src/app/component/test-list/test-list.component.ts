import { Component, OnInit } from '@angular/core';
import { Test } from 'src/app/model/test';
import { TestService } from 'src/app/service/test.service';

@Component({
  selector: 'app-test-list',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.css']
})
export class TestListComponent implements OnInit {

  currentPage: number = 0;
  totalElements: number = 0;
  showLimit: number = 10;
  tests: Test[];

  constructor(private testService: TestService) { }

  ngOnInit(): void {
    this.updateList({page: this.currentPage, size: this.showLimit});
  }

  updateList(request: any) {
    this.testService.findAll(request).subscribe(data => {
      this.tests = data["content"];
      this.totalElements = data["totalElements"];
    });
  }

  onDelete(test: Test) {
    this.testService.delete(test.id).subscribe(() => {
      this.tests = this.tests.filter(item => item.id !== test.id);
    });
  }

  changePage(pageNumber: number) {
    this.currentPage = pageNumber - 1;
    this.updateList({page: this.currentPage, size: this.showLimit});
  }
}
