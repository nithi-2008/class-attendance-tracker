package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Problem 6: Class Attendance Tracker
 *
 * TASK:
 * A teacher records attendance for one student as an array of 1s (present) and 0s (absent),
 * one value per day. The teacher wants to know how many days the student was present and
 * the longest run of days the student attended without a break.
 *
 * Implement a function attendanceSummary(days) that returns the number of present days
 * and the length of the longest unbroken run of present days.
 *
 * CONSTRAINTS:
 * • 1 <= length of days <= 100
 * • Each value is 0 or 1
 *
 * COMPLEXITY REQUIREMENTS:
 * • Time Complexity:  O(n) in a single pass, where n is length of days
 * • Auxiliary Space:  O(1) constant extra space
 */
public class ClassAttendanceTracker {

    /**
     * Immutable result holder for attendance metrics.
     */
    public static class AttendanceResult {
        private final int presentDays;
        private final int longestStreak;

        public AttendanceResult(int presentDays, int longestStreak) {
            this.presentDays = presentDays;
            this.longestStreak = longestStreak;
        }

        public int getPresentDays() {
            return presentDays;
        }

        public int getLongestStreak() {
            return longestStreak;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            AttendanceResult other = (AttendanceResult) obj;
            return this.presentDays == other.presentDays && this.longestStreak == other.longestStreak;
        }

        @Override
        public int hashCode() {
            return 31 * presentDays + longestStreak;
        }

        @Override
        public String toString() {
            return "Present: " + presentDays + ", Longest streak: " + longestStreak;
        }
    }

    /**
     * Computes the number of present days and the longest continuous streak of present days in a single pass.
     *
     * @param days array of binary attendance indicators (1 = present, 0 = absent)
     * @return AttendanceResult containing total present days and longest streak
     * @throws IllegalArgumentException if days array is null, empty, exceeds 100 elements, or contains non-binary values
     */
    public static AttendanceResult attendanceSummary(int[] days) {
        if (days == null || days.length == 0) {
            throw new IllegalArgumentException("Days array must not be null or empty.");
        }
        if (days.length > 100) {
            throw new IllegalArgumentException("Days array length (" + days.length + ") exceeds maximum constraint of 100.");
        }

        int totalPresent = 0;
        int currentStreak = 0;
        int longestStreak = 0;

        // Single pass linear scan: O(n) time, O(1) auxiliary space
        for (int day : days) {
            if (day == 1) {
                totalPresent++;
                currentStreak++;
                if (currentStreak > longestStreak) {
                    longestStreak = currentStreak;
                }
            } else if (day == 0) {
                currentStreak = 0;
            } else {
                throw new IllegalArgumentException("Invalid attendance value '" + day + "'. Each value must be 0 or 1.");
            }
        }

        return new AttendanceResult(totalPresent, longestStreak);
    }

    /**
     * Parses standard formatted string representations of an attendance array,
     * e.g., "[1, 1, 0, 1, 1, 1, 0, 1]" or "1, 1, 0, 1" or "1 1 0 1".
     *
     * @param input raw input string
     * @return integer array of attendance values
     */
    public static int[] parseDaysFromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new int[0];
        }

        String cleaned = input.trim();
        if (cleaned.startsWith("[") && cleaned.endsWith("]")) {
            cleaned = cleaned.substring(1, cleaned.length() - 1).trim();
        }

        if (cleaned.isEmpty()) {
            return new int[0];
        }

        String[] tokens = cleaned.split("[,\\s]+");
        int[] result = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            result[i] = Integer.parseInt(tokens[i].trim());
        }
        return result;
    }

    /**
     * Interactive CLI entry point.
     * Supports:
     * 1. Piped/Standard input with bracket notation: "[1, 1, 0, 1, 1, 1, 0, 1]"
     * 2. Piped/Standard input with whitespace separated numbers: "1 1 0 1 1 1 0 1"
     * 3. CLI arguments: java -cp out src.ClassAttendanceTracker 1 1 0 1
     * 4. Fallback default to Sample 1 if no input provided
     */
    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            // Read from command-line arguments
            String combined = String.join(" ", args);
            int[] days = parseDaysFromString(combined);
            AttendanceResult result = attendanceSummary(days);
            System.out.println(result);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNext()) {
            System.out.println("No input provided. Running Sample 1 default:");
            int[] sample = {1, 1, 0, 1, 1, 1, 0, 1};
            System.out.println(attendanceSummary(sample));
            scanner.close();
            return;
        }

        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine()).append(" ");
        }
        scanner.close();

        int[] days = parseDaysFromString(sb.toString());
        AttendanceResult result = attendanceSummary(days);
        System.out.println(result);
    }
}
