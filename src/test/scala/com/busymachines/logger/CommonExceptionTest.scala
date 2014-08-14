package com.busymachines.logger

import com.busymachines.commons.CommonException
import org.apache.logging.log4j.LogManager
import org.scalatest.FlatSpec

/**
 * Created by Alexandru Matei on 14.08.2014.
 */
class CommonExceptionTest extends FlatSpec{

  val logger= LogManager.getLogger()
  "Logger" should "log commons exceptions properly" in {
    logger.error("Commons Exception 1",  new CommonException("First type of error",Some("12"),Map("party"->"BusyMachines","user"->"Alexandru"),Some(new IndexOutOfBoundsException())))

  }
}
