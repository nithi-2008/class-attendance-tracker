# Problem 6: Class Attendance Tracker

[![Java](https://img.shields.io/badge/Java-8%2B-ED8B00?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Python](https://img.shields.io/badge/Python-3.8%2B-3776AB?logo=python&logoColor=white)](https://www.python.org/)
[![Tests](https://img.shields.io/badge/Tests-30%20Passed-brightgreen)](tests/ClassAttendanceTrackerTest.java)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Complete, production-grade solutions and automated test suites for **Category C · Problem 6: Class Attendance Tracker** (STEP SEM-3 · CodInClub | Powered by BridgeLabz).

---

## Repository Structure

```text
class-attendance-tracker/
├── .gitignore                          # Ignore rules for Java & Python build artifacts
├── README.md                           # Project overview, usage & CLI documentation
├── SOLUTIONS.md                        # Complete QLA design, single-pass proof & analysis
│
├── src/
│   └── ClassAttendanceTracker.java     # Core Java implementation & interactive CLI
├── tests/
│   └── ClassAttendanceTrackerTest.java # Automated Java unit test suite (15 test cases)
│
└── python/
    ├── class_attendance_tracker.py      # Python reference implementation & CLI
    └── test_class_attendance_tracker.py # Automated Python unit test suite (15 test cases)
```

---

## Problem Summary

A teacher records attendance for one student as an array of `1`s (present) and `0`s (absent), one value per day. The teacher wants to know:
1. How many days the student was **present**?
2. What is the **longest run of days** the student attended without a break (longest streak)?

Implement a function `attendanceSummary(days)` that returns the number of present days and the length of the longest unbroken run of present days.

### Constraints
- $1 \le \text{length of } days \le 100$
- Each value is either `0` or `1`

### Examples

#### Sample 1
```text
days = [1, 1, 0, 1, 1, 1, 0, 1]

Expected Output: Present: 6, Longest streak: 3
```

#### Sample 2
```text
days = [0, 0, 0]

Expected Output: Present: 0, Longest streak: 0
```

### Complexity Requirements
- **Time Complexity:** $\mathcal{O}(n)$ in a **single pass**, where $n$ is the number of days.
- **Auxiliary Space:** $\mathcal{O}(1)$ constant extra space.

---

## How to Compile & Run

### 1. Java

#### Compile Core & Test Suite
```bash
javac -d out src/ClassAttendanceTracker.java tests/ClassAttendanceTrackerTest.java
```

#### Run Automated Test Suite
```bash
java -cp out tests.ClassAttendanceTrackerTest
```

#### Run CLI with Arguments
```bash
java -cp out src.ClassAttendanceTracker 1 1 0 1 1 1 0 1
```

#### Run CLI with Piped Input (Bracket Notation or Whitespace)
```bash
echo "[1, 1, 0, 1, 1, 1, 0, 1]" | java -cp out src.ClassAttendanceTracker
# or
echo "0 0 0" | java -cp out src.ClassAttendanceTracker
```

---

### 2. Python

#### Run Automated Test Suite
```bash
py -m unittest python/test_class_attendance_tracker.py
```

#### Run CLI with Arguments
```bash
py python/class_attendance_tracker.py 1 1 0 1 1 1 0 1
```

#### Run CLI with Piped Input
```bash
echo "[1, 1, 0, 1, 1, 1, 0, 1]" | py python/class_attendance_tracker.py
```

---

## Verification & Test Coverage

Both Java and Python test suites validate **15 test cases** (30 test assertions total):
1. **Sample 1:** Standard assignment example with mixed attendance.
2. **Sample 2:** All absences (`[0, 0, 0]`).
3. **All Present:** Full unbroken attendance (`[1, 1, 1, 1, 1]`).
4. **Single Day Present:** Minimal boundary `[1]`.
5. **Single Day Absent:** Minimal boundary `[0]`.
6. **Alternating Days:** Repeated short streaks of length 1 (`[1, 0, 1, 0, 1]`).
7. **Streak at Beginning:** Longest streak starts at index 0.
8. **Streak at End:** Longest streak finishes at the final index.
9. **Streak in Middle:** Longest streak isolated between absences.
10. **Tied Streaks:** Multiple equal unbroken runs.
11. **Max Constraint (100 days):** All 100 days present.
12. **Max Constraint with Break:** 100 days with an isolated absence.
13. **Bracket String Parser:** Parsing `"[1, 1, 0, 1]"`.
14. **Space-Separated String Parser:** Parsing `"1 1 0 1"`.
15. **Input Validation & Exception Handling:** Enforces array size limits ($1 \le n \le 100$) and binary validity ($d \in \{0, 1\}$).
