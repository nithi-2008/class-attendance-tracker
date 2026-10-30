"""
Problem 6: Class Attendance Tracker

TASK:
A teacher records attendance for one student as an array of 1s (present) and 0s (absent),
one value per day. The teacher wants to know how many days the student was present and
the longest run of days the student attended without a break.

Implement a function attendanceSummary(days) that returns the number of present days
and the length of the longest unbroken run of present days.

CONSTRAINTS:
• 1 <= length of days <= 100
• Each value is 0 or 1

COMPLEXITY REQUIREMENTS:
• Time Complexity:  O(n) in a single pass, where n is length of days
• Auxiliary Space:  O(1) constant extra space
"""

from typing import List, Tuple
import ast
import sys


def attendance_summary(days: List[int]) -> Tuple[int, int]:
    """
    Computes the total number of present days and the longest continuous streak
    of attendance in a single linear pass.

    :param days: List of binary integers (1 for present, 0 for absent)
    :return: Tuple of (present_days, longest_streak)
    :raises ValueError: if days is None, empty, >100 elements, or has invalid values
    """
    if days is None or len(days) == 0:
        raise ValueError("Days list must not be null or empty.")
    if len(days) > 100:
        raise ValueError(f"Days list length ({len(days)}) exceeds maximum constraint of 100.")

    total_present = 0
    current_streak = 0
    longest_streak = 0

    # Single pass traversal: O(n) time, O(1) space
    for day in days:
        if day == 1:
            total_present += 1
            current_streak += 1
            if current_streak > longest_streak:
                longest_streak = current_streak
        elif day == 0:
            current_streak = 0
        else:
            raise ValueError(f"Invalid attendance value '{day}'. Each value must be 0 or 1.")

    return total_present, longest_streak


def format_output(present: int, longest_streak: int) -> str:
    """Formats output according to problem specifications."""
    return f"Present: {present}, Longest streak: {longest_streak}"


def parse_days_from_string(text: str) -> List[int]:
    """
    Parses bracketed representation (e.g. '[1, 1, 0, 1]') or
    comma/space delimited numbers into a list of integers.
    """
    text = text.strip()
    if not text:
        return []

    if text.startswith("[") and text.endswith("]"):
        try:
            parsed = ast.literal_eval(text)
            if isinstance(parsed, list):
                return [int(x) for x in parsed]
        except (ValueError, SyntaxError):
            pass

    # Normalize brackets and commas
    cleaned = text.replace("[", "").replace("]", "").replace(",", " ")
    tokens = cleaned.split()
    return [int(token) for token in tokens]


def main():
    """
    CLI driver supporting command line arguments, stdin piped input,
    or interactive terminal entry.
    """
    if len(sys.argv) > 1:
        raw_input = " ".join(sys.argv[1:])
        days = parse_days_from_string(raw_input)
        present, streak = attendance_summary(days)
        print(format_output(present, streak))
        return

    input_text = sys.stdin.read().strip()
    if not input_text:
        # Default fallback to Sample 1
        sample_days = [1, 1, 0, 1, 1, 1, 0, 1]
        present, streak = attendance_summary(sample_days)
        print(format_output(present, streak))
        return

    days = parse_days_from_string(input_text)
    present, streak = attendance_summary(days)
    print(format_output(present, streak))


if __name__ == "__main__":
    main()
