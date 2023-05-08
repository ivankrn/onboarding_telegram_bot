import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { AlertService } from 'src/app/alert/service/alert.service';
import { ArticleTopic } from 'src/app/model/article-topic';
import { Test } from 'src/app/model/test';
import { ArticleTopicService } from 'src/app/service/article-topic.service';
import { ModalService } from 'src/app/service/modal.service';
import { TestService } from 'src/app/service/test.service';

@Component({
  selector: 'app-test-form',
  templateUrl: './test-form.component.html',
  styleUrls: ['./test-form.component.css']
})
export class TestFormComponent implements OnInit, OnDestroy {

  testId: number | undefined;
  paramsSub: Subscription;
  test: Test;
  articleTopics: ArticleTopic[];
  form = new FormGroup({
    title: new FormControl("", Validators.required),
    description: new FormControl(""),
    topic: new FormGroup({
      id: new FormControl(null, Validators.required),
      name: new FormControl()
    }),
    questions: new FormArray([], Validators.required)
  });

  constructor(private route: ActivatedRoute, private router: Router, private testService: TestService,
    private articleTopicService: ArticleTopicService, private alertService: AlertService,
    private modalService: ModalService) {
    this.test = <Test>{};
    this.updateTopics();
  }

  ngOnInit() {
    this.paramsSub = this.route.params.subscribe(params => {
      if (params["id"] !== undefined) {
        this.testId = +params["id"];
        this.testService.findById(this.testId).subscribe((data: any) => {
          this.test = data;
          this.form.patchValue(data);
          data.questions.forEach((q: { [x: string]: any; }) => {
            const questionAnswers = q["answers"].map((ans: { [x: string]: any; }) => new FormGroup({
              id: new FormControl(ans["id"]),
              answer: new FormControl(ans["answer"], Validators.required),
              correct: new FormControl(ans["correct"])
            }));
            this.questions.push(new FormGroup({
              id: new FormControl(q["id"]),
              question: new FormControl(q["question"], Validators.required),
              answers: new FormArray(questionAnswers)
            }));
          });
        });
      }
    });
  }

  ngOnDestroy() {
    this.paramsSub.unsubscribe();
  }

  public onSubmit() {
    if (this.form.valid) {
      this.test = <Test><unknown>this.form.value;
      if (this.testId === undefined) {
        this.testService.create(this.test).subscribe({
          next: () => {
            this.alertService.success("Тест успешно добавлен!");
            this.goToTestList();
          },
          error: () => {
            this.alertService.error("Произошла ошибка при добавлении теста!");
          },
        });
      } else {
        this.testService.update(this.testId, this.test).subscribe({
          next: () => {
            this.alertService.success("Тест успешно изменён!");
            this.goToTestList();
          },
          error: () => {
            this.alertService.error("Произошла ошибка при изменении теста!");
          },
        });
      }
    }
  }

  public goToTestList() {
    this.router.navigate(['/tests']);
  }

  updateTopics() {
    this.articleTopicService.findAll().subscribe(data => this.articleTopics = data);
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
      answers: new FormArray([], Validators.required)
    }));
  }

  deleteQuestionById(questionId: number) {
    this.questions.removeAt(questionId);
  }

  addAnswerToId(questionId: number) {
    this.getAnswersByQuestionId(questionId).push(new FormGroup({
      answer: new FormControl("", Validators.required),
      correct: new FormControl(false)
    }));
  }

  deleteAnswerById(questionId: number, answerId: number) {
    this.getAnswersByQuestionId(questionId).removeAt(answerId);
  }

  openTopicPopup() {
    this.modalService.open("topic-popup");
  }
  
  updateTopicsAfterTopicDeletion(deletedTopicId: number) {
    this.articleTopics = this.articleTopics.filter(topic => topic.id !== deletedTopicId);
    if (this.test.topic && this.articleTopics.find(topic => topic.id === this.test.topic.id) === undefined) {
      this.goToTestList();
    }
  }
}
