import { bootstrapApplication } from '@angular/platform-browser';
import { taskboardConfig } from './taskboard/taskboard.config';
import { TaskboardComponent } from './taskboard/taskboard.component';

bootstrapApplication(TaskboardComponent, taskboardConfig)
  .catch((err) => console.error(err));
