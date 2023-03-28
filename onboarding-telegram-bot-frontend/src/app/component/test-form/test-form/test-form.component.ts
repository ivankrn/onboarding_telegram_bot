import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { map, Observable } from 'rxjs';
import { Article } from 'src/app/model/article';
import { Test } from 'src/app/model/test';
import { ArticleTopicService } from 'src/app/service/article-topic.service';
import { ArticleService } from 'src/app/service/article.service';
import { TestService } from 'src/app/service/test.service';

@Component({
  selector: 'app-test-form',
  templateUrl: './test-form.component.html',
  styleUrls: ['./test-form.component.css']
})
export class TestFormComponent implements OnInit {

  test: Test;
  articleTopics: Observable<Map<number, string>>;
  form = new FormGroup({
    title: new FormControl("", Validators.required),
    description: new FormControl(""),
    topic: new FormGroup({
      id: new FormControl(null, Validators.required),
      title: new FormControl()
    }),
    questions: new FormArray([])
  });

  constructor(private route: ActivatedRoute, private router: Router, private testService: TestService,
    private articleService: ArticleService, private articleTopicService: ArticleTopicService) {
    this.updateTopics();
  }

  ngOnInit() {
    this.route.paramMap.pipe(map(() => window.history.state)).subscribe(data => {
      this.test = data;
      this.form.patchValue(data);
    });
  }

  public onSubmit() {
    console.log(this.form.value);
    // this.test = Object.assign(this.test, this.form.value);
    // this.testService.save(this.test).subscribe(() => this.goToTestList());
  }

  public goToTestList() {
    this.router.navigate(['/tests']);
  }

  updateTopics() {
    this.articleTopics = this.articleTopicService.getTopicsNames();
  }

  get questions() {
    return this.form.get("questions") as FormArray;
  }

  getAnswersByQuestionId(id: number) {
    return this.questions.controls[id].get("answers") as FormArray;
  }

  addQuestion() {
    this.questions.push(new FormGroup({
      question: new FormControl("", Validators.required),
      answers: new FormArray([]),
      correctAnswer: new FormControl(null, Validators.required)
    }));
    console.log(this.form.value);
  }

  addAnswerToId(questionId: number) {
    this.getAnswersByQuestionId(questionId).push(new FormGroup({
      answer: new FormControl("", Validators.required)
    }));
    console.log(this.form.value);
  }
}