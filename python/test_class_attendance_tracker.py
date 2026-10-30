import os
import sys
import unittest

sys.path.insert(0, os.path.dirname(__file__))

from class_attendance_tracker import (
    attendance_summary,
    format_output,
    parse_days_from_string
)


class TestClassAttendanceTracker(unittest.TestCase):

    def test_sample_1(self):
        days = [1, 1, 0, 1, 1, 1, 0, 1]
        present, streak = attendance_summary(days)
        self.assertEqual(present, 6)
        self.assertEqual(streak, 3)
        self.assertEqual(format_output(present, streak), "Present: 6, Longest streak: 3")

    def test_sample_2(self):
        days = [0, 0, 0]
        present, streak = attendance_summary(days)
        self.assertEqual(present, 0)
        self.assertEqual(streak, 0)
        self.assertEqual(format_output(present, streak), "Present: 0, Longest streak: 0")

    def test_all_present(self):
        days = [1, 1, 1, 1, 1]
        present, streak = attendance_summary(days)
        self.assertEqual(present, 5)
        self.assertEqual(streak, 5)

    def test_single_day_present(self):
        days = [1]
        present, streak = attendance_summary(days)
        self.assertEqual(present, 1)
        self.assertEqual(streak, 1)

    def test_single_day_absent(self):
        days = [0]
        present, streak = attendance_summary(days)
        self.assertEqual(present, 0)
        self.assertEqual(streak, 0)

    def test_alternating_days(self):
        days = [1, 0, 1, 0, 1, 0, 1]
        present, streak = attendance_summary(days)
        self.assertEqual(present, 4)
        self.assertEqual(streak, 1)

    def test_streak_at_beginning(self):
        days = [1, 1, 1, 1, 0, 0, 1, 1]
        present, streak = attendance_summary(days)
        self.assertEqual(present, 6)
        self.assertEqual(streak, 4)

    def test_streak_at_end(self):
        days = [0, 0, 1, 1, 0, 1, 1, 1, 1, 1]
        present, streak = attendance_summary(days)
        self.assertEqual(present, 7)
        self.assertEqual(streak, 5)

    def test_streak_in_middle(self):
        days = [0, 1, 0, 1, 1, 1, 1, 0, 1, 0]
        present, streak = attendance_summary(days)
        self.assertEqual(present, 6)
        self.assertEqual(streak, 4)

    def test_multiple_tied_longest_streaks(self):
        days = [1, 1, 0, 1, 1, 0, 1, 1]
        present, streak = attendance_summary(days)
        self.assertEqual(present, 6)
        self.assertEqual(streak, 2)

    def test_max_constraint_all_present(self):
        days = [1] * 100
        present, streak = attendance_summary(days)
        self.assertEqual(present, 100)
        self.assertEqual(streak, 100)

    def test_max_constraint_with_breaks(self):
        days = [1] * 50 + [0] + [1] * 49
        present, streak = attendance_summary(days)
        self.assertEqual(present, 99)
        self.assertEqual(streak, 50)

    def test_string_parser_bracket_format(self):
        raw = "[1, 1, 0, 1, 1, 1, 0, 1]"
        days = parse_days_from_string(raw)
        present, streak = attendance_summary(days)
        self.assertEqual(present, 6)
        self.assertEqual(streak, 3)

    def test_string_parser_space_separated(self):
        raw = "1 1 0 1 1 1 0 1"
        days = parse_days_from_string(raw)
        present, streak = attendance_summary(days)
        self.assertEqual(present, 6)
        self.assertEqual(streak, 3)

    def test_invalid_inputs(self):
        with self.assertRaises(ValueError):
            attendance_summary(None)

        with self.assertRaises(ValueError):
            attendance_summary([])

        with self.assertRaises(ValueError):
            attendance_summary([1, 2, 0])

        with self.assertRaises(ValueError):
            attendance_summary([1, -1, 0])

        with self.assertRaises(ValueError):
            attendance_summary([1] * 101)


if __name__ == "__main__":
    unittest.main()
