package com.pixelkobold.entity.components;

import com.badlogic.ashley.core.Component;

import java.util.HashMap;


public class StatsComponent implements Component {
    private HashMap<String, Object> stats;

    public void modStat(String stat, int mod) {
        if (stats.get(stat) instanceof Integer i) {
            stats.put(stat, i + mod);
        }
    }

    public void modStat(String stat, float mod) {
        if (stats.get(stat) instanceof Float f) {
            stats.put(stat, f + mod);
        }
    }

    public void setStat(String stat, Object v) {
        stats.put(stat, v);
    }

    public Object get(String stat) {
        return stats.get(stat);
    }

}
