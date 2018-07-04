name := "First_test"

version := "0.1"

scalaVersion := "2.11.8"


resolvers ++= Seq(
  "confluent" at "http://packages.confluent.io/maven/",
  Resolver.mavenLocal //so we can use local build
)

// grading libraries
libraryDependencies += "junit" % "junit" % "4.10" % Test
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.2.0",
  "org.apache.spark" %% "spark-mllib" % "2.2.0",
  "org.apache.spark" %% "spark-streaming" % "2.2.0",
  "org.apache.spark" %% "spark-sql" % "2.2.0" ,
  "org.apache.spark" % "spark-streaming-kafka-0-8_2.11" % "2.2.0" ,
  "org.apache.spark" % "spark-streaming-flume_2.11" % "2.2.0",

  "com.storm-enroute" %% "scalameter" % "0.7",
  "com.storm-enroute" %% "scalameter-core" % "0.7",
  "com.github.dwickern" %% "scala-nameof" % "1.0.3" % "provided",
  "com.github.scala-blitz" %% "scala-blitz" % "1.1",
  "org.scala-lang.modules" %% "scala-swing" % "1.0.1",

  //confluent
  "io.confluent" % "kafka-avro-serializer" % "3.3.1",
  "org.apache.bahir" %% "spark-streaming-twitter" % "2.0.0",
  "org.facebook4j" % "facebook4j-core" % "2.4.9",
  "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.3",
  "it.nerdammer.bigdata" % "spark-hbase-connector_2.10" % "1.0.3",
  "com.github.acrisci" % "commander_2.11" % "0.1.0",

  //avro
  "com.databricks" %% "spark-avro" % "4.0.0",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.5.2" artifacts (Artifact("stanford-corenlp", "models"), Artifact("stanford-corenlp")),
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.7",
  "org.elasticsearch" %% "elasticsearch-spark-20" % "6.0.0",
  "com.typesafe" % "config" % "1.2.1",
  "org.apache.avro" % "avro" % "1.7.7"
)
