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

  public findById(id: number): Observable<Article> {
    return this.httpClient.get<Article>(this.apiUrl + "/" + id);
  }

  public findAll(params: any): Observable<any> {
    return this.httpClient.get(this.apiUrl, {params});
  }

  public count(): Observable<number> {
    return this.httpClient.get<number>(this.apiUrl + "/count");
  }

  public create(article: Partial<Article>) {
    return this.httpClient.post<Article>(this.apiUrl, article);
  }

  public update(id: number, article: Partial<Article>) {
    return this.httpClient.put<Article>(this.apiUrl + "/" + id, article);
  }

  public delete(id: number) {
    return this.httpClient.delete<Article>(this.apiUrl + '/' + id);
  }
}
