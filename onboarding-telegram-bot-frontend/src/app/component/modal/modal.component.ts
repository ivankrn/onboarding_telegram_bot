import { Component, ElementRef, EventEmitter, Input, OnDestroy, OnInit, Output, TemplateRef, ViewEncapsulation } from '@angular/core';
import { ModalService } from 'src/app/service/modal.service';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ModalComponent implements OnInit, OnDestroy {
  
  @Input()
  id?: string;
  @Output()
  onClose = new EventEmitter<void>();
  isOpen = false;
  private element: any;

  constructor(private modalService: ModalService, private elem: ElementRef) {
    this.element = elem.nativeElement;
  }

  ngOnInit() {
    this.modalService.add(this);
    document.body.appendChild(this.element);
    this.element.addEventListener('click', (elem: any) => {
      if (elem.target.className === "my-modal") {
        this.close();
      }
    })
  }

  ngOnDestroy() {
    this.modalService.remove(this);
    this.element.remove();
  }

  open() {
    this.element.style.display = 'block';
    document.body.classList.add("my-modal-open");
    this.isOpen = true;
  }

  close() {
    this.element.style.display = 'none';
    document.body.classList.remove("my-modal-open");
    this.isOpen = false;
    this.onClose.emit();
  }
}
