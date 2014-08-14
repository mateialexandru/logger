package com.busymachines.logger.domain

case class CodeLocationInfo(
  level: Option[String],
  thread: Option[String],
  className: Option[String],
  fileName: Option[String],
  methodName: Option[String],
  lineNumber: Option[Int],
  message: Option[String],
  time: Option[String],
  stackTrace: Option[String])

case class DefaultException(
  val `type`: String = "StandardException",
  val message: Option[String],
  val cause: Option[String],
  val stackTrace: List[String])

case class CommonExceptionInfo(
  val `type`: String = "CommonException",
  val message: Option[String],
  val cause: Option[String],
  val stackTrace: List[String],
  errorId: Option[String],
  parameters: Map[String, String])

case class LogMessage(
  codeLocation: CodeLocationInfo,
  defaultException: Option[DefaultException] = None,
  commonException: Option[CommonExceptionInfo] = None)