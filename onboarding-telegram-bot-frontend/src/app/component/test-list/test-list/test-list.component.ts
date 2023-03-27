import { Component, OnInit } from '@angular/core';
import { Test } from 'src/app/model/test';
import { TestService } from 'src/app/service/test.service';

@Component({
  selector: 'app-test-list',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.css']
})
export class TestListComponent implements OnInit {

  tests: Test[];

  constructor(private testService: TestService) { }

  ngOnInit(): void {
    this.updateList();
  }

  updateList() {
    this.testService.findAll().subscribe(data => {
      this.tests = data;
    });
  }

  onDelete(test: Test) {
    this.testService.delete(test.id).subscribe(() => {
      this.updateList();
    });
  }
}
