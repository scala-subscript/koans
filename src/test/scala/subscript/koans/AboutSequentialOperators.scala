package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutSequentialOperators extends KoanSuite with OperatorKoansHelper {

  koan(1)(
    """
    | There are three sequential operators available for script expressions:
    |
    | - as already introduced: white space between terms, or even nothing.
    | - a semicolon (;). This is much like the meaning of `;` in Scala
    | - a new line, for which about the same semicolon inference rules hold as in Scala
    |
    | Note that, like in Scala, the white space operator has a high priority,
    | whereas the semicolon and the new line have a low priority.
    | The new line has even a lower priority than the semicolon.
    | This has some significance which will appear when Iterations are discussed.
    """
  ) {
    var    i    = 0
    script foo1 = {! i += 1 !}{!  i += 1 !}
    script foo2 = {! i += 1 !} {! i += 1 !}
    script foo3 = {! i += 1 !};{! i += 1 !}
    script foo4 = {! i += 1 !}
                ; {! i += 1 !}
    script foo5 = {! i += 1 !}
                  {! i += 1 !}

    test(1) { runScript(foo1); i shouldBe __ }
    test(2) { runScript(foo2); i shouldBe __ }
    test(3) { runScript(foo3); i shouldBe __ }
    test(4) { runScript(foo4); i shouldBe __ }
    test(5) { runScript(foo5); i shouldBe __ }
  }

  koan(2)(
    """
    | In SubScript things are not just executed like that;
    | there is an execution mechanism that performs multiple steps.
    |
    | When a top level script is executed (`top level` means it is run from Scala code)
    | first the script expression of body gets `activated`.
    | If the script expression is a sequence, this means that the first operand is activated.
    | If the operand is an atomic action, that will later 'happen';
    | one of the consequences is that its Scala code fragment is executed.
    |
    | Thereafter it will be the turn for the next operand of the sequence operator,
    | until all are done, and then the entire script is ready.
    |
    | The execution mechanism is a in fact more complicated than that, see further on this page.
    |
    | In this koan, `runWithInput` runs a script with certain input; thereafter
    | something may be still activated in the call graph.
    | It is up to you to answer what that is, in a call to `thenActivatedOrSuccess`
    |
    | E.g., suppose `script s1 = b  c  d`,
    | then for `runWithInput(s1)(b,c)`
    | you should provide the answer `thenActivatedOrSuccess(d)`.
    |
    | If there are more applicable activated operands, separate those by a comma, as in `thenActivatedOrSuccess(d,e)`
    | If you think no operands are activated leave the list empty: `thenActivatedOrSuccess()`.
    |
    | All these methods and operands are defined at the KoanSuite trait.
    | This current class extends from that trait;
    | the methods and operands are not part of the SubScript distribution.
    """
  ) {
    script..
      s1 = b  c  d
      s2 = b; c; d

    test(1) { runWithInput(s1)(     ); thenActivatedOrSuccess( __ ) }
    test(2) { runWithInput(s1)(b    ); thenActivatedOrSuccess( __ ) }
    test(3) { runWithInput(s1)(b,c  ); thenActivatedOrSuccess( __ ) }
    test(4) { runWithInput(s1)(b,c,d); thenActivatedOrSuccess( __ ) }
    test(5) { runWithInput(s2)(b,c  ); thenActivatedOrSuccess( __ ) }
  }

  koan(3)(
     """
       | Some more details on the execution mechanism:
       |
       | If the operand is a script call, the called script is activated.
       | This is much like the way a call stack is created in conventional programming languages.
       | The activated structure in SubScript is more generic: a so called `call graph`.
       | We say that the top level script is at the top of that graph;
       | the deeper the activation level is, the lower the corresponding nodes are in the graph.
       |
       | If the operand is a atomic action (of the form `{! ... !}`), then
       | this action is also activated, which means that it may (or may not!) be executed
       | later, when there are no more activations to be done, and other operations
       | on the call graph.
       |
       | After an atomic action has been executed, it normally `succeeds`.
       | The alternative is that it `fails`, which happens when an uncaught exception was thrown inside.
       | The success will then be reported upwards in the call graph.
       | Normally, this reporting continues through script calls.
       | When such a reported success arrives at a call graph node that
       | corresponds to a sequential operator, two things may happen:
       |
       | - if the success did not come from a node that corresponds to the last operand of the sequence,
       |   the next operand of the sequence is activated
       | - else the node corresponding to the sequence operator has a success itself
       |
       | And after the success reporting the code fragment is deactivated: taken out of the call graph,
       | since it has nothing more to do. The same happens with other 'outdated' nodes.
       |
       | E.g., suppose we have a script
       |
       |  s = {!a!} {!b!}
       |
       | Then activating s will initially yield such a call graph (2 topmost nodes are not displayed):
       |
       |    s
       |    |
       |    ;
       |    |
       |  {!a!}
       |
       | Then the atomic action {!a!} is executed successfully.
       | It has a success which is reported upwards; it causes the sequential operator to start a new node.
       | The graph becomes
       |
       |      s
       |      |
       |      ;
       |     /\
       | {!a!} {!b!}
       |
       | As the first atomic action, {!a!}, is done, its node is deactivated:
       |    s
       |    |
       |    ;
       |    |
       |  {!b!}
       |
       | Then {!b!} is executed; its success is reported through the semicolon to the top of the graph.
       | Finally, all 3 nodes from the bottom upwards are deactivated and the script execution ends successfully.
       |
       | During execution the call graph grows and shrinks. Parts of it correspond with
       | parts of the abstract syntax trees (AST) of the called scripts.
       | E.g. the AST of the called script is
       |
       |      s
       |      |
       |      ;
       |     /\
       | {!a!} {!b!}
       |
       | This is no big deal, for now.
       | In general, the similarity of parts of the ASTs and parts of the call graph are a big
       | advantage. The call graph shows what an active element corresponds to:
       | in what call has it been activated etc, with a clear correspondence to the static program text,
       | through the AST.
       |
       | Compare this to the execution of a Scala thread. It has been launched from somewhere
       | that you don't really see, unless it produceces a call stack, e.g. upon an exception.
       | Moreover, the thread may end at some point; that has usually no consequence for the rest of
       | the program, in terms of executing a specific set of code.
       | Launch a few threads in a Java program and you may well end up in a mess.
       |
       | In SubScript the activations and executions, even of code that will run in different
       | threads, will always be within the structure of a call graph.
       | When depicted in a graphical debugger you can see much better
       | where threads have been launched from, etc.
       |
       """
  ) {
  }
}
