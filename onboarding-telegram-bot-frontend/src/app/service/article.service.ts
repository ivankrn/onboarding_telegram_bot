import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Article } from '../model/article';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private apiUrl: string = "http://localhost:8080/api/articles"

  constructor(private httpClient: HttpClient) { }

  public findAll(): Observable<Article[]> {
    return this.httpClient.get<Article[]>(this.apiUrl);
  }

  public count(): Observable<number> {
    return this.httpClient.get<number>(this.apiUrl + "/count");
  }

  public save(article: Article) {
    return this.httpClient.post<Article>(this.apiUrl, article);
  }

  public delete(id: number) {
    return this.httpClient.delete<Article>(this.apiUrl + '/' + id);
  }
}
