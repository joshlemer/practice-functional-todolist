import scala.util.Properties

case class ToDoList(title: String, tasks: Array[Task] = Array[Task]()) {

  def add(task: Task): ToDoList = copy(tasks = tasks :+ task)

  def remove(taskName: String): ToDoList = copy(tasks = tasks.filter(task => task.title == taskName))

  def done(taskToTagAsDone: String): ToDoList = {
    def tagAsDone(task: Task) = if (task.title == taskToTagAsDone) task.copy(isDone = true) else task
    copy(tasks = tasks.map(tagAsDone))
  }

  override def toString: String = {
    val header = s"List '$title':"
    if(tasks.length == 0)
      s"$header ${Properties.lineSeparator} <No Tasks added yet>"
    else {
      val tasksStringified = tasks
        .map(task => s"${Properties.lineSeparator}$task")
        .reduce((a, b) => a + b)
      s"$header $tasksStringified"
    }
  }
}
