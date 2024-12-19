package com.s13g.aoc.aoc2024

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 19: Linen Layout ---
 * https://adventofcode.com/2024/day/19
 */
class Day19 : Solver {
  override fun solve(lines: List<String>): Result {
    val avail = lines[0].split(",").map { it.trim() }
    val desired = lines.subList(2, lines.size)

    val result1 = desired.count { countVariation(it, avail) > 0 }
    val result2 = desired.sumOf { countVariation(it, avail) }
    return Result("$result1", "$result2")
  }

  val possibleCache = mutableMapOf<String, Long>()
  private fun countVariation(pattern: String, avail: List<String>): Long {
    if (pattern in possibleCache) return possibleCache[pattern]!!

    var variations = 0L
    for (a in avail.toSet()) {
      if (a == pattern) variations++
      if (pattern.startsWith(a)) {
        val subPattern = pattern.substring(a.length)
        variations += countVariation(subPattern, avail)
      }
    }
    possibleCache[pattern] = variations
    return variations
  }
}