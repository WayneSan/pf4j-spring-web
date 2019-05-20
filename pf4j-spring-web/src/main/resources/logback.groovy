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
// !!! Put the bellow classes on level TRACE when you are in trouble
logger("org.pf4j.PluginClassLoader", TRACE, ["PF4J_CONSOLE"], false)
logger("org.pf4j.AbstractPluginManager", TRACE, ["PF4J_CONSOLE"], false)
logger("org.pf4j.AbstractExtensionFinder", TRACE, ["PF4J_CONSOLE"], false)
