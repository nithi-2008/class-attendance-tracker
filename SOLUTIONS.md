# Problem 6: Class Attendance Tracker — Complete Solutions (QLA)

**Category:** Category C · Coding Assignment  
**Program:** STEP SEM-3 · CodInClub | Powered by BridgeLabz  
**Topic:** Linear Arrays, Running Counters, Accumulation & Streak Tracking Algorithms  

---

## Table of Contents
1. [Part A — Problem Statement & Specifications](#part-a--problem-statement--specifications)
2. [Part B — Algorithmic Design & Single-Pass Proof](#part-b--algorithmic-design--single-pass-proof)
   - [Core Algorithm](#core-algorithm)
   - [State Transition Invariants](#state-transition-invariants)
   - [Single-Pass Proof](#single-pass-proof)
3. [Part C — Complexity Analysis](#part-c--complexity-analysis)
   - [Time Complexity](#time-complexity)
   - [Space Complexity](#space-complexity)
   - [Complexity Answering the Prompt Requirement](#complexity-answering-the-prompt-requirement)
4. [Part D — Step-by-Step Execution Trace](#part-d--step-by-step-execution-trace)
   - [Trace for Sample 1](#trace-for-sample-1)
   - [Trace for Sample 2](#trace-for-sample-2)
5. [Part E — Edge Cases & Validation Matrix](#part-e--edge-cases--validation-matrix)
6. [Part F — Source Implementations](#part-f--source-implementations)
   - [Java Implementation](#java-implementation)
   - [Python Implementation](#python-implementation)
7. [Part G — Conceptual Q&A](#part-g--conceptual-qa)

---

# Part A — Problem Statement & Specifications

### Problem Description
A teacher records daily class attendance for a student as an array of binary values:
- `1` represents **Present**
- `0` represents **Absent**

Each element in the array represents exactly one day in chronological sequence. The teacher needs two metrics:
1. The **total number of days** the student was present.
2. The **length of the longest unbroken run** (consecutive streak) of days the student attended without a single break.

### Function Signature
Implement a function:
```text
attendanceSummary(days) -> (presentDays, longestStreak)
```
yielding output formatted as:
```text
Present: <presentDays>, Longest streak: <longestStreak>
```

### Constraints
- $1 \le \text{length of } days \le 100$
- $\text{days}[i] \in \{0, 1\}$ for all valid indices $i$.

### Examples

#### Sample 1
- **Input:** `days = [1, 1, 0, 1, 1, 1, 0, 1]`
- **Evaluation:**
  - Total days with `1`: $1 + 1 + 1 + 1 + 1 + 1 = 6$.
  - Streaks of consecutive `1`s:
    - Days 0–1: length 2
    - Days 3–5: length 3
    - Day 7: length 1
  - Longest streak = $\max(2, 3, 1) = 3$.
- **Expected Output:**
  ```text
  Present: 6, Longest streak: 3
  ```

#### Sample 2
- **Input:** `days = [0, 0, 0]`
- **Evaluation:**
  - Total days with `1`: $0$.
  - Streaks of consecutive `1`s: None.
  - Longest streak = $0$.
- **Expected Output:**
  ```text
  Present: 0, Longest streak: 0
  ```

---

# Part B — Algorithmic Design & Single-Pass Proof

### Core Algorithm
To track both quantities simultaneously without extra memory or multiple iterations, we maintain three integer variables during a single linear sweep across the array:

1. `totalPresent`: An accumulator for all days where the student was marked present (`1`).
2. `currentStreak`: A running streak counter representing the length of the currently active continuous sequence of `1`s.
3. `longestStreak`: The historical high-water mark of `currentStreak`.

```text
Algorithm attendanceSummary(days):
    Input: Array days of length n

    totalPresent <- 0
    currentStreak <- 0
    longestStreak <- 0

    for each day in days do:
        if day == 1 then:
            totalPresent <- totalPresent + 1
            currentStreak <- currentStreak + 1
            if currentStreak > longestStreak then:
                longestStreak <- currentStreak
            end if
        else if day == 0 then:
            currentStreak <- 0
        else:
            throw InvalidValueException
        end if
    end for

    return (totalPresent, longestStreak)
```

### State Transition Invariants
At the end of processing each day $i$ (where $0 \le i < n$):
1. **Total Present Invariant:**
   $$\text{totalPresent} = \sum_{k=0}^{i} \text{days}[k]$$
2. **Current Streak Invariant:**
   If $\text{days}[i] == 0$, $\text{currentStreak} = 0$.  
   If $\text{days}[i] == 1$, $\text{currentStreak}$ equals the number of contiguous `1`s ending at index $i$.
3. **Longest Streak Invariant:**
   $$\text{longestStreak} = \max_{0 \le s \le e \le i} \{ e - s + 1 \mid \forall k \in [s, e], \text{days}[k] = 1 \}$$
   (or $0$ if no such subsegment exists).

### Single-Pass Proof
- **Question:** *Can both answers be found in a single pass?*
- **Proof:**
  - Both properties are **online prefix-computable**:
    - `totalPresent` is purely additive; adding an element requires only inspecting the current value and updating the sum in $\mathcal{O}(1)$.
    - `currentStreak` depends only on the current value $\text{days}[i]$ and the immediate predecessor streak $\text{currentStreak}_{i-1}$.
    - `longestStreak` is monotonic non-decreasing, and each time $\text{currentStreak}$ increments, comparing it to `longestStreak` guarantees that any new maximal streak is immediately recorded before any subsequent `0` resets `currentStreak`.
  - Because no backtracking, lookahead, or secondary traversal is needed, both answers are strictly computed in **one single pass** ($\mathcal{O}(n)$ time).

---

# Part C — Complexity Analysis

### Time Complexity
- The algorithm uses a single `for` loop that iterates from index $0$ to $n - 1$.
- In each iteration, a constant number of basic operations (comparisons, additions, assignments) are performed.
- Number of operations = $k \times n$, where $k$ is a small constant ($k \le 4$).
- **Overall Time Complexity:** $\mathcal{O}(n)$, where $n$ is the length of `days`.
- Given $1 \le n \le 100$, the loop completes in at most 100 iterations (under $1\ \mu\text{s}$ execution time).

### Space Complexity
- The algorithm allocates only three scalar integer variables:
  - `totalPresent` (4 bytes)
  - `currentStreak` (4 bytes)
  - `longestStreak` (4 bytes)
- No auxiliary arrays, hash sets, or recursion stacks are used.
- **Overall Auxiliary Space Complexity:** $\mathcal{O}(1)$ (strictly constant extra space).

### Complexity Answering the Prompt Requirement
| Metric | Complexity | Notes |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(n)$ | Single linear scan through the array |
| **Auxiliary Space** | $\mathcal{O}(1)$ | Independent of array length $n$ |
| **Single Pass Feasibility** | **Yes** | Both counters updated concurrently |

---

# Part D — Step-by-Step Execution Trace

### Trace for Sample 1
**Input:** `days = [1, 1, 0, 1, 1, 1, 0, 1]` ($n = 8$)

| Step / Index | Value | Action Taken | `totalPresent` | `currentStreak` | `longestStreak` |
| :---: | :---: | :--- | :---: | :---: | :---: |
| *Init* | — | Initialize all to 0 | 0 | 0 | 0 |
| **$i = 0$** | `1` | Present day: increment counters | 1 | 1 | 1 |
| **$i = 1$** | `1` | Present day: increment counters | 2 | 2 | 2 |
| **$i = 2$** | `0` | Absent day: reset streak to 0 | 2 | 0 | 2 |
| **$i = 3$** | `1` | Present day: new streak starts | 3 | 1 | 2 |
| **$i = 4$** | `1` | Present day: increment streak | 4 | 2 | 2 |
| **$i = 5$** | `1` | Present day: streak exceeds max | 5 | 3 | **3** |
| **$i = 6$** | `0` | Absent day: reset streak to 0 | 5 | 0 | 3 |
| **$i = 7$** | `1` | Present day: streak starts | **6** | 1 | **3** |

**Final Result:** `Present: 6, Longest streak: 3`

---

### Trace for Sample 2
**Input:** `days = [0, 0, 0]` ($n = 3$)

| Step / Index | Value | Action Taken | `totalPresent` | `currentStreak` | `longestStreak` |
| :---: | :---: | :--- | :---: | :---: | :---: |
| *Init* | — | Initialize all to 0 | 0 | 0 | 0 |
| **$i = 0$** | `0` | Absent day: reset streak | 0 | 0 | 0 |
| **$i = 1$** | `0` | Absent day: reset streak | 0 | 0 | 0 |
| **$i = 2$** | `0` | Absent day: reset streak | 0 | 0 | 0 |

**Final Result:** `Present: 0, Longest streak: 0`

---

# Part E — Edge Cases & Validation Matrix

| Category | Test Input | Expected Output | Rationale & Invariant Verified |
| :--- | :--- | :--- | :--- |
| **Sample 1** | `[1, 1, 0, 1, 1, 1, 0, 1]` | `Present: 6, Longest streak: 3` | Multiple runs of varying lengths |
| **Sample 2** | `[0, 0, 0]` | `Present: 0, Longest streak: 0` | Student never attended; zero streak |
| **All Present** | `[1, 1, 1, 1, 1]` | `Present: 5, Longest streak: 5` | No resets occur; full run equal to length |
| **Single Day (1)** | `[1]` | `Present: 1, Longest streak: 1` | Minimal constraint boundary |
| **Single Day (0)** | `[0]` | `Present: 0, Longest streak: 0` | Minimal constraint boundary |
| **Alternating** | `[1, 0, 1, 0, 1, 0, 1]` | `Present: 4, Longest streak: 1` | Resets after every single attendance |
| **Streak at Start**| `[1, 1, 1, 1, 0, 0, 1]` | `Present: 5, Longest streak: 4` | Peak occurs prior to subsequent zeros |
| **Streak at End** | `[0, 0, 1, 1, 1, 1, 1]` | `Present: 5, Longest streak: 5` | Peak active through end of array |
| **Tied Peaks** | `[1, 1, 0, 1, 1, 0, 1]` | `Present: 5, Longest streak: 2` | Multiple streaks of identical maximal size |
| **Max Size (100)**| `[1, 1, ..., 1]` (100 1s) | `Present: 100, Longest streak: 100` | Upper limit of constraint $n = 100$ |
| **Break at 50** | 50 `1`s, one `0`, 49 `1`s | `Present: 99, Longest streak: 50` | Streak correctly identified before break |
| **Invalid Value** | `[1, 2, 0]` | Throws Exception | Guards against out-of-domain values |
| **Empty Input** | `[]` | Throws Exception | Enforces $1 \le \text{length} \le 100$ |

---

# Part F — Source Implementations

### Java Implementation

```java
package src;

public class ClassAttendanceTracker {

    public static class AttendanceResult {
        private final int presentDays;
        private final int longestStreak;

        public AttendanceResult(int presentDays, int longestStreak) {
            this.presentDays = presentDays;
            this.longestStreak = longestStreak;
        }

        public int getPresentDays() { return presentDays; }
        public int getLongestStreak() { return longestStreak; }

        @Override
        public String toString() {
            return "Present: " + presentDays + ", Longest streak: " + longestStreak;
        }
    }

    public static AttendanceResult attendanceSummary(int[] days) {
        if (days == null || days.length == 0) {
            throw new IllegalArgumentException("Days array must not be null or empty.");
        }
        if (days.length > 100) {
            throw new IllegalArgumentException("Days array exceeds constraint length 100.");
        }

        int totalPresent = 0;
        int currentStreak = 0;
        int longestStreak = 0;

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
                throw new IllegalArgumentException("Invalid attendance value: " + day);
            }
        }

        return new AttendanceResult(totalPresent, longestStreak);
    }
}
```

### Python Implementation

```python
from typing import List, Tuple

def attendance_summary(days: List[int]) -> Tuple[int, int]:
    if not days:
        raise ValueError("Days list must not be empty.")
    if len(days) > 100:
        raise ValueError("Days list exceeds maximum constraint of 100.")

    total_present = 0
    current_streak = 0
    longest_streak = 0

    for day in days:
        if day == 1:
            total_present += 1
            current_streak += 1
            if current_streak > longest_streak:
                longest_streak = current_streak
        elif day == 0:
            current_streak = 0
        else:
            raise ValueError(f"Invalid attendance value: {day}")

    return total_present, longest_streak
```

---

# Part G — Conceptual Q&A

### Q1: Why do we update `longestStreak` inside the `day == 1` branch instead of at array termination or at zeros?
**Answer:**
Updating `longestStreak` incrementally when `day == 1` has two major advantages:
1. It eliminates the need for post-loop cleanup. If the longest streak continues all the way to the final element of the array (e.g. `[0, 1, 1, 1]`), an algorithm that only checks streak maximums when encountering a `0` would fail unless an additional check is performed after the loop terminates.
2. It makes the algorithm strictly **streaming/online**, meaning the state at any point represents the exact truth for the prefix processed so far.

### Q2: What is the difference between Kadane's Algorithm and the Streak Tracking Algorithm?
**Answer:**
- **Kadane's Algorithm** finds the maximum sum subarray in an array of arbitrary numbers (including negative numbers), resetting the running sum to zero when it becomes negative ($\max(0, \text{currentSum} + x)$).
- **Streak Tracking** is a specialized consecutive-count algorithm where elements are binary. The reset condition is triggered specifically by the discrete boundary value `0`, while the growth is strictly monotonic (+1 per present day). Both share the same single-pass $\mathcal{O}(n)$ time and $\mathcal{O}(1)$ space properties.

### Q3: How would the algorithm scale if attendance records contained millions of days?
**Answer:**
Because the algorithm operates in $\mathcal{O}(n)$ time and $\mathcal{O}(1)$ auxiliary space, it processes elements sequentially with cache locality $\mathcal{O}(1)$ cache misses per cache line. It can easily process $10^8$ entries in under 100 milliseconds without consuming additional memory.
