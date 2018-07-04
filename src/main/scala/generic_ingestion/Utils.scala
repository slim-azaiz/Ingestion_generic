package generic_ingestion

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Utils {
  /** Makes sure only ERROR messages get logged **/
  def setupLogging() = {
    import org.apache.log4j.{Level, Logger}
    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)
  }

  object SparkSetup {
    val spark = SparkSession
      .builder
      .master("local[*]")
      .appName(getClass.getSimpleName.replace("$", ""))
      .getOrCreate()

    val sqlContext = spark.sqlContext

    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")
    sc.getConf
      .set("es.index.auto.create", "true")
      .set("es.nodes","localhost:9200")

    val conf = new SparkConf()
    conf.set("spark.hbase.host", "127.0.0.1")
    conf.setMaster("local[*]")
    conf.setAppName("HbaseExample")
    val ssc = new StreamingContext(conf, Seconds(1))
  }

}
