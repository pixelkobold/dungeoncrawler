package com.pixelkobold.log;

public enum LogLevel {
	INFO("[INFO]"), WARNING("[WARNING]"), CRITICAL("[CRITICAL]"), DEBUG("[DEBUG]");
	private String lvl;

	private LogLevel(String level) {
		lvl = level;
	}

	@Override
	public String toString() {
		return lvl;
	}
}
