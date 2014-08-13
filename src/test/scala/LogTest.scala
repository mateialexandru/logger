
import org.apache.logging.log4j.LogManager
import org.scalatest.FlatSpec
/**
 * Created by alex on 13.08.2014.
 */
class Bar {
   val logger = LogManager.getLogger();

    def doIt():Boolean= {
      logger.entry();
      logger.error("Did it again!");
      return logger.exit(false);
    }
}

class LogTest extends FlatSpec {
val logger= LogManager.getLogger()
  "Log test" should "log by defaul" in {
    logger.trace("Starting test..")
    val bar = new Bar()
    if(!bar.doIt())
      logger.error("Didn't do it")
    logger.trace("Exiting application.")
  }
}
