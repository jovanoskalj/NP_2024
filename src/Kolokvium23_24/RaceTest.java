package Kolokvium23_24;

import java.io.*;
import java.util.*;

class Participant {
    int id;
    long runningTime; // running time in milliseconds

    public Participant(int id, long runningTime) {
        this.id = id;
        this.runningTime = runningTime;
    }

    @Override
    public String toString() {
        // Format the running time as HH:mm:ss
        long hours = runningTime / 3600000;
        long minutes = (runningTime / 60000) % 60;
        long seconds = (runningTime / 1000) % 60;
        return String.format("%04d %02d:%02d:%02d", id, hours, minutes, seconds);
    }
}

class TeamRace {
    public static void findBestTeam(InputStream is, OutputStream os) throws IOException {
        Scanner scanner = new Scanner(is);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
        List<Participant> participants = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ");
            int id = Integer.parseInt(parts[0]);
            String startTimeStr = parts[1];
            String endTimeStr = parts[2];

            long startTimeInMillis = parseTimeToMillis(startTimeStr);
            long endTimeInMillis = parseTimeToMillis(endTimeStr);

            long runningTime = endTimeInMillis - startTimeInMillis;
            participants.add(new Participant(id, runningTime));
        }

        // Sort participants by their running time in ascending order
        participants.sort(Comparator.comparingLong(p -> p.runningTime));

        // Get the best 4 participants
        List<Participant> bestTeam = participants.subList(0, 4);

        // Calculate total running time of the best team
        long totalRunningTime = bestTeam.stream().mapToLong(p -> p.runningTime).sum();

        // Print the best 4 participants
        for (Participant participant : bestTeam) {
            writer.println(participant);
        }

        // Print the total running time in HH:mm:ss format
        long totalHours = totalRunningTime / 3600000;
        long totalMinutes = (totalRunningTime / 60000) % 60;
        long totalSeconds = (totalRunningTime / 1000) % 60;
        writer.printf("%02d:%02d:%02d%n", totalHours, totalMinutes, totalSeconds);

        writer.flush();
    }

    private static long parseTimeToMillis(String timeStr) {
        String[] timeParts = timeStr.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        int seconds = Integer.parseInt(timeParts[2]);

        // Convert time to milliseconds
        return (hours * 3600 + minutes * 60 + seconds) * 1000L;
    }
}

public class RaceTest {
    public static void main(String[] args) {
        try {
            TeamRace.findBestTeam(System.in, System.out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}