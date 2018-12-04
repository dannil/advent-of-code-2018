package day4;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Objects;

public class Task2 {

    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        List<String> input = Files.readAllLines(Paths.get(absolutePath, "src/day4", "input.txt"));

        List<GuardEvent> guardEvents = new ArrayList<>();
        for (String action : input) {
            GuardEvent guardEvent = new GuardEvent(action);
            guardEvents.add(guardEvent);
        }
        
        Collections.sort(guardEvents);
        
        Collection<Guard> guards = getGuards(guardEvents);
        Guard guard = getGuardWhichFallsAsleepMostForAnyMinute(guards);
        int mostFrequentMinuteAsleep = guard.getMostFrequentMinuteAsleep();
        System.out.println(guard.number * mostFrequentMinuteAsleep);
    }
    
    private static Collection<Guard> getGuards(List<GuardEvent> guardEvents) {
        Map<Integer, Guard> guards = new HashMap<>();
        Guard guard = null;
        for (GuardEvent guardEvent : guardEvents) {
            String geAction = guardEvent.action.substring(guardEvent.action.indexOf("#") + 1);
            if (!geAction.equals("falls asleep") && !geAction.equals("wakes up")) {
                int number = Integer.valueOf(geAction.substring(0, geAction.indexOf(" ")));
                if (!guards.containsKey(number)) {
                    guard = new Guard(number);
                    guards.put(number, guard);
                } else {
                    guard = guards.get(number);
                }
            }
            guard.events.add(guardEvent);
        }
        return guards.values();
    }

    private static Guard getGuardWhichFallsAsleepMostForAnyMinute(Collection<Guard> guards) {
        Guard guardWhichFallsAsleepMostForAnyMinute = new Guard(-1);
        int mostFrequentMinuteCount = -1;
        for (Guard guard : guards) {
            guard.getTotalMinutesAsleep();
            for (Entry<Integer, Integer> entry : guard.minuteFrequency.entrySet()) {
                if (entry.getValue() > mostFrequentMinuteCount) {
                    mostFrequentMinuteCount = entry.getValue();
                    guardWhichFallsAsleepMostForAnyMinute = guard;
                }
            }
        }
        return guardWhichFallsAsleepMostForAnyMinute;
    }

    private static class Guard {
        private int number;
        private List<GuardEvent> events;
        private Map<Integer, Integer> minuteFrequency;

        public Guard(int number) {
            this.number = number;
            this.events = new ArrayList<>();
            this.minuteFrequency = new HashMap<>();
        }

        public int getTotalMinutesAsleep() {
            int minutesAsleep = 0;
            LocalDateTime lastFallsAsleep = null;
            LocalDateTime lastWakesUp;
            for (GuardEvent ge : events) {
                if (ge.action.equals("falls asleep")) {
                    lastFallsAsleep = ge.timeOfEvent;
                } else if (ge.action.equals("wakes up")) {
                    lastWakesUp = ge.timeOfEvent;
                    minutesAsleep += ChronoUnit.MINUTES.between(lastFallsAsleep, lastWakesUp) - 1;
                    int startMinute = lastFallsAsleep.getMinute();
                    int endMinute = lastWakesUp.getMinute();
                    for (int i = startMinute; i < endMinute; i++) {
                        if (minuteFrequency.containsKey(i)) {
                            minuteFrequency.put(i, minuteFrequency.get(i) + 1);
                        } else {
                            minuteFrequency.put(i, 1);
                        }
                    }
                }
            }
            return minutesAsleep;
        }

        public int getMostFrequentMinuteAsleep() {
            int mostFrequentMinute = -1;
            int mostFrequentMinuteCount = -1;
            for (Entry<Integer, Integer> entry : minuteFrequency.entrySet()) {
                if (entry.getValue() > mostFrequentMinuteCount) {
                    mostFrequentMinute = entry.getKey();
                    mostFrequentMinuteCount = entry.getValue();
                }
            }
            return mostFrequentMinute;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.number);
        }

        @Override
        public boolean equals(Object obj) {
            Guard g = (Guard) obj;
            return Objects.equals(this.number, g.number);
        }

    }

    private static class GuardEvent implements Comparable<GuardEvent> {
        private LocalDateTime timeOfEvent;
        private String action;

        public GuardEvent(String guardEvent) {
            String localDateTime = guardEvent.substring(1, guardEvent.indexOf("]"));
            timeOfEvent = LocalDateTime.parse(localDateTime.replace(" ", "T"));
            action = guardEvent.substring(guardEvent.indexOf("]") + 2);
        }

        @Override
        public int compareTo(GuardEvent o) {
            return timeOfEvent.compareTo(o.timeOfEvent);
        }

    }

}
