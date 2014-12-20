package com.fintech.spark

import com.fintech.spark.workflow._
import com.fintech.spark.workflow.action.StageRisk

object SparkRiskApp extends App {
  println("Hello!")
  println(com.fintech.spark.common.NoOp)

  val processFeed = new WorkflowOperation("Process Feed", None)
  val parseRisk = new WorkflowOperation("Parse Risk", None)
  val stageRisk = new StageRisk(None)

  val getInstAttr = new WorkflowOperation("Get Instrument Attributes", None)
  val getProdAttr = new WorkflowOperation("Get Product Attributes", None)
  val getCptyAttr = new WorkflowOperation("Get Counterparty Attributes", None)
  val getAttributes = new WorkflowOperation("Get All Attributes for Enrichment", None)
  val enrichAttributes = WrapperWorkflow("Enrich Attributes", None)(
    EmptyWorkflow << Set(getInstAttr, getProdAttr, getCptyAttr))

  val transformRisk = new WorkflowOperation("Transform Risk", None)
  val processDimensions = new WorkflowOperation("Process Dimensions", None)
  val loadRisk = new WorkflowOperation("Load Risk", None)

  val feedProcWf = parseRisk >> stageRisk >> enrichAttributes >> transformRisk >>
    processDimensions >> loadRisk
  feedProcWf.execute

  //  val feedProcWf = new WorkflowOperation("Process Feed", None) dependsOn (
  //    loadRisk dependsOn (
  //      processDimensions dependsOn (
  //        transformRisk dependsOn (
  //          enrichAttributes dependsOn (
  //            stageRisk dependsOn (
  //              parseRisk))))))

}