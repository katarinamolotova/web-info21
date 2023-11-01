package edu.school21.info21.enums;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public enum TableNames {
    CHECK_TABLE("checks"),
    PEERS_TABLE("peers"),
    TASKS_TABLE("tasks"),
    P2P_TABLE("p2p"),
    VERTER_TABLE("verter"),
    TRANSFERRED_POINTS_TABLE("transferred_points"),
    FRIENDS_TABLE("friends"),
    RECOMMENDATIONS_TABLE("recommendations"),
    XP_TABLE("xp"),
    TIME_TRACKING_TABLE("time_tracking"),
    CUSTOM("custom");

    private final String name;

    TableNames(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> getAllNames() {
        return EnumSet.allOf(TableNames.class)
                .stream()
                .map(it -> it.name)
                .collect(Collectors.toList());
    }

    public static TableNames fromString(String text) {
        for (TableNames b : TableNames.values()) {
            if (b.getName().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}

