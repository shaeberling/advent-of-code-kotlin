package com.s13g.aoc.aoc2025

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.resultFrom

/**
 * --- Day 4: Printing Department ---
 * https://adventofcode.com/2025/day/4
 */
class Day4 : Solver {
  private fun List<String>.isTp(x: Int, y: Int): Boolean {
    if (y < 0 || x < 0 || y >= this.size || x >= this[y].length) return false
    return this[y][x] == '@'
  }

  override fun solve(lines: List<String>): Result {
    var countA = 0
    val removedRolls = mutableSetOf<Pair<Int, Int>>()

    do {
      val newlyRemovedRolls = mutableSetOf<Pair<Int, Int>>()
      for (y in 0 until lines.size) {
        for (x in 0 until lines[y].length) {
          val p = Pair(x, y)
          if (!lines.isTp(x, y) || p in removedRolls) continue
          if (lines.countAdjacent(
              x,
              y,
              removedRolls
            ) < 5
          ) newlyRemovedRolls.add(p)
        }
      }
      if (countA == 0) countA = newlyRemovedRolls.size
      removedRolls.addAll(newlyRemovedRolls)
    } while (newlyRemovedRolls.isNotEmpty())
    return resultFrom(countA, removedRolls.size)
  }

  private fun List<String>.countAdjacent(
    x: Int,
    y: Int,
    removedRolls: Set<Pair<Int, Int>>
  ): Int {
    var count = 0
    for (yy in y - 1..y + 1) {
      for (xx in x - 1..x + 1) {
        if (this.isTp(xx, yy) && Pair(xx, yy) !in removedRolls) count++
      }
    }
    return count
  }
}