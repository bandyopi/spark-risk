package com.fintech.spark.feed

abstract class DataSet (name: String, fields: List[String])

case class FlatFile(
    name: String, 
    fields: List[String], 
    delimiter: String = ",", 
    hasHeader: Boolean = false) extends DataSet(name, fields)

case class DataFormatException(
    message: String = null, 
    cause: Throwable = null) extends RuntimeException (message, cause)