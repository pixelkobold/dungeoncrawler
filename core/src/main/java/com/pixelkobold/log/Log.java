package com.pixelkobold.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pixelkobold.config.Config;
import com.pixelkobold.util.GameUtils;
import com.pixelkobold.util.SystemProperties;

public class Log {
	private static File log;
	private static FileWriter fw;
	private static BufferedWriter writer;
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public static void init() throws Exception {
		log = new File(GameUtils.GAME_DIR + "log.txt");
		if (!log.exists()) {
			new File(GameUtils.GAME_DIR).mkdirs();
			log.createNewFile();
		}
		try {
			// System.out.println(log.exists());
			fw = new FileWriter(log);
			writer = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				Log.stop();
			}
		});
	}

	public static void log(LogLevel level, String msg) {
		if(level==LogLevel.DEBUG && !Config.debug)
			return;

		Date date = new Date();

		StringBuilder sb = new StringBuilder(dateFormat.format(date));
		sb.append(level.toString());
		sb.append(msg);
		sb.append(SystemProperties.LINE_SEPARATOR);
		String out = sb.toString();
		try {
			System.out.print(out);
			writer.write(out);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void stop() {
		try {
			System.out.println("Stopping log");
			writer.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
