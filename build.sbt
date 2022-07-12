ThisBuild / scalaVersion := "2.13.8"

name := "zio-learning"

libraryDependencies ++= Seq(
  "dev.zio"       %% "zio"            % "2.0.0",
  "dev.zio"       %% "zio-json"       % "0.3.0-RC7",
  "io.d11"        %% "zhttp"          % "2.0.0-RC7",
  "io.getquill"   %% "quill-zio"      % "3.18.0",
  "io.getquill"   %% "quill-jdbc-zio" % "3.18.0",
  "com.h2database" % "h2"             % "2.1.212"
)
