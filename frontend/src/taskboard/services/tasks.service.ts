import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {firstValueFrom} from 'rxjs';
import {TaskDto} from '../client/model/taskDto';

@Injectable({
  providedIn: 'root'
})
/**
 * Service for backend calls related to tasks.
 */
export class TasksService {
  baseUrl = '/api/tasks';

  constructor(private httpClient: HttpClient) {
  }

  /**
   * Get all tasks.
   */
  public getTasks(): Promise<Array<TaskDto>> {
    const url = this.baseUrl;
    return firstValueFrom(this.httpClient.get<Array<TaskDto>>(url));
  }
}
