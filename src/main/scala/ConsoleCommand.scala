import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class ConsoleCommand()
case class CreateToDoList(title: String) extends ConsoleCommand
case class PrintToDoList() extends ConsoleCommand
case class AddTask(title: String, deadline: LocalDateTime, listTitle: String) extends ConsoleCommand
case class RemoveTask(title: String, listTitle: String) extends ConsoleCommand
case class TaskDone(title: String, listTitle: String) extends ConsoleCommand
case class ShowHelp() extends ConsoleCommand

object ConsoleUtils {
  val dateTimeParser : DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  val splitCliInputBySpaceOrQuotationMarkRegex = "\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?"

  def parseInputToCommand(input: String) : ConsoleCommand = {
    val cliInputParts = input.split(splitCliInputBySpaceOrQuotationMarkRegex)

    cliInputParts(0) match {
      case "create" if cliInputParts.length == 2 => CreateToDoList(cliInputParts(1))
      case "print" => PrintToDoList()
      case "task" => cliInputParts(1) match {
        case "add" if cliInputParts.length == 5 => AddTask(cliInputParts(2), LocalDateTime.parse(cliInputParts(3), dateTimeParser), cliInputParts(4))
        case "remove" if cliInputParts.length == 4 => RemoveTask(cliInputParts(2), cliInputParts(3))
        case "done" if cliInputParts.length == 4 => TaskDone(cliInputParts(2), cliInputParts(3))
      }
      case _ => ShowHelp()
    }
  }
}
