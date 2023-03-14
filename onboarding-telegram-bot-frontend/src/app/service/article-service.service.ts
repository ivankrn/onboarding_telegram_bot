import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Article } from '../model/article';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private apiUrl: string = "http://localhost:8080/articles"

  constructor(private httpClient: HttpClient) { }

  public findAll(): Observable<Article[]> {
    return this.httpClient.get<Article[]>(this.apiUrl);
  }

  public save(article: Article) {
    return this.httpClient.post<Article>(this.apiUrl, article);
  }
}
