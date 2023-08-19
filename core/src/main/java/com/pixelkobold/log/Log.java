package com.pixelkobold.log;

import com.badlogic.gdx.utils.TimeUtils;
import com.pixelkobold.config.Config;
import com.pixelkobold.util.GameUtils;
import com.pixelkobold.util.SystemProperties;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static FileWriter fw;
    private static BufferedWriter writer;
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static void init() throws Exception {
        File log = new File(GameUtils.GAME_DIR + "log.txt");
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
        Runtime.getRuntime().addShutdownHook(new Thread(() -> stop()));
    }

    public static void log(LogLevel level, String msg) {
        if (level == LogLevel.DEBUG && !Config.debug)
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

    public static String message(String message) {
        Date date = new Date(TimeUtils.millis());

        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(dateFormat.format(date));
        sb.append("] ");
        sb.append(message);

        return sb.toString();
    }
}
