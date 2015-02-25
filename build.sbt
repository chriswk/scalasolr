name := """scalasolr"""

version := "1.0"

scalaVersion := "2.11.5"

resolvers += "amateras-repo" at "http://amateras.sourceforge.jp/mvn/"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.apache.solr" %% "solr-solrj" % "5.0",
  "com.typesafe.akka" %% "akka-actor" % "2.3.9"
)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.3.9"
