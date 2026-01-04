package com.s13g.aoc.aoc2025

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 3: Lobby ---
 * https://adventofcode.com/2025/day/3
 */
class Day3 : Solver{
  override fun solve(lines: List<String>): Result {
    val resultA = lines.sumOf { getJoltage(it, 2) }
    val resultB = lines.sumOf { getJoltage(it, 12) }
    return resultFrom(resultA, resultB)
  }

  private fun getJoltage(bank: String, num: Int): Long {
    var resultStr = ""
    var leftEdge = 0
    for (n in 1 .. num) {
      var leftIdx = leftEdge
      // Find the largest number in the remaining space from right-of the last
      // number to the right edge that leaves enough space for the remaining
      // numbers.
      for (i in leftIdx until bank.length  - (num-n)) {
        if (bank[i] > bank[leftIdx]) leftIdx = i
      }
      resultStr += "${bank[leftIdx]}"
      leftEdge = leftIdx + 1
    }
    return resultStr.toLong()
  }
}