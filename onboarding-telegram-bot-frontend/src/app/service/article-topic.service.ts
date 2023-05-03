import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ArticleTopic } from '../model/article-topic';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArticleTopicService {

  private apiUrl: string = "http://localhost:8080/api/article-topics";

  constructor(private httpClient: HttpClient) {}

  public findAll(): Observable<ArticleTopic[]> {
    return this.httpClient.get<ArticleTopic[]>(this.apiUrl);
  }

  public save(topic: ArticleTopic) {
    return this.httpClient.post<ArticleTopic>(this.apiUrl, topic);
  }

  public delete(id: number) {
    return this.httpClient.delete<ArticleTopic>(this.apiUrl + '/' + id);
  }
}
