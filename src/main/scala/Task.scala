import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

case class Task(title: String, deadline: LocalDateTime, isDone: Boolean = false)

object TaskUtils {
  def markAsDone(task: Task) : Task = task.copy(isDone = true)
  def stringify(task: Task) : String = {
    def formatDate(date: LocalDateTime) = DateTimeFormatter.ofPattern("dd.MM.YYYY hh:mm").format(date)
    s"${if (task.isDone) "DONE:" else "OPEN:"} ${task.title} (${formatDate(task.deadline)})"
  }
}
