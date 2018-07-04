package generic_ingestion

import com.github.acrisci.commander.Program

case class ToolOptions(
                           source: String,
                           params_source: String,
                           sink: String,
                           params_sink: String
) extends Serializable

trait OptionParser {
  def _program = new Program()
    .version("2.0.0")

  def apply(args: Array[String]): ToolOptions = {
    val toolOptions = ToolOptions(
      source = "KAFKA",
    params_source = "BROKER TOPIC",
    sink = "HBASE",
    params_sink = "TABLE_NAME COLUMN_FAMILY COLUMN1 COLUMN2"
    )
    toolOptions
  }
}
