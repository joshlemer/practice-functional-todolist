import scala.io.StdIn.readLine

object App {
  val applicationName = "ToDoListManager"
  val version = 1.0

  def main(args: Array[String]) {
    println(s"$applicationName v$version")

    var toDoListGroup = ToDoListGroup()

    while(true){
      val input = readLine("ToDoList> ")
      val command = ConsoleUtils.parseInputToCommand(input)

      toDoListGroup = command match {
        case createToDoList: CreateToDoList =>
           ToDoListGroupUtils.addList(ToDoList(createToDoList.title), toDoListGroup)

        case addTask : AddTask =>
          val list = ToDoListGroupUtils.findToDoListByTitle(addTask.listTitle, toDoListGroup)
          if(list == null) {
            println("No such list found")
            toDoListGroup
          }
          else {
            toDoListGroup = ToDoListGroupUtils.removeList(list, toDoListGroup)
            val newList = ToDoListUtils.addTask(Task(addTask.title, addTask.deadline), list)
            ToDoListGroupUtils.addList(newList, toDoListGroup)
          }

        case removeTask : RemoveTask =>
          val list = ToDoListGroupUtils.findToDoListByTitle(removeTask.listTitle, toDoListGroup)
          if(list == null) {
            println("No such list found")
            toDoListGroup
          }
          else {
            val taskToBeRemoved = ToDoListUtils.findTaskByTitle(removeTask.title, list)
            if(taskToBeRemoved == null){
              println("No such task found")
              toDoListGroup
            } else {
              toDoListGroup = ToDoListGroupUtils.removeList(list, toDoListGroup)
              val newList = ToDoListUtils.removeTask(taskToBeRemoved, list)
              ToDoListGroupUtils.addList(newList, toDoListGroup)
            }
          }

        case taskDone : TaskDone =>
          val list = ToDoListGroupUtils.findToDoListByTitle(taskDone.listTitle, toDoListGroup)
          if(list == null) {
            println("No such list found")
            toDoListGroup
          }
          else {
            val taskToBeMarkedAsDone = ToDoListUtils.findTaskByTitle(taskDone.title, list)
            if(taskToBeMarkedAsDone == null){
              println("No such task found")
              toDoListGroup
            } else {
              val taskMarkedAsDone = TaskUtils.markAsDone(taskToBeMarkedAsDone)
              val newList = ToDoListUtils.addTask(taskMarkedAsDone, ToDoListUtils.removeTask(taskToBeMarkedAsDone, list))
              toDoListGroup = ToDoListGroupUtils.removeList(list, toDoListGroup)
              ToDoListGroupUtils.addList(newList, toDoListGroup)
            }
          }

        case _: PrintToDoList =>
          println(ToDoListGroupUtils.stringify(toDoListGroup))
          toDoListGroup

        case _: ShowHelp =>
          // TODO: rm placeholder
          println("THIS IS THE HELP PLACEHOLDER")
          toDoListGroup
      }
    }
  }
}