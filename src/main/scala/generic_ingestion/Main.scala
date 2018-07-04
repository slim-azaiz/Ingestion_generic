package generic_ingestion

import generic_ingestion.tools._
import generic_ingestion.tools.list_tools._
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.DStream

object Main extends App {
  val options = CollectArgs.parse(args)
  Collector.doIt(options.toolOptions)
}



object Collector {
  def doIt(options: ToolOptions): Unit = {
    options.params_source match {
      case KAFKA => {

        val params_source:List[String] = options.params_source.split(" ").toList
        val params_sink:List[String] = options.params_sink.split(" ").toList
        val data = read_to_Dstream(params_source)
        write_dstream(data, options.sink, params_sink)
      }
      case _ => {
        val params_source:List[String] = options.params_source.split(" ").toList
        val params_sink:List[String] = options.params_sink.split(" ").toList
        val data = read_to_rdd(options.source, params_source)
        write_rdd(data, options.sink, params_sink)
      }
    }

    def read_to_rdd(source: String, params_source: List[String]): RDD[_] = {
      source match {
        case HDFS => {
          val hdfs_params = HDFS_PARAMS(params_source(0))
          return Hdfs.source(hdfs_params)
        }
        case ES => {
          val es_params = ES_PARAMS(params_source(0), params_source(1))
          return Es.source(es_params)
        }
        //case HBASE => return Hbase.source()
      }
    }

    def read_to_Dstream(params_source: List[String]): DStream[_] = {
      val kafka_params = KAFKA_PARAMS(params_source(0), params_source(1))
      return Kafka.source(kafka_params)
    }
  }

  def write_rdd(data: RDD[_], sink: String, params_sink: List[String]): Unit = {
    sink match {
      case HDFS => {
        val hdfs_params = HDFS_PARAMS(params_sink(0))
        Hdfs.sink(data, hdfs_params)
      }
      case KAFKA => {
        val kafka_params = KAFKA_PARAMS(params_sink(0), params_sink(1))
        Kafka.sink(_, kafka_params)
      }
      case ES => {
        val es_params = ES_PARAMS(params_sink(0), params_sink(1))
        //Es.sink(_,params_sink(0))
      }
      case HBASE => {
        // val hbase_params = HBASE_PARAMS(params_sink(0), params_sink(1),params_sink(2))
        // Hbase.sink(_, hbase_params)
      }
    }
  }

  def write_dstream(data: DStream[_], sink: String, params_sink: List[String]): Unit = {
    sink match {
      case HDFS => {
        val hdfs_params = HDFS_PARAMS(params_sink(0))
        // Hdfs.sink(data,hdfs_params)
      }
      case KAFKA => {
        val kafka_params = KAFKA_PARAMS(params_sink(0), params_sink(1))
        Kafka.sink(_, kafka_params)
      }
      case ES => {
        val es_params = ES_PARAMS(params_sink(0), params_sink(1))
        //Es.sink(_,params_sink(0))
      }
      case HBASE => {
        val hbase_params = HBASE_PARAMS(params_sink(0), params_sink(1), params_sink)
        Hbase.sink_Dstream(_, hbase_params)
      }
    }
  }
}
