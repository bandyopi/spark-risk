package com.fintech.spark.workflow

import scala.collection.mutable.Set
import com.fintech.spark.common.{ OperationStatus, Operation, NoOp }

/*
 * An abstract class representing an operation in a workflow. 
 * The operation itself can be a sub workflow.
 */
class WorkflowOperation(
  override val name: String,
  context: Option[WorkflowContext],
  wfOperation: WorkflowOperation) extends Operation {

  def this(name: String, context: Option[WorkflowContext]) = this(name, context, NoOpWorkflow)

  def operation: WorkflowOperation = wfOperation

  val predecessors: Set[WorkflowOperation] = Set.empty[WorkflowOperation]

  override def execute(): OperationStatus.Value = {
    var status = OperationStatus.SUCCEEDED
    try {
      // execute in parallel
      predecessors.par.foreach(_.execute())
      println(this + " started.")
      operation.execute()
      println(this + " ended.")
    } catch {
      case e: Exception => status = OperationStatus.FAILED
    }
    status
  }

  def >>(nextOp: WorkflowOperation): WorkflowOperation = {
    nextOp.predecessors += this
    nextOp
  }

  def <<(predecessor: WorkflowOperation): WorkflowOperation = {
    predecessors += predecessor
    this
  }
  def <<(predecessors: Iterable[WorkflowOperation]): WorkflowOperation = {
    predecessors.foreach(<<(_))
    this
  }

  def dependsOn(dependent: WorkflowOperation) = {
    predecessors += dependent
    this
  }
  def dependsOn(dependents: Set[WorkflowOperation]) = {
    predecessors ++= dependents
    this
  }

  /*
   * Returns "Operation[<name>]"
   */
  override def toString(): String = {
    "WorkflowOperation[" + name + "]"
  }
}

object WrapperWorkflow {
  def apply(name: String, context: Option[WorkflowContext])(wfOperation: WorkflowOperation): WorkflowOperation = {
    new WorkflowOperation(name, context, wfOperation) 
  }
}

object NoOpWorkflow extends WorkflowOperation("NoOp Workflow", None) {
  override def execute(): OperationStatus.Value = {
    OperationStatus.SUCCEEDED
  }
}

object EmptyWorkflow extends WorkflowOperation("Empty Workflow", None) {
    override def execute(): OperationStatus.Value = {
    var status = OperationStatus.SUCCEEDED
    try {
      // execute in parallel
      predecessors.par.foreach(_.execute())
    } catch {
      case e: Exception => status = OperationStatus.FAILED
    }
    status
  }
}

