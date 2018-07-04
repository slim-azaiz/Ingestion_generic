package generic_ingestion

import com.github.acrisci.commander.Program
abstract sealed case class CollectArgs(
                                         toolOptions: ToolOptions
                                         )

object CollectArgs extends OptionParser {
  override val _program = super._program
    .usage("Collect [options] <source> <param_sources> <sink> <params_sink>")

  def parse(args: Array[String]): CollectArgs = {
    val program: Program = _program.parse(args)
    if (program.args.length!=program.usage.split(" ").length-2) program.help

    new CollectArgs(
      toolOptions = super.apply(args)
    ){}

  }
}
