import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit {
  @Input()
  currentPage: number = 1;
  @Input()
  total: number = 0;
  @Input()
  limit: number = 10;
  @Output()
  changePage = new EventEmitter<number>();

  pages: number[] = [];

  ngOnInit() {
    const pagesCount = Math.ceil(this.total / this.limit);
    this.pages = this.generateRange(1, pagesCount);
  }

  generateRange(start: number, end: number): number[] {
    const result = [];
    for (let i = start; i <= end; i++) {
      result.push(i);
    }
    return result;
  }
}
