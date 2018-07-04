package generic_ingestion.tools

import org.apache.spark.rdd.RDD

object Hdfs {

  def source(hdfs_params: HDFS_PARAMS): RDD[String] ={
    import generic_ingestion.Utils.SparkSetup._
    val input = sc.textFile(hdfs_params.path)
    input
  }

  def sink(rDD: RDD[_], hdfs_params: HDFS_PARAMS): Unit ={
    // we can add option type
    rDD.saveAsTextFile(hdfs_params.path)
  }
}
