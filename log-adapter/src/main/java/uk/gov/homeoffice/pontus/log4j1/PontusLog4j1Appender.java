package uk.gov.homeoffice.pontus.log4j1;

/*
 * Copyright 2014 Higher Frequency Trading
 *
 * http://www.higherfrequencytrading.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import net.openhft.chronicle.logger.ChronicleLogLevel;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import java.lang.management.ManagementFactory;
public class PontusLog4j1Appender extends net.openhft.chronicle.logger.log4j1.TextVanillaChronicleAppender {

    public static final String uRunning = ManagementFactory.getRuntimeMXBean().getName()+"#";;

    public PontusLog4j1Appender() {
        super();
    }

    public static ChronicleLogLevel toChronicleLogLevel(final Level level) {
        switch(level.toInt()) {
            case Level.DEBUG_INT:
                return ChronicleLogLevel.DEBUG;
            case Level.TRACE_INT:
                return ChronicleLogLevel.TRACE;
            case Level.INFO_INT:
                return ChronicleLogLevel.INFO;
            case Level.WARN_INT:
                return ChronicleLogLevel.WARN;
            case Level.FATAL_INT:
            case Level.ERROR_INT:
                return ChronicleLogLevel.ERROR;
            default:
                throw new IllegalArgumentException(level.toInt() + " not a valid level value");
        }
    }


    @Override
    public void doAppend( LoggingEvent event) {

      ThrowableInformation ti = event.getThrowableInformation();


        writer.write(
                toChronicleLogLevel(event.getLevel()),
                event.getTimeStamp(),
                event.getThreadName(),
                event.getLoggerName(),
                uRunning.concat(event.getRenderedMessage()),
                ti == null? null :ti.getThrowable());
    }
}

