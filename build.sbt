ThisBuild / organization := "com.github"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.1"
ThisBuild / scalacOptions := Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding",
  "utf-8",                         // Specify character encoding used by source files.
  "-explaintypes",                 // Explain type errors in more detail.
  "-feature",                      // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials",        // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros", // Allow macro definition (besides implementation and application)
  "-language:higherKinds",         // Allow higher-kinded types
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-unchecked",                    // Enable additional warnings where generated code depends on assumptions.

  // Inlining options. More at https://www.lightbend.com/blog/scala-inliner-optimizer, https://github.com/scala/scala/pull/4858, https://github.com/scala/bug/issues/8790
  //"-opt:l:method",            // Enable intra-method optimizations: unreachable-code,simplify-jumps,compact-locals,copy-propagation,redundant-casts,box-unbox,nullness-tracking,closure-invocations,allow-skip-core-module-init,assume-modules-non-null,allow-skip-class-loading.
  //"-opt:l:inline",            // Enable cross-method optimizations (note: inlining requires -opt-inline-from): l:method,inline.
  //"-opt-inline-from:tofu.**", // Patterns for classfile names from which to allow inlining
  //"-opt-warnings:none",       // No optimizer warnings.

  //  "-Xcheckinit",                   // Wrap field accessors to throw an exception on uninitialized access. (SHOULD BE USED ONLY IN DEV)
  "-Xlint:adapted-args",           // Warn if an argument list is modified to match the receiver.
  "-Xlint:delayedinit-select",     // Selecting member of DelayedInit.
  "-Xlint:doc-detached",           // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible",           // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any",              // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator",   // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-override",       // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Xlint:nullary-unit",           // Warn when nullary methods return Unit.
  "-Xlint:option-implicit",        // Option.apply used implicit view.
  "-Xlint:package-object-classes", // Class or object defined in package object.
  "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow",         // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align",            // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow",  // A local type parameter shadows a type already in scope.
  "-Xlint:constant",               // Evaluation of a constant arithmetic expression results in an error.
  "-Ywarn-unused:imports",         // Warn if an import selector is not referenced.
  "-Ywarn-unused:locals",          // Warn if a local definition is unused.
  "-Ywarn-unused:params",          // Warn if a value parameter is unused.
  "-Ywarn-unused:patvars",         // Warn if a variable bound in a pattern is unused.
  "-Ywarn-unused:privates",        // Warn if a private member is unused.
  "-Ywarn-unused:implicits",       // Warn if an implicit parameter is unused.
  "-Ywarn-extra-implicit",         // Warn when more than one implicit parameter section is defined.
  // 2.13 only
  "-Ymacro-annotations"
)

val grpcSettings = Seq(
  scalapbCodeGeneratorOptions ++= Seq(
    CodeGeneratorOption.FlatPackage
  )
)

lazy val helloworld = project.in(file("./services/helloworld"))

lazy val `webgateway-app` = project.in(file("./services/webgateway/app"))
  .settings(
    libraryDependencies ++= Seq(
      Deps.caliban,
      Deps.calibanAkkaHttp,
      Deps.calibanCats,
      Deps.akkaHttp,
      Deps.akkaStream,
      Deps.cats,
      Deps.tethys
    )
  )

lazy val `wallet-grpc` = project.in(file("./services/wallet/grpc"))
  .enablePlugins(Fs2Grpc)
  .settings(grpcSettings)

lazy val wallet = project.in(file("./services/wallet"))
  .aggregate(`wallet-grpc`)

lazy val zasport = project.in(file("."))
  .aggregate(
    helloworld,
    wallet
  )
