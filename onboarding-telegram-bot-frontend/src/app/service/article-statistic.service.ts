import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArticleStatisticService {

  private apiUrl: string = "http://localhost:8080/api/articles/statistics"

  constructor(private httpClient: HttpClient) { }

  public findAll(params: any): Observable<any> {
    return this.httpClient.get(this.apiUrl, {params});
  }
  
}
