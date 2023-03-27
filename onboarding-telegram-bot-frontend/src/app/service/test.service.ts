import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Test } from '../model/test';

@Injectable({
  providedIn: 'root'
})
export class TestService {

  private apiUrl: string = "http://localhost:8080/api/tests"

  constructor(private httpClient: HttpClient) { }

  public findAll(): Observable<Test[]> {
    return this.httpClient.get<Test[]>(this.apiUrl);
  }

  public count(): Observable<number> {
    return this.httpClient.get<number>(this.apiUrl + "/count");
  }

  public save(test: Test) {
    return this.httpClient.post<Test>(this.apiUrl, test);
  }

  public delete(id: number) {
    return this.httpClient.delete<Test>(this.apiUrl + '/' + id);
  }
}
