import scala.util.Properties

case class ToDoList(title: String, tasks: Vector[Task] = Vector[Task]())

object ToDoListUtils {
  def stringify(toDoList: ToDoList) : String = {
    val header = s"List '${toDoList.title}':"
    if(toDoList.tasks.isEmpty)
      s"$header ${Properties.lineSeparator} <No Tasks added yet>"
    else {
      val tasksStringified = toDoList.tasks
        .map(task => s"${Properties.lineSeparator} ${TaskUtils.stringify(task)}")
        .reduce((a, b) => a + b)
      s"$header $tasksStringified"
    }
  }

  def removeTask(taskToRm: Task, toDoList: ToDoList) : ToDoList =
    toDoList.copy(tasks = toDoList.tasks.filter(task => task != taskToRm))

  def addTask(taskToAdd: Task, toDoList: ToDoList) : ToDoList =
    toDoList.copy(tasks = toDoList.tasks :+ taskToAdd)

  def findTaskByTitle(taskTitle: String, toDoList: ToDoList) : Task =
    toDoList.tasks.find(task => task.title == taskTitle).orNull
}
