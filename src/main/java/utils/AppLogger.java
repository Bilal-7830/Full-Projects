package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {
	private Logger logger = null;

	public Logger getLogger() {
		if (logger == null) {
			setLogger();
		}
		return logger;
	}

	public void setLogger() {
		this.logger = LoggerFactory.getLogger(AppLogger.class);
	}

}
