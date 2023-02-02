ThisBuild / scalaVersion := "3.2.0"
name                     := "zio-learning"

val ZioConfigVersion            = "3.0.7"
val ZioHttpVersion              = "0.0.4"
val ZioJsonVersion              = "0.4.2"
val ZioLoggingVersion           = "2.1.8"
val ZioMetricsConnectorsVersion = "2.0.5"
val ZioVersion                  = "2.0.6"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio"                    % ZioVersion,
  "dev.zio" %% "zio-http"               % ZioHttpVersion,
  "dev.zio" %% "zio-json"               % ZioJsonVersion,
  "dev.zio" %% "zio-logging"            % ZioLoggingVersion,
  "dev.zio" %% "zio-logging-slf4j"      % ZioLoggingVersion,
  "dev.zio" %% "zio-metrics-connectors" % ZioMetricsConnectorsVersion,

//  "dev.zio" %% "zio-streams" % ZioVersion,
  "dev.zio"       %% "zio-config"          % ZioConfigVersion,
  "dev.zio"       %% "zio-config-typesafe" % ZioConfigVersion,
  "ch.qos.logback" % "logback-classic"     % "1.4.5",
  "dev.zio"       %% "zio-test"            % ZioVersion % Test,
  "dev.zio"       %% "zio-test-sbt"        % ZioVersion % Test
)
testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
