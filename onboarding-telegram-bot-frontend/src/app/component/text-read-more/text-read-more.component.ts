import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-text-read-more',
  templateUrl: './text-read-more.component.html',
  styleUrls: ['./text-read-more.component.css']
})
export class TextReadMoreComponent {

  @Input()
  text: string;
  @Input()
  charLimit: number;
  isExpanded: boolean = false;

  constructor() { }

  switchTextState() {
    this.isExpanded = !this.isExpanded;
  }

}
