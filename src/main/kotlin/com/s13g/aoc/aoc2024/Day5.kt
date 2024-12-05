package com.s13g.aoc.aoc2024

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 5: Print Queue ---
 * https://adventofcode.com/2024/day/5
 */
class Day5 : Solver {
  override fun solve(lines: List<String>): Result {
    val rules = mutableListOf<Pair<Int, Int>>()
    val updates = mutableListOf<List<Int>>()

    var result1 = 0
    var result2 = 0
    for (line in lines) {
      if (line.contains('|')) rules.add(line.split("|").map { it.toInt() }
        .zipWithNext().first())
      else if (line.isNotBlank()) updates.add(
        line.split(',').map { it.toInt() })
    }

    for (update in updates) {
      val sorted = update.sortedWith { o1, o2 ->
        (if (Pair(o1, o2) in rules) -1
        else if (Pair(o2, o1) in rules) 1
        else 0)
      }
      if (sorted == update) result1 += update[update.size / 2] // Already sorted
      else result2 += sorted[sorted.size / 2] // Wasn't sorted but it's now
    }
    return Result("$result1", "$result2")
  }
}
