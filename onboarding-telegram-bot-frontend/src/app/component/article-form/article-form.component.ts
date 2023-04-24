import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { AlertService } from 'src/app/alert/service/alert.service';
import { Article } from 'src/app/model/article';
import { ArticleTopicService } from 'src/app/service/article-topic.service';
import { ArticleService } from 'src/app/service/article.service';
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
  articleTopics: Observable<Map<number, string>>;
  tests: Observable<Map<number, string>>;
  form = new FormGroup({
    title: new FormControl("", Validators.required),
    content: new FormControl("", Validators.required),
    topic: new FormGroup({
      id: new FormControl(null, Validators.required),
      name: new FormControl()
    }),
    usefulLinks: new FormControl(""),
    test: new FormGroup({
      id: new FormControl(null),
      title: new FormControl()
    })
  });

  constructor(private route: ActivatedRoute, private router: Router, 
    private articleService: ArticleService, private articleTopicService: ArticleTopicService, 
    private testService: TestService, private alertService: AlertService) {
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
      this.article = Object.assign(this.article, this.form.value);
      if (this.form.value.test!.id == null) {
        this.article.test = undefined;
      }
      this.articleService.save(this.article).subscribe({
        next: () => {
          const successMessage = this.articleId === undefined ? "Статья успешно добавлена!" : "Статья успешно изменена!";
          this.alertService.success(successMessage);
          this.goToArticleList();
        },
        error: () => this.alertService.error("Произошла ошибка при добавлении статьи!"),
      });
    }
  }

  public goToArticleList() {
    this.router.navigate(['/articles']);
  }

  updateTopics() {
    this.articleTopics = this.articleTopicService.getTopicsNames();
  }

  updateTests() {
    this.tests = this.testService.getTestsNames();
  }
}
