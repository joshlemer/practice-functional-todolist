import java.time.LocalDateTime

import org.scalatest._

class ToDoListSpec extends FlatSpec with Matchers {

  private[this] val baseTaskList = Array[Task](
    Task("Task 1", LocalDateTime.now()),
    Task("Task 2", LocalDateTime.now()))

  private[this] val baseToDoList = ToDoList("Test-List", baseTaskList)

  "A ToDoList" should "append the task properly" in {
    val taskToAdd = Task("Task 3", LocalDateTime.now())
    val toDoList = baseToDoList add taskToAdd
    toDoList.tasks.last should be (taskToAdd)
  }

  it should "remove the task properly" in {
    val toDoList = baseToDoList remove baseTaskList.last.title
    toDoList.tasks should not contain baseTaskList.last
  }

  it should "tag a task as done properly" in {
    val toDoList = baseToDoList done baseTaskList.last.title
    toDoList.tasks.last.isDone should be (true)
  }
}