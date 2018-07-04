package generic_ingestion

package object tools {

  object list_tools extends Enumeration {
    val HDFS = Value("HDFS")
    val ES = Value("ES")
    val KAFKA = Value("KAFKA")
    val HBASE = Value("HBASE")
  }

  case class KAFKA_PARAMS(broker: String, topic: String)
  case class HDFS_PARAMS(path: String)
  case class HBASE_PARAMS(table_name: String, column_family: String, columns: List[String])
  case class ES_PARAMS(es_type: String, es_index: String)

}
