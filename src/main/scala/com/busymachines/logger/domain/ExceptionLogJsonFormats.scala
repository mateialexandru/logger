package com.busymachines.logger.domain

import com.busymachines.commons.Implicits._

trait ExceptionLogJsonFormats {
  implicit val codeLocationInfoFormat = format9(CodeLocationInfo)
  implicit val defaultExceptionFormat = format4(DefaultExceptionInfo)

  implicit val commonExceptionFormat = format6(CommonExceptionInfo)
  implicit val logMessageFormat = format3(LogMessage)
}