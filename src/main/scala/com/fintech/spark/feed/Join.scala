package com.fintech.spark.feed

import com.fintech.spark.common.OperationStatus

import com.fintech.spark.common.Operation

case class DataSetJoinOperation(
    override val name: String, 
    leftDataSet: DataSet, 
    rightDataSet: DataSet, 
    joinKeys: List[Tuple2[String, String]],
    leftDataSetFields: List[String],
    rightDataSetFields: List[String]) extends Operation {
  /*
   * Joins left and right datasets using joinKeys
   */
  override def execute() = {
    OperationStatus.FAILED
  }
}