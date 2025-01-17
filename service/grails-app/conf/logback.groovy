import java.nio.charset.StandardCharsets

import org.springframework.boot.logging.logback.ColorConverter
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender
import grails.util.BuildSettings
import grails.util.Environment

conversionRule 'clr', ColorConverter
conversionRule 'wex', WhitespaceThrowableProxyConverter

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = StandardCharsets.UTF_8

        pattern =
                '%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} ' + // Date
                '%clr(%5p) ' + // Log level
                '%clr(---){faint} %clr([%15.15t]){faint} ' + // Thread
                "%clr(%-30.30logger{29} %15(%replace([%X{tenant:-_NO_TENANT_}]){'\\[_NO_TENANT_\\]',''})){cyan} %clr(:){faint} " +
                '%m%n%wex' // Message
    }
}

// Default level should be info.
root(WARN, ['STDOUT'])
logger ('org.hibernate.orm.deprecation', ERROR)
logger ('com.k_int.okapi.OkapiSchemaHandler', INFO)

// Just increase verbosity for dev/test.


boolean devEnv = Environment.isDevelopmentMode() || Environment.currentEnvironment.name == 'vagrant-db'

if (devEnv || Environment.currentEnvironment == Environment.TEST) {
  
  // Change default verbosity to INFO for dev/test
  root(INFO, ['STDOUT'])
  
  // Increase specific levels to debug
  logger 'grails.app.init', DEBUG
  logger 'grails.app.controllers', DEBUG
  logger 'grails.app.domains', DEBUG
  logger 'grails.app.jobs', DEBUG
  logger 'grails.app.services', DEBUG
  logger 'com.zaxxer.hikari.pool.HikariPool', DEBUG
  
  logger 'com.k_int', DEBUG
  logger 'com.k_int.web.toolkit', DEBUG
  logger 'org.olf', DEBUG
  logger 'grails.views', TRACE
  
  
  if (Environment.currentEnvironment == Environment.TEST) {
    // Test only.
//    logger 'org.hibernate', DEBUG
    logger 'com.k_int.okapi.OkapiClient', TRACE
    logger 'groovy.net.http.JavaHttpBuilder', DEBUG
//    logger 'org.hibernate.loader.criteria', TRACE
    logger 'org.hibernate.SQL', DEBUG
    logger 'org.hibernate.type.descriptor.sql.BasicBinder', TRACE
  }
}
else {
  logger ('org.olf', INFO)
}

def targetDir = BuildSettings.TARGET_DIR
if (devEnv && targetDir != null) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            charset = StandardCharsets.UTF_8
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}
