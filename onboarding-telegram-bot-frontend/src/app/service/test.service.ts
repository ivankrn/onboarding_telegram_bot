import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Test } from '../model/test';

@Injectable({
  providedIn: 'root'
})
export class TestService {

  private apiUrl: string = "http://localhost:8080/api/tests"

  constructor(private httpClient: HttpClient) { }

  public findById(id: number): Observable<Test> {
    return this.httpClient.get<Test>(this.apiUrl + "/" + id);
  }

  public findAll(params: any): Observable<any> {
    return this.httpClient.get(this.apiUrl, {params});
  }

  public count(): Observable<number> {
    return this.httpClient.get<number>(this.apiUrl + "/count");
  }

  public create(test: Partial<Test>) {
    return this.httpClient.post<Test>(this.apiUrl, test);
  }

  public update(id: number, test: Partial<Test>) {
    return this.httpClient.put<Test>(this.apiUrl + "/" + id, test);
  }

  public delete(id: number) {
    return this.httpClient.delete<Test>(this.apiUrl + '/' + id);
  }

  public getTestsNames() {
    return this.findAll({}).pipe(map(testPage => {
      let names: Map<number, string> = new Map();
      const tests = testPage["content"];
      tests.forEach((test: Test) => {
        names.set(test.id, test.title)
      });
      return names;
    }));
  }
}
