package tests;

import src.ClassAttendanceTracker;
import src.ClassAttendanceTracker.AttendanceResult;

import java.util.Arrays;

/**
 * Automated Test Suite for Problem 6: Class Attendance Tracker.
 * Validates correctness, edge cases, single-pass logic, and constraint limits.
 */
public class ClassAttendanceTrackerTest {

    private static int totalPassed = 0;
    private static int totalFailed = 0;

    private static void assertEquals(String testName, Object expected, Object actual) {
        if (expected == null && actual == null) {
            System.out.println("  [PASS] " + testName);
            totalPassed++;
        } else if (expected != null && expected.equals(actual)) {
            System.out.println("  [PASS] " + testName);
            totalPassed++;
        } else {
            System.err.println("  [FAIL] " + testName + " | Expected: " + expected + ", Got: " + actual);
            totalFailed++;
        }
    }

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("  [PASS] " + testName);
            totalPassed++;
        } else {
            System.err.println("  [FAIL] " + testName + " | Condition evaluated to false");
            totalFailed++;
        }
    }

    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println(" Running Test Suite: Class Attendance Tracker (Problem 6)");
        System.out.println("=========================================================");

        testSample1();
        testSample2();
        testAllPresent();
        testSingleDayPresent();
        testSingleDayAbsent();
        testAlternatingDays();
        testStreakAtBeginning();
        testStreakAtEnd();
        testStreakInMiddle();
        testMultipleTiedLongestStreaks();
        testMaxConstraintAllPresent();
        testMaxConstraintWithBreaks();
        testStringParserBracketFormat();
        testStringParserSpaceSeparated();
        testInvalidInputs();

        System.out.println("\n---------------------------------------------------------");
        System.out.println("Test Results: " + totalPassed + " passed, " + totalFailed + " failed.");
        System.out.println("=========================================================");

        if (totalFailed > 0) {
            System.exit(1);
        }
    }

    private static void testSample1() {
        System.out.println("\n--- Test 1: Sample 1 from Assignment ---");
        int[] days = {1, 1, 0, 1, 1, 1, 0, 1};
        AttendanceResult result = ClassAttendanceTracker.attendanceSummary(days);
        assertEquals("Sample 1 present days", 6, result.getPresentDays());
        assertEquals("Sample 1 longest streak", 3, result.getLongestStreak());
        assertEquals("Sample 1 formatted string", "Present: 6, Longest streak: 3", result.toString());
    }

    private static void testSample2() {
        System.out.println("\n--- Test 2: Sample 2 from Assignment (All Absences) ---");
        int[] days = {0, 0, 0};
        AttendanceResult result = ClassAttendanceTracker.attendanceSummary(days);
        assertEquals("Sample 2 present days", 0, result.getPresentDays());
        assertEquals("Sample 2 longest streak", 0, result.getLongestStreak());
        assertEquals("Sample 2 formatted string", "Present: 0, Longest streak: 0", result.toString());
    }

    private static void testAllPresent() {
        System.out.println("\n--- Test 3: All Days Present ---");
        int[] days = {1, 1, 1, 1, 1};
        AttendanceResult result = ClassAttendanceTracker.attendanceSummary(days);
        assertEquals("All present count", 5, result.getPresentDays());
        assertEquals("All present streak", 5, result.getLongestStreak());
    }

    private static void testSingleDayPresent() {
        System.out.println("\n--- Test 4: Single Day Present ---");
        int[] days = {1};
        AttendanceResult result = ClassAttendanceTracker.attendanceSummary(days);
        assertEquals("Single day present count", 1, result.getPresentDays());
        assertEquals("Single day present streak", 1, result.getLongestStreak());
    }

    private static void testSingleDayAbsent() {
        System.out.println("\n--- Test 5: Single Day Absent ---");
        int[] days = {0};
        AttendanceResult result = ClassAttendanceTracker.attendanceSummary(days);
        assertEquals("Single day absent count", 0, result.getPresentDays());
        assertEquals("Single day absent streak", 0, result.getLongestStreak());
    }

    private static void testAlternatingDays() {
        System.out.println("\n--- Test 6: Alternating Present and Absent ---");
        int[] days = {1, 0, 1, 0, 1, 0, 1};
        AttendanceResult result = ClassAttendanceTracker.attendanceSummary(days);
        assertEquals("Alternating present count", 4, result.getPresentDays());
        assertEquals("Alternating longest streak", 1, result.getLongestStreak());
    }

    private static void testStreakAtBeginning() {
        System.out.println("\n--- Test 7: Longest Streak at the Beginning ---");
        int[] days = {1, 1, 1, 1, 0, 0, 1, 1};
        AttendanceResult result = ClassAttendanceTracker.attendanceSummary(days);
        assertEquals("Streak at start count", 6, result.getPresentDays());
        assertEquals("Streak at start streak", 4, result.getLongestStreak());
    }

    private static void testStreakAtEnd() {
        System.out.println("\n--- Test 8: Longest Streak at the End ---");
        int[] days = {0, 0, 1, 1, 0, 1, 1, 1, 1, 1};
        AttendanceResult result = ClassAttendanceTracker.attendanceSummary(days);
        assertEquals("Streak at end count", 7, result.getPresentDays());
        assertEquals("Streak at end streak", 5, result.getLongestStreak());
    }

    private static void testStreakInMiddle() {
        System.out.println("\n--- Test 9: Longest Streak in the Middle ---");
        int[] days = {0, 1, 0, 1, 1, 1, 1, 0, 1, 0};
        AttendanceResult result = ClassAttendanceTracker.attendanceSummary(days);
        assertEquals("Streak in middle count", 6, result.getPresentDays());
        assertEquals("Streak in middle streak", 4, result.getLongestStreak());
    }

    private static void testMultipleTiedLongestStreaks() {
        System.out.println("\n--- Test 10: Multiple Equal Longest Streaks ---");
        int[] days = {1, 1, 0, 1, 1, 0, 1, 1};
        AttendanceResult result = ClassAttendanceTracker.attendanceSummary(days);
        assertEquals("Tied streaks present count", 6, result.getPresentDays());
        assertEquals("Tied streaks longest streak", 2, result.getLongestStreak());
    }

    private static void testMaxConstraintAllPresent() {
        System.out.println("\n--- Test 11: Maximum Constraint (100 Days All Present) ---");
        int[] days = new int[100];
        Arrays.fill(days, 1);
        AttendanceResult result = ClassAttendanceTracker.attendanceSummary(days);
        assertEquals("100 days present count", 100, result.getPresentDays());
        assertEquals("100 days longest streak", 100, result.getLongestStreak());
    }

    private static void testMaxConstraintWithBreaks() {
        System.out.println("\n--- Test 12: Maximum Constraint (100 Days with Break) ---");
        int[] days = new int[100];
        // 50 ones, 1 zero at index 50, 49 ones
        for (int i = 0; i < 50; i++) days[i] = 1;
        days[50] = 0;
        for (int i = 51; i < 100; i++) days[i] = 1;

        AttendanceResult result = ClassAttendanceTracker.attendanceSummary(days);
        assertEquals("50+49 present count", 99, result.getPresentDays());
        assertEquals("Longest streak 50", 50, result.getLongestStreak());
    }

    private static void testStringParserBracketFormat() {
        System.out.println("\n--- Test 13: String Parser with Bracket Format ---");
        String input = "[1, 1, 0, 1, 1, 1, 0, 1]";
        int[] parsed = ClassAttendanceTracker.parseDaysFromString(input);
        AttendanceResult result = ClassAttendanceTracker.attendanceSummary(parsed);
        assertEquals("Parsed bracket present count", 6, result.getPresentDays());
        assertEquals("Parsed bracket streak", 3, result.getLongestStreak());
    }

    private static void testStringParserSpaceSeparated() {
        System.out.println("\n--- Test 14: String Parser with Space Delimited Format ---");
        String input = "1 1 0 1 1 1 0 1";
        int[] parsed = ClassAttendanceTracker.parseDaysFromString(input);
        AttendanceResult result = ClassAttendanceTracker.attendanceSummary(parsed);
        assertEquals("Parsed space delimited present count", 6, result.getPresentDays());
        assertEquals("Parsed space delimited streak", 3, result.getLongestStreak());
    }

    private static void testInvalidInputs() {
        System.out.println("\n--- Test 15: Error Handling & Validation ---");
        boolean nullCaught = false;
        try {
            ClassAttendanceTracker.attendanceSummary(null);
        } catch (IllegalArgumentException e) {
            nullCaught = true;
        }
        assertTrue("Null array throws IllegalArgumentException", nullCaught);

        boolean emptyCaught = false;
        try {
            ClassAttendanceTracker.attendanceSummary(new int[0]);
        } catch (IllegalArgumentException e) {
            emptyCaught = true;
        }
        assertTrue("Empty array throws IllegalArgumentException", emptyCaught);

        boolean invalidValCaught = false;
        try {
            ClassAttendanceTracker.attendanceSummary(new int[]{1, 2, 0});
        } catch (IllegalArgumentException e) {
            invalidValCaught = true;
        }
        assertTrue("Invalid value 2 throws IllegalArgumentException", invalidValCaught);

        boolean negativeValCaught = false;
        try {
            ClassAttendanceTracker.attendanceSummary(new int[]{1, -1, 0});
        } catch (IllegalArgumentException e) {
            negativeValCaught = true;
        }
        assertTrue("Negative value -1 throws IllegalArgumentException", negativeValCaught);

        boolean overMaxConstraintCaught = false;
        try {
            ClassAttendanceTracker.attendanceSummary(new int[101]);
        } catch (IllegalArgumentException e) {
            overMaxConstraintCaught = true;
        }
        assertTrue("Array length 101 throws IllegalArgumentException", overMaxConstraintCaught);
    }
}
