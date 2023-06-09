import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Article } from 'src/app/model/article';
import { ArticleTopic } from 'src/app/model/article-topic';
import { ArticleTopicService } from 'src/app/service/article-topic.service';
import { ModalService } from 'src/app/service/modal.service';

@Component({
  selector: 'app-article-topic-form',
  templateUrl: './article-topic-form.component.html',
  styleUrls: ['./article-topic-form.component.css']
})
export class ArticleTopicFormComponent implements OnInit {

  topic: ArticleTopic;
  articleTopics: ArticleTopic[];
  form = new FormGroup({
    name: new FormControl(null, Validators.required)
  });
  topicToDeleteId: number;
  @Output()
  deletedTopicId = new EventEmitter<number>;

  constructor(private articleTopicService: ArticleTopicService, private modalService: ModalService) { }

  ngOnInit() {
    this.topic = <ArticleTopic>{};
    this.updateTopics();
  }

  updateTopics() {
    this.articleTopicService.findAll().subscribe(data => {
      this.articleTopics = data;
    });
  }

  deleteTopicById(id: number) {
    this.articleTopicService.delete(id).subscribe(() => {
      this.articleTopics = this.articleTopics.filter(topic => topic.id !== id);
      this.deletedTopicId.emit(id);
      this.closeDeletionConfirmationPopup();
    });
  }

  onSubmit() {
    if (this.form.valid) {
      if (this.topic.id === undefined) {
        this.topic = <ArticleTopic><unknown>this.form.value;
        this.articleTopicService.create(this.topic).subscribe(() => {
          this.updateTopics();
        });
      } else {
        const topicIdToChange = this.topic.id;
        this.topic = this.getChangedFormValues(this.form);
        this.articleTopicService.updatePartial(topicIdToChange, this.topic).subscribe(() => {
          this.updateTopics();
          this.closeEditPopup();
        })
      }
      this.form.controls["name"].patchValue(null);
    }
  }

  private getChangedFormValues(form: any) {
    const changedValues: any = {};
    Object.keys(form.controls)
    .forEach(key => {
        let currentControl = form.controls[key];
        if (currentControl.dirty) {
            if (currentControl.controls) {
              changedValues[key] = this.getChangedFormValues(currentControl);
            } else {
              changedValues[key] = currentControl.value;
            }
        }
    });
    return changedValues;
  }

  openEditPopup(topicToEditId: number) {
    this.topic.id = topicToEditId;
    this.modalService.open("topic-edit-popup");
  }

  closeEditPopup() {
    this.modalService.close("topic-edit-popup");
    this.afterCloseEditPopup();
  }

  afterCloseEditPopup() {
    this.topic = <ArticleTopic>{};
    this.form.reset();
  }

  openDeletionConfirmationPopup(topicToDeleteId: number) {
    this.topicToDeleteId = topicToDeleteId;
    this.modalService.open("topic-deletion-popup");
  }

  closeDeletionConfirmationPopup() {
    this.modalService.close("topic-deletion-popup");
  }
}
