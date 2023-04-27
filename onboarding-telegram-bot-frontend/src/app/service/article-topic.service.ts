import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ArticleTopic } from '../model/article-topic';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ArticleTopicService {

  private apiUrl: string = "http://localhost:8080/api/article-topics";

  constructor(private httpClient: HttpClient) {}

  public getTopicsNames() {
    return this.findAll().pipe(map(topics => {
      let names: Map<number, string> = new Map();
      topics.forEach(topic => {
        names.set(topic.id, topic.name)
      });
      return names;
    }))
  }

  private findAll(): Observable<ArticleTopic[]> {
    return this.httpClient.get<ArticleTopic[]>(this.apiUrl);
  }

}
