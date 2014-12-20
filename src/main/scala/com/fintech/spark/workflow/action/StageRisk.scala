package com.fintech.spark.workflow.action

import com.fintech.spark.common._
import com.fintech.spark.workflow._

class StageRisk(context: Option[WorkflowContext])
  extends WorkflowOperation("Stage Risk", context) {

  override val operation: WorkflowOperation = new WorkflowOperation(name, context) {
    override def execute(): OperationStatus.Value = {
      println("\tStaging Risk")
      OperationStatus.SUCCEEDED
    }
  }

}
