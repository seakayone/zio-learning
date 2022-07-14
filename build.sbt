ThisBuild / scalaVersion := "2.13.8"
name                     := "zio-learning"

val ZioVersion        = "2.0.0"
val ZioHttpVersion    = "2.0.0-RC4"
val ZioJsonVersion    = "0.3.0-RC3"
val ZioConfigVersion  = "3.0.1"
val ZioSchemaVersion  = "0.2.0"
val ZioLoggingVersion = "2.0.0"
val ZioZmxVersion     = "2.0.0-RC4"
val ZioPreludeVersion = "1.0.0-RC13"

libraryDependencies ++= Seq(
  "dev.zio"       %% "zio"                 % ZioVersion,
  "dev.zio"       %% "zio-streams"         % ZioVersion,
  "dev.zio"       %% "zio-logging"         % ZioVersion,
  "dev.zio"       %% "zio-logging-slf4j"   % ZioVersion,
  "dev.zio"       %% "zio"                 % ZioVersion,
  "dev.zio"       %% "zio-macros"          % ZioVersion,
  "io.d11"        %% "zhttp"               % ZioHttpVersion,
  "dev.zio"       %% "zio-json"            % ZioJsonVersion,
  "dev.zio"       %% "zio-prelude"         % ZioPreludeVersion,
  "dev.zio"       %% "zio-logging"         % ZioLoggingVersion,
  "dev.zio"       %% "zio-logging-slf4j"   % ZioLoggingVersion,
  "dev.zio"       %% "zio-config"          % ZioConfigVersion,
  "dev.zio"       %% "zio-config-magnolia" % ZioConfigVersion,
  "dev.zio"       %% "zio-config-typesafe" % ZioConfigVersion,
  "dev.zio"       %% "zio-test"            % ZioVersion % Test,
  "dev.zio"       %% "zio-test-sbt"        % ZioVersion % Test,
  "ch.qos.logback" % "logback-classic"     % "1.2.11"
)
testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
