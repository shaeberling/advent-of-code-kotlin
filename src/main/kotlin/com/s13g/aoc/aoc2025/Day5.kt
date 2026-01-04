package com.s13g.aoc.aoc2025

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom
import kotlin.math.max

/**
 * --- Day 5: Cafeteria ---
 * https://adventofcode.com/2025/day/5
 */
class Day5 : Solver {
  private data class Range(val start: Long, val end: Long)

  private fun Range.isWithin(v: Long) = v in start..end

  override fun solve(lines: List<String>): Result {
    val (freshRanges, available) = parseDatabase(lines)

    val freshA =
      available.map { ingr -> freshRanges.count { it.isWithin(ingr) } }
        .map { if (it > 0) 1 else 0 }.sum()

    val rangesB = mutableSetOf<Range>()
    val sortedFreshRanges = freshRanges.sortedBy { it.start }

    var curr = sortedFreshRanges.first()
    for (r in sortedFreshRanges.drop(0)) {
      val overlap = r.start >= curr.start && r.start <= curr.end
      if (!overlap) {
        rangesB.add(curr)
        curr = r
      } else {
        curr = Range(curr.start, max(curr.end, r.end))
      }
    }
    rangesB.add(curr)
    val totalB = rangesB.sumOf { it.end - it.start + 1 }
    return resultFrom(freshA, totalB)
  }

  private fun parseDatabase(lines: List<String>): Pair<List<Range>, Set<Long>> {
    val ranges = mutableListOf<Range>()
    val available = mutableSetOf<Long>()
    for (line in lines) {
      if (line.contains('-')) {
        val split = line.split('-')
        ranges.add(Range(split[0].toLong(), split[1].toLong()))
      } else if (!line.trim().isEmpty()) {
        available.add(line.toLong())
      }
    }
    return ranges to available
  }
}