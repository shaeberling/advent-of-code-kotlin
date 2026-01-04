package com.s13g.aoc.aoc2025

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 2: Gift Shop ---
 * https://adventofcode.com/2025/day/2
 */
class Day2 : Solver{

  private data class Range(val start: Long, val end: Long)
  fun Int.even() = this % 2 == 0

  override fun solve(lines: List<String>): Result {
    val ranges = lines[0].split(",").map {it.split('-').let { Range(it[0].toLong(), it[1].toLong()) } }
    val resultA = ranges.map { getInvalidIds(it, ::isInvalidIdA) }.flatten().sum()
    val resultB = ranges.map { getInvalidIds(it, ::isInvalidIdB) }.flatten().sum()
    return resultFrom(resultA, resultB)
  }

  private fun getInvalidIds(range: Range, isInvalid: (Long) -> (Boolean)): Set<Long> {
    val result = mutableSetOf<Long>()
    for (n in range.start..range.end) {
      if (isInvalid(n)) result.add(n)
    }
    return result
  }
  private fun isInvalidIdB(n: Long): Boolean {
    val nStr = n.toString()
    for (l in 1..nStr.length/2) {
      if (nStr.length % l > 0) continue
      val slice = nStr.take(l)

      var newStr = ""
      for (n in 0 until nStr.length / l) {
        newStr += slice
      }
      if (newStr == nStr) return true
    }
    return false
  }

  private fun isInvalidIdA(n: Long): Boolean {
    val nStr = n.toString()
    if (!nStr.length.even()) return false

    val firstHalf = nStr.take(nStr.length / 2)
    return (firstHalf == nStr.substring(nStr.length / 2))

  }
}