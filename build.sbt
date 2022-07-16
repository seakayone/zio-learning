ThisBuild / scalaVersion := "3.1.3"
name := "zio-learning"

val ZioVersion = "2.0.0"
val ZioJsonVersion = "0.3.0-RC10"
val ZioHttpVersion = "2.0.0-RC10"
val ZioConfigVersion = "3.0.1"
val ZioLoggingVersion = "2.0.0"
val ZioZmxVersion = "2.0.0-RC4"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % ZioVersion,
  "dev.zio" %% "zio-streams" % ZioVersion,
  "dev.zio" %% "zio-logging" % ZioVersion,
  "dev.zio" %% "zio-logging-slf4j" % ZioVersion,
  "dev.zio" %% "zio" % ZioVersion,
  "dev.zio" %% "zio-json" % ZioJsonVersion,
  "io.d11" %% "zhttp" % ZioHttpVersion,
  "dev.zio" %% "zio-logging" % ZioLoggingVersion,
  "dev.zio" %% "zio-logging-slf4j" % ZioLoggingVersion,
  "dev.zio" %% "zio-config" % ZioConfigVersion,
  "dev.zio" %% "zio-config-typesafe" % ZioConfigVersion,
  "dev.zio" %% "zio-test" % ZioVersion % Test,
  "dev.zio" %% "zio-test-sbt" % ZioVersion % Test,
  "ch.qos.logback" % "logback-classic" % "1.2.11"
)
testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
