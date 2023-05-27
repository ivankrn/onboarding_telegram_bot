import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { AlertService } from 'src/app/alert/service/alert.service';
import { Article } from 'src/app/model/article';
import { ArticleTopic } from 'src/app/model/article-topic';
import { ArticleTopicService } from 'src/app/service/article-topic.service';
import { ArticleService } from 'src/app/service/article.service';
import { ModalService } from 'src/app/service/modal.service';
import { TestService } from 'src/app/service/test.service';

@Component({
  selector: 'app-article-form',
  templateUrl: './article-form.component.html',
  styleUrls: ['./article-form.component.css']
})
export class ArticleFormComponent implements OnInit {

  articleId: number | undefined;
  paramsSub: Subscription;
  article: Article;
  articleTopics: ArticleTopic[];
  tests: Observable<Map<number, string>>;
  form = new FormGroup({
    title: new FormControl(null, Validators.required),
    content: new FormControl(null, Validators.required),
    topic: new FormGroup({
      id: new FormControl(null, Validators.required),
      name: new FormControl()
    }),
    usefulLinks: new FormControl(null),
    testId: new FormControl(null)
  });

  constructor(private route: ActivatedRoute, private router: Router, 
    private articleService: ArticleService, private articleTopicService: ArticleTopicService, 
    private testService: TestService, private alertService: AlertService,
    private modalService: ModalService) {
    this.article = <Article>{};
    this.updateTopics();
    this.updateTests();
  }

  ngOnInit() {
    this.paramsSub = this.route.params.subscribe(params => {
      if (params["id"] !== undefined) {
        this.articleId = +params["id"];
        this.articleService.findById(this.articleId).subscribe((data: any) => {
          this.article = data;
          this.form.patchValue(data);
        })
      }
    })
  }

  public onSubmit() {
    if (this.form.valid) {
      this.sanitizeFormData();
      if (this.articleId === undefined) {
        this.article = <Article><unknown>this.form.value;
        this.articleService.create(this.article).subscribe({
          next: () => {
            this.alertService.success("Статья успешно добавлена!");
            this.goToArticleList();
          },
          error: () => {
            this.alertService.error("Произошла ошибка при добавлении статьи!");
          },
        });
      } else {
        this.article = this.getChangedFormValues(this.form);
        this.articleService.updatePartial(this.articleId, this.article).subscribe({
          next: () => {
            this.alertService.success("Статья успешно изменена!");
            this.goToArticleList();
          },
          error: () => {
            this.alertService.error("Произошла ошибка при изменении статьи!");
          },
        });
      }
    }
  }

  private sanitizeFormData() {
    if ((this.form.get("usefulLinks")?.value as unknown as string)?.trim() === '') {
      this.form.controls["usefulLinks"].patchValue(null);
    }
  }

  private getChangedFormValues(form: any) {
    const changedValues: any = {};
    Object.keys(form.controls)
    .forEach(key => {
        let currentControl = form.controls[key];
        if (currentControl.dirty) {
            if (currentControl.controls)
            changedValues[key] = this.getChangedFormValues(currentControl);
            else
            changedValues[key] = currentControl.value;
        }
    });
    return changedValues;
  }

  public goToArticleList() {
    this.router.navigate(['/articles']);
  }

  updateTopics() {
    this.articleTopicService.findAll().subscribe(data => this.articleTopics = data);
  }

  updateTests() {
    this.tests = this.testService.getTestsNames();
  }

  openTopicPopup() {
    this.modalService.open("topic-popup");
  }
  
  updateTopicsAfterTopicDeletion(deletedTopicId: number) {
    this.articleTopics = this.articleTopics.filter(topic => topic.id !== deletedTopicId);
    if (this.article.topic && this.articleTopics.find(topic => topic.id === this.article.topic.id) === undefined) {
      this.goToArticleList();
    }
  }
}
