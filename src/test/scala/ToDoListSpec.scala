import java.time.LocalDateTime

import org.scalatest._

class ToDoListSpec extends FlatSpec with Matchers {

  private[this] val baseTaskList = Vector[Task](
    Task("Task 1", LocalDateTime.now()),
    Task("Task 2", LocalDateTime.now()))

  private[this] val baseToDoList = ToDoList("Test-List", baseTaskList)

  "A ToDoList" should "append the task properly" in {
    val taskToAdd = Task("Task 3", LocalDateTime.now())
    val toDoList = ToDoListUtils.addTask(taskToAdd, baseToDoList)
    toDoList.tasks.last should be (taskToAdd)
  }

  it should "remove the task properly" in {
    val toDoList = ToDoListUtils.removeTask(baseTaskList.last, baseToDoList)
    toDoList.tasks should not contain baseTaskList.last
  }

  it should "tag a task as done properly" in {
    val task = TaskUtils.markAsDone(baseTaskList.last)
    task.isDone should be (true)
  }
}