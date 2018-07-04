package generic_ingestion.tools

import generic_ingestion.Utils.SparkSetup._
import it.nerdammer.spark.hbase._
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.DStream


object Hbase {

  def source(hbase_params: HBASE_PARAMS) :RDD[String] ={
    val hBaseRDD = sc.hbaseTable[(String)](hbase_params.table_name)

    val rDD = hBaseRDD
        .select(hbase_params.columns(0))
        .inColumnFamily(hbase_params.column_family)
    rDD
  }


  def sink_Dstream(dStream: DStream[String], hbase_params: HBASE_PARAMS)  {
      dStream.foreachRDD((rdd, time) => {
        println(s"========= $time =========")
        rdd.cache()
        println("Writing " + rdd.count() + " rows to Hbase")
        try {
          for (item <- hbase_params.columns){
            rdd.toHBaseTable(hbase_params.table_name)
              .inColumnFamily(hbase_params.column_family)
              .toColumns(item)
              .save()
          }
          throw new IllegalArgumentException("Threw an IllegalArgumentException")
        }
        catch {
          case e: IllegalArgumentException => println(s"error", e)
        }
      })
  }

  def sink(rDD: RDD[String], hbase_params: HBASE_PARAMS) = {
      rDD.cache()
      try {
        for (item <- hbase_params.columns) {
          rDD.toHBaseTable(hbase_params.table_name)
            .inColumnFamily(hbase_params.column_family)
            .toColumns(item)
            .save()
        }
        throw new IllegalArgumentException("Threw an IllegalArgumentException")

      }
      catch {
        case e: IllegalArgumentException => println(s"error", e)
      }
  }

}
