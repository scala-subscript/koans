package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutCodeFragments extends KoanSuite with OperatorKoansHelper {

  koan(1)(
    """
    | As seen, one way to execute a fragment of Scala code is in an atomic action, e.g.,
    |
    | {! i+=1 !}
    |
    | Except in special cases this runs in the main thread, which also manages the call graph.
    |
    | It is also possible to run a fragment of Scala code in a separate thread, e.g.,
    |
    | {* i+=1 *}
    |
    | This involves normally 2 atomic actions: one at the start of the thread execution,
    | and one at the end. The second action does not happen when the code
    | fragment execution ends because of an unhandled exception, or when
    | the execution is disrupted, e.g. using the disruption operator `/`.
    """
  ) {
      var i = 0;

      script..

        reset = {! i=0 !}

        p  = {* i+=1 *}
        q  = {* i=1; Thread.sleep(100); i+=100 *}
        r  = {* i=1; Thread.sleep(300); i+=300 *}

        s1 = reset;  p ; p
        s2 = reset;  p & p
        s3 = reset;  q & q
        s4 = reset;  q || r

      test(1) {runScript(s1); i shouldBe __`2`}
      test(2) {runScript(s2); i shouldBe __`2`}
      test(3) {runScript(s3); i shouldBe __`201`}
      test(4) {runScript(s4); i shouldBe __`101`}
    }

  koan(2)(
    """
    | Another kind of code fragment is called `tiny`, e.g.,
    |
    | {: i+=1 :}
    |
    | This does not count as an atomic action; rather it is much like the neutral process `[]`.
    | Upon activation the code is executed, and thereafer the tiny code fragment has no real effect any more.
    |
    | Tiny code fragments execute much more efficiently than normal code fragments.
    | Often they are appropriate to use. Sometimes not, though, e.g. when exclusive choice is needed.
    """
  ) {
      var i = 0;

      script..

        reset = {! i=0 !}

        p  = {: i+=1 :}
        q  = {! i+=1 !}

        s1 = reset;  p + p
        s2 = reset;  p + q
        s3 = reset;  q + q
        s4 = reset;  p ; p
        s5 = reset;  p ; q

      test(1) {runScript(s1); i shouldBe __`2`}
      test(2) {runScript(s2); i shouldBe __`2`}
      test(3) {runScript(s3); i shouldBe __`1`}
      test(4) {runScript(s4); i shouldBe __`2`}
      test(5) {runScript(s5); i shouldBe __`2`}
    }

  koan(3)(
    """
    |
    | Code fragments have access to a value named `here`.
    | That value is much like the `this` pointer for the current object instance.
    | But instead of pointing to that outer object, it points to the current
    | node in the call graph.
    |
    |There are also annotations that contain code fragments, e.g.,
    |
    | @{i=1}: {! i+=1 !}
    | @{i=1}: [ a + b ]
    |
    | Like with the tiny code fragment, the annotation is executed during its activation.
    | It operates on a script term, and it can access its operand node as using the
    | value named `there`.
    |
    | These nodes have methods for installing handlers that run at the time
    | of success, failure (i.e. deactivation without success) and deactivation.
    | E.g.,
    |
    | @{there.onActivate  {println("Activate")}
    |   there.onDeactivate{println("Deactivate")}
    |   there.onSuccess   {println("Success")}
    |   there.onFailure   {println("Failure")}}: {!  !}
    |
    """
  ) {
      var i = 0;

      script..

        s  = @{                   i+=    1
               there.onActivate  {i+=   10}
               there.onSuccess   {i+=  100}
               there.onFailure   {i+= 1000}
               there.onDeactivate{i+=10000}}: b

      test(1) {runWithInput(s)(b); ; i shouldBe __`10111`}
    }

  koan(4)(
    """
    |
    | There are also code fragments that execute code as response to events.
    | This is useful for instance for listeners to GUI controls. E.g.
    |
    |   {. i+=1 .}
    |
    | Sometimes such listeners should remain active for multiple events
    | when those could arrive in a short time, e.g. mouse movement events. E.g.,
    |
    |   {... i+=1 ...}
    |
    | Normally a succesful execution corresponds with an atomic action.
    | However, that will then occur shortly after the execution.
    | There is no guarantee that it will happen, because meanwhile
    | the event handling code fragment may have been excluded, e.g.
    | in an exclusive choice context (`+`).
    |
    | With annotations and the `there` value these event handling code fragments
    | may be properly setup to listen to events.
    |
    """
  ) {

      var listeners = List[() => Unit]()
      def trigger   = listeners.foreach(_())
      def    addListener(f: ()=>Unit) {listeners ::= f}
      def removeListener(f: ()=>Unit) {listeners.filter(elm => elm!=f)}

      def installExecutionListener(node: subscript.vm.model.callgraph.CallGraphNode) = {
        val myListener =  {()=>node.codeExecutor.executeAA}
        node.  onActivate{   addListener(myListener)}
        node.onDeactivate{removeListener(myListener)}
      }

      var i = 0;

      script..

        reset = {! i=0 !}

        once  = reset; @{installExecutionListener(there)}: {.   i+=1   .}
        some  = reset; @{installExecutionListener(there)}: {... i+=1 ...}

        s1    = reset; once & {!trigger!}
        s2    = reset; some & {!trigger!} b {!trigger!} c {!trigger!}

      test(1) {runScript   (s1)     ; i shouldBe __`1`}
//      test(2) {runWithInput(s2)(   ); i shouldBe __`1`}
//      test(3) {runWithInput(s2)(b  ); i shouldBe __`2`}
//      test(4) {runWithInput(s2)(b,c); i shouldBe __`3`}
    }
 }
