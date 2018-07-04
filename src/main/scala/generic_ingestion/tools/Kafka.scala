package generic_ingestion.tools

import java.util.Properties

import kafka.serializer.StringDecoder
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.kafka.KafkaUtils

object Kafka {
  // RDD or DATASET
  // schema creation


  def source(kafka_params: KAFKA_PARAMS) = {
    import generic_ingestion.Utils.SparkSetup._

    val kafkaParams = Map("metadata.broker.list" -> kafka_params.broker)
    val topics = List(kafka_params.topic).toSet
    val lines = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topics).map(_._2)
    lines
  }


  def sink(rDD: RDD[String],kafka_params: KAFKA_PARAMS)  {
      import org.apache.kafka.clients.producer._
      val producer = new KafkaProducer[String, String](configuration(kafka_params.broker))
      for (i <- 1 to 50) {
        val record = new ProducerRecord(kafka_params.topic, "key" + i, "value" + i)
        producer.send(record)
      }
      producer.close()
  }




  def configuration(broker:String) = {
    val props = new Properties()
    props.put("bootstrap.servers", broker)
    props.put("acks", "1")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props
  }
}