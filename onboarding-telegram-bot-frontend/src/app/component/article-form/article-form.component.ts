import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { map, Observable } from 'rxjs';
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
    private articleService: ArticleService, private articleTopicService: ArticleTopicService, private testService: TestService) {
    this.article = new Article();
    this.updateTopics();
    this.updateTests();
  }

  ngOnInit() {
    this.route.paramMap.pipe(map(() => window.history.state)).subscribe(data => {
      this.article = data;
      this.form.patchValue(data);
    });
  }

  public onSubmit() {
    this.article = Object.assign(this.article, this.form.value);
    if (this.form.value.test!.id == null) {
      this.article.test = undefined;
    }
    this.articleService.save(this.article).subscribe(() => this.goToArticleList());
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
