import org.scalatest._

import subscript.koans._

import subscript.koans.util.KoanSuiteGlobal._

class PathToEnlightenment extends Suite with SequentialNestedSuiteExecution {override def nestedSuites = scala.collection.immutable.IndexedSeq (
  new AboutSubScript
, new AboutScriptDefinitions

, new AboutSequentialOperators
, new AboutAlternativeComposition
,
  new AboutEpsilonAndDelta

, new AboutAndParallelism
, new AboutOrParallelism
, new AboutDisruption

, new AboutIterations
//, new AboutCodeFragments // tiny, threaded, unsure, event handling
//, new AboutParentheses // how to get rid of those: plus(a,b), call:syntax, semicolon, method calls, let.
, new AboutAdvancedSyntax // <= AboutVarValLet?

//, new AboutHereAndThere
//, new AboutAnnotations

//, new AboutScriptParameters // output, colon notation
//, new AboutTerms // wrap up

, new AboutConditionalOperators
, new AboutResultValues
, new AboutAdvancedResultValues
, new AboutDataflow

//, new AboutACPAxioms // their relation with SubScript
).filter {s =>
  envSeq("about").isEmpty || envSeq("about").exists(s.getClass.getCanonicalName.endsWith)
}}
