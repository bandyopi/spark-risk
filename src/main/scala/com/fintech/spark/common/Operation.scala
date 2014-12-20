package com.fintech.spark.common

/*
 * Enumeration to represent the execution status of an operation
 */
object OperationStatus extends Enumeration {
  val SUCCEEDED = Value("Succeeded")
  val FAILED = Value("Failed")
}

/*
 * A trait class for an Operation
 */
trait Operation {
  
  /*
   * Name of the operation
   */
  def name: String
  /*
   * Returns "Operation[<name>]"
   */
  override def toString(): String = {
    "Operation[" + name + "]"
  }

  /*
   * Method to execute the operation
   */
  def execute(): OperationStatus.Value
}

/*
 * Represents a No-Operation
 */
case object NoOp extends Operation {
  override val name = "NoOp"
  override def execute(): OperationStatus.Value = {
    OperationStatus.SUCCEEDED
  }
}
