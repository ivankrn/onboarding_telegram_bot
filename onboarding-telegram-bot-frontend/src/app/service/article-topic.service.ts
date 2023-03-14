import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ArticleTopic } from '../model/article-topic';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ArticleTopicService {

  private apiUrl: string = "http://localhost:8080/api/article_topics";
  private topicsNames: Map<number, string> = new Map();
  private topicsNames$: Subject<Map<number, string>> = new Subject();

  constructor(private httpClient: HttpClient) {
    this.loadTopics().subscribe(data => {
      this.topicsNames$.next(data);
    })
  }

  public getTopicsNames() {
    return this.topicsNames$;
  }

  private findAll(): Observable<ArticleTopic[]> {
    return this.httpClient.get<ArticleTopic[]>(this.apiUrl);
  }

  private loadTopics() {
    return this.findAll().pipe(map(articleTopics => {
      articleTopics.forEach(topic => {
        this.topicsNames.set(topic.id, topic.name);
      });
      return this.topicsNames;
    }))
  }

}
