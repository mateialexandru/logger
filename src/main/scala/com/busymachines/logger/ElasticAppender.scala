package com.busymachines.logger

import java.io.Serializable
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.appender.{ AbstractAppender, DefaultErrorHandler }
import org.apache.logging.log4j.core.config.plugins.{ PluginElement, PluginAttribute, PluginFactory, Plugin }
import org.apache.logging.log4j.core.impl.ThrowableProxy
import org.apache.logging.log4j.core.layout.{ AbstractStringLayout, PatternLayout }
import org.apache.logging.log4j.core._
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import com.busymachines.commons.CommonException

import com.busymachines.commons.Implicits._

/**
 * Created by Alexandru Matei on 14.08.2014.
 */

object ElasticAppender {
  @PluginFactory
  def createAppender(@PluginAttribute("name") name: String,
    @PluginAttribute("ignoreExceptions") ignoreExceptions: Boolean,
    @PluginAttribute("hostNames") hosts: String,
    @PluginAttribute("port") port: String,
    @PluginAttribute("clusterName") clusterName: String,
    @PluginAttribute("indexNamePrefix") indexNamePrefix: String,
    @PluginAttribute("indexNameDateFormat") indexNameDateFormat: String,
    @PluginAttribute("indexDocumentType") indexDocumentType: String,
    @PluginElement("Layout") layout: Layout[_ <: Serializable],
    @PluginElement("Filters") filter: Filter): ElasticAppender = new ElasticAppender(name, layout, filter, ignoreExceptions, hosts, port, clusterName, indexNamePrefix, indexNameDateFormat, indexDocumentType);
}

@Plugin(name = "Elastic", category = "Core", elementType = "appender", printObject = true)
class ElasticAppender(name: String, layout: Layout[_ <: Serializable], filter: Filter, ignoreExceptions: Boolean, hosts: String, port: String, clusterName: String, indexNamePrefix: String, indexNameDateFormat: String, indexDocumentType: String) extends AbstractAppender(name, filter, layout, ignoreExceptions) {
  lazy val logger = LogManager.getLogger()
  lazy val client = new TransportClient(ImmutableSettings.settingsBuilder().put("cluster.name", clusterName))
    .addTransportAddresses(hosts.split(",").map(new InetSocketTransportAddress(_, port.toInt)): _*)

  lazy val actualIndexName = s"$indexNamePrefix-${DateTimeFormat.forPattern(indexNameDateFormat).print(DateTime.now)}"

  logger.debug(s"Config : host=$hosts port=$port clusterName=$clusterName indexNamePrefix=$indexNamePrefix indexNameDateFormat=$indexNameDateFormat indexDocumentType=$indexDocumentType")
  override def append(event: LogEvent): Unit = {
    send(event)
  }

  def send(event: LogEvent) {}
//    val message = LogMessage(
//      event.getLevel.toString,
//      event.getThreadName,
//      event.getSource.getClassName,
//      event.getSource.getFileName,
//      event.getSource.getMethodName,
//      event.getSource.getLineNumber,
//      event.getMessage.getFormattedMessage,
//      DateTimeFormat.longDateTime.print(event.getTimeMillis),
//      event.getThrownProxy match {
//        case proxy: ThrowableProxy => proxy.getMessage
//        case _ => ""
//      },
//      exception = Option(event.getThrown()))

//    try {
//      client.prepareIndex(
//        actualIndexName, indexDocumentType)
//        .setSource(message.toString)
//        .execute
//        .actionGet
//    } catch {
//      case ex: Exception =>
//        println(s"Exception while using ElasticSearch client! $ex")
//    } finally {
//    }
//  }

}
