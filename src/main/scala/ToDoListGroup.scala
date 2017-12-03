import scala.util.Properties

case class ToDoListGroup(toDoLists: Vector[ToDoList] = Vector[ToDoList]())

object ToDoListGroupUtils {
  def stringify(toDoListGroup: ToDoListGroup) : String =
    toDoListGroup.toDoLists
      .map(list => s"${ToDoListUtils.stringify(list)}")
      .reduce((a,b) => s"$a  ${Properties.lineSeparator} $b")

  def addList(listToAdd: ToDoList, toDoListGroup: ToDoListGroup) : ToDoListGroup =
    toDoListGroup.copy(toDoLists = toDoListGroup.toDoLists :+ listToAdd)

  def removeList(listToRemove: ToDoList, toDoListGroup: ToDoListGroup) : ToDoListGroup =
    toDoListGroup.copy(toDoLists = toDoListGroup.toDoLists.filter(list => list != listToRemove))

  def findToDoListByTitle(listTitle: String, toDoListGroup: ToDoListGroup) : ToDoList =
    toDoListGroup.toDoLists.find(list => list.title == listTitle).orNull
}