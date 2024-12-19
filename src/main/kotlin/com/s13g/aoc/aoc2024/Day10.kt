package com.s13g.aoc.aoc2024

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY

/**
 * --- Day 10: Hoof It ---
 * https://adventofcode.com/2024/day/10
 */
class Day10 : Solver {
  override fun solve(map: List<String>): Result {
    var result1 = 0
    var result2 = 0
    for (y in 0 until map.size) {
      for (x in 0 until map[0].length) {
        if (map.at(x, y) == 0) {
          val locs = stepAndGetNines(x, y, 0, map)
          result1 += locs.toSet().size
          result2 += locs.size
        }
      }
    }
    return Result("$result1", "$result2")
  }

  fun stepAndGetNines(x: Int, y: Int, check: Int, map: List<String>): List<XY> {
    if (check != map.at(x, y)) return emptyList()
    if (check == 9) return listOf(XY(x, y))

    val result = mutableListOf<XY>()
    result.addAll(stepAndGetNines(x - 1, y, check + 1, map))
    result.addAll(stepAndGetNines(x, y - 1, check + 1, map))
    result.addAll(stepAndGetNines(x + 1, y, check + 1, map))
    result.addAll(stepAndGetNines(x, y + 1, check + 1, map))
    return result
  }

  private fun List<String>.at(x: Int, y: Int) =
    if (y < 0 || y > this.lastIndex) Int.MIN_VALUE
    else if (x < 0 || x > this[0].lastIndex) Int.MIN_VALUE
    else this[y][x].digitToInt()
}
