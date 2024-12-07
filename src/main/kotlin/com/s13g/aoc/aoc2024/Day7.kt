package com.s13g.aoc.aoc2024

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 7: Bridge Repair ---
 * https://adventofcode.com/2024/day/7
 */
class Day7 : Solver {
  override fun solve(lines: List<String>): Result {
    val parsed = lines.map { it.split(": ") }
      .map { it[0].toLong() to it[1].split(" ").map { it.toLong() } }
    var result1 = parsed.sumOf {
      if (isValid(it.first, it.second, part2 = false)) it.first else 0
    }
    var result2 = parsed.sumOf {
      if (isValid(it.first, it.second, part2 = true)) it.first else 0
    }
    return Result("$result1", "$result2")
  }

  private fun isValid(want: Long, nums: List<Long>, part2: Boolean): Boolean {
    // If we have reduced the equation to a single number, check if it matches
    // the number we want. We're done.
    if (nums.size == 1) return nums.first() == want

    fun runNewValue(newValue: Long): Boolean {
      val newNumsAdd = mutableListOf<Long>()
      newNumsAdd.add(newValue)
      newNumsAdd.addAll(nums.subList(2, nums.size))
      return isValid(want, newNumsAdd, part2)
    }
    // If we have more than one number, run the remainder of the digits through
    // all the operation options recursively while reducing the list.

    val results = mutableListOf<Boolean>()
    // Option 1: Do an addition
    if (runNewValue(nums[0] + nums[1])) return true
    // Option 2: Do a multiplication
    if (runNewValue(nums[0] * nums[1])) return true
    // Option 3: For part 2, do a concatenation.
    if (part2) {
      // Left-shift left operand depending on the size of the right operand.
      var newValueCon = nums[0] * 10L
      var rightVal = nums[1]
      while (rightVal >= 10) {
        newValueCon *= 10L
        rightVal /= 10
      }
      if (runNewValue(newValueCon + nums[1])) return true
    }
    return false
  }
}