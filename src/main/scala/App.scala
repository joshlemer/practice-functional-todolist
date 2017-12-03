import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.util.Properties
import scala.io.StdIn.readLine

object App {
  val applicationName = "ToDoListManager"
  val version = 1.0
  val dateTimeParser : DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  val splitCliInputBySpaceOrQuotationMarkRegex = "\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?"

  def main(args: Array[String]) {
    var toDoLists = Array[ToDoList]()
    println(s"$applicationName v$version")

    while(true){
      val input = readLine("ToDoList> ")
      val cliInput = input.split(splitCliInputBySpaceOrQuotationMarkRegex)

      val command = GetCommandByCommandLineInput.execute(cliInput)

      command match {
        case createToDoList: CreateToDoList => toDoLists = toDoLists :+ ToDoList(createToDoList.title)
        case addTask : AddTask =>
          var index = findIndexByTitle(toDoLists, addTask.listName)
          if(index == -1)
            println("No such list found")
          else
            toDoLists(index) = toDoLists(index).add(Task(addTask.title, addTask.deadline))

        case removeTask : RemoveTask =>
          var index = findIndexByTitle(toDoLists, removeTask.listName)
          if(index == -1)
            println("No such list found")
          else
            toDoLists(index) = toDoLists(index).remove(removeTask.title)

        case taskDone : TaskDone =>
          var index = findIndexByTitle(toDoLists, taskDone.listName)
          if(index == -1)
            println("No such list found")
          else
            toDoLists(index) = toDoLists(index).done(taskDone.title)

        case _: PrintToDoList => toDoLists.foreach(list => println(s"$list ${Properties.lineSeparator}"))
        case _: ShowHelp => showHelp()
      }
    }
  }

  def findIndexByTitle(lists: Array[ToDoList], name: String) : Int = lists.indexWhere(t => t.title == name)

  abstract class Command()
  case class CreateToDoList(title: String) extends Command
  case class PrintToDoList() extends Command
  case class AddTask(title: String, deadline: LocalDateTime, listName: String) extends Command
  case class RemoveTask(title: String, listName: String) extends Command
  case class TaskDone(title: String, listName: String) extends Command
  case class ShowHelp() extends Command

  def showHelp() {
    // TODO: rm placeholder
    println("THIS IS THE HELP PLACEHOLDER")
  }


  object GetCommandByCommandLineInput{
    def execute(cliInput: Array[String]) : Command = {
      cliInput(0) match {
        case "create" if cliInput.length == 2 => CreateToDoList(cliInput(1))
        case "print" => PrintToDoList()
        case "task" => cliInput(1) match {
          case "add" if cliInput.length == 5 => AddTask(cliInput(2), LocalDateTime.parse(cliInput(3), dateTimeParser), cliInput(4))
          case "remove" if cliInput.length == 4 => RemoveTask(cliInput(2), cliInput(3))
          case "done" if cliInput.length == 4 => TaskDone(cliInput(2), cliInput(3))
        }
        case _ => ShowHelp()
      }
    }
  }
}


