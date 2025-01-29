import {Component} from '@angular/core';
import {TaskTableComponent} from "./components/task-table/task-table.component";

@Component({
  selector: 'taskboard-root',
  imports: [
    TaskTableComponent
  ],
  templateUrl: './taskboard.component.html',
  styleUrl: './taskboard.component.css'
})
export class TaskboardComponent {
  title = 'TaskBoard Frontend';
}
