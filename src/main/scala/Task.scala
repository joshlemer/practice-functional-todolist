import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

case class Task(title: String, deadline: LocalDateTime, isDone: Boolean = false) {
  override def toString : String ={
    def formatDate(date: LocalDateTime) = DateTimeFormatter.ofPattern("dd.MM.YYYY hh:mm").format(date)
    s"${if(isDone) "DONE:" else "OPEN:"} $title (${formatDate(deadline)})"

  }
}
