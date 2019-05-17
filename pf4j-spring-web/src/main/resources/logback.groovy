import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO

import ch.qos.logback.classic.encoder.PatternLayoutEncoder

//always a good idea to add an on console status listener
statusListener(OnConsoleStatusListener)

//## Define App Name
context.name = "PF4J-Spring-Web"

def logLevel = INFO

appender("CONSOLE", ConsoleAppender) {
	encoder(PatternLayoutEncoder) {
		pattern = "%date{\"HH:mm:ss,SSS\"} |-%highlight(%-5level) [%thread] %logger - %m%n"
	}
}

appender("DEMO_CONSOLE", ConsoleAppender) {
	encoder(PatternLayoutEncoder) {
		pattern = "%date{\"HH:mm:ss,SSS\"} |-%highlight(%-5level) [%thread] %boldMagenta(%logger) - %m%n"
	}
}

appender("PF4J_CONSOLE", ConsoleAppender) {
	encoder(PatternLayoutEncoder) {
		pattern = "%date{\"HH:mm:ss,SSS\"} |-%highlight(%-5level) [%thread] %boldYellow(%logger) - %m%n"
	}
}


def appenderNames = ["CONSOLE"]

scan("10 seconds")

root(logLevel, appenderNames)

//## Define App logger level
logger("com.example", logLevel, ["DEMO_CONSOLE"], false)

logger("org.pf4j", logLevel, ["PF4J_CONSOLE"], false)
logger("org.pf4j.PluginClassLoader", DEBUG, ["PF4J_CONSOLE"], false)
logger("org.pf4j.AbstractExtensionFinder", DEBUG, ["PF4J_CONSOLE"], false)
