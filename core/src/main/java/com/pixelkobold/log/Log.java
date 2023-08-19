package com.pixelkobold.log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static final DateFormat dateFormat = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]");

    private static String message(String message) {
        Date date = new Date(TimeUtils.millis());

        StringBuffer sb = new StringBuffer();
        sb.append(dateFormat.format(date));
        sb.append(" ");
        sb.append(message);

        return sb.toString();
    }

    public static void info(String tag, String message) {
        Gdx.app.log(tag, message(message));
    }

    public static void debug(String tag, String message) {
        Gdx.app.debug(tag, message(message));
    }

    public static void error(String tag, String message) {
        Gdx.app.error(tag, message(message));
    }
}
