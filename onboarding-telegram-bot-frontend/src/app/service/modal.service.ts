import { Injectable } from '@angular/core';
import { ModalComponent } from '../component/modal/modal.component';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  private modals: ModalComponent[] = [];

  add(modal: ModalComponent) {
    if (!modal.id || this.modals.find(m => m.id === modal.id)) {
      throw new Error("Modal must have an unique id!");
    }
    this.modals.push(modal);
  }

  remove(modal: ModalComponent) {
    this.modals = this.modals.filter(m => m != modal);
  }

  open(id: string) {
    const modal = this.modals.find(m => m.id === id);
    if (!modal) {
      throw new Error(`Modal ${id} not found!`);
    }
    modal.open();
  }

  close(id: string) {
    const modal = this.modals.find(m => m.isOpen && m.id === id);
    modal?.close();
  }
}
