package generic_ingestion.tools

import org.apache.spark.rdd.RDD
import generic_ingestion.Utils.SparkSetup._
import org.elasticsearch.spark.sql._
import org.elasticsearch.spark._

object Es {

  def source(es_params: ES_PARAMS):RDD[(String, scala.collection.Map[String, AnyRef])]= {
    val rDD = sc.esRDD(es_params.es_index+ "/" + es_params.es_type )
    rDD
  }



  case class Rating(userID: Int, movieID: Int, rating: Float, timestamp: Int)

  def sink(rDD: RDD[String], es_params:ES_PARAMS, mapper : String => Rating) ={
    import spark.implicits._
    // from rdd[String]
    // sink[type]
    val header = rDD.first
    val data = rDD.filter(row => row!= header)
    val results = data.map(mapper)
      .toDF
      .saveToEs(es_params.es_index+"/"+es_params.es_type)
  }

}
