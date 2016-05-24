import subscript.language
import subscript.Predef._

import scala.util.Failure

import org.scalatest._

import subscript.koans._

import subscript.koans.util.KoanSuiteGlobal._

class PathToEnlightenment extends Suite with SequentialNestedSuiteExecution with BeforeAndAfterAll {
  override def nestedSuites = scala.collection.immutable.IndexedSeq (
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
  , new AboutCodeFragments
  //, new AboutParentheses // how to get rid of those: if then, plus(a,b), call:syntax, semicolon, method calls, let.
  , new AboutAdvancedSyntax
  , new AboutVarValLet

  //, new AboutHereAndThereAnnotations

  //, new AboutScriptParameters // output, colon notation
  //, new AboutTerms // wrap up

  , new AboutConditionalOperators
  , new AboutResultValues
  , new AboutAdvancedResultValues
  , new AboutDataflow

  //, new AboutACPAxioms // their relation with SubScript
  ).filter {s =>
    envSeqPredicate("about")(s.getClass.getCanonicalName.endsWith) &&
    (envSeq("skip").isEmpty || envSeq("skip").forall(x => !s.getClass.getCanonicalName.endsWith(x)))
  }

  override def beforeAll() {
    script..
      enforceWorkflowConstraints =
        allow: "koan" , ifSingle: "about"
        allow: "test" , ifSingle: "koan"
        allow: "debug", ifSingle: "test"
        allow: "trace", ifSingle: "test"

      allowIfSingle(allow: String, ifSingle: String) =
        if System.getProperty(allow) != null && envSeq(ifSingle).size != 1
        then {:throw new RuntimeException(s"The option '$allow:' is allowed if exactly one '$ifSingle:' is specified"):}

    runScript(enforceWorkflowConstraints).$ match {
      case Failure(e) => throw e
      case _ =>
    }
  }
}
