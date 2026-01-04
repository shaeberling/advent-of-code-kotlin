package com.s13g.aoc.aoc2025

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom
import kotlin.math.abs
import kotlin.math.max

/**
 * --- Day 9:  ---
 * https://adventofcode.com/2025/day/9
 */
class Day9 : Solver{
  private data class XY(val x: Long, val y: Long)
  override fun solve(lines: List<String>): Result {
    val redTiles = lines
      .map { line -> line.split(',').map { it.toLong() }}
      .map { XY(it[0], it[1]) }

    var largestArea = 0L
    for (i in 0 .. redTiles.lastIndex) {
      val tile1 = redTiles[i]
      for (j in i .. redTiles.lastIndex) {
        val tile2 = redTiles[j]
        val area = abs(tile1.x - tile2.x+1) * abs(tile1.y - tile2.y+1)
        largestArea = max(largestArea, area)
      }
    }
    return resultFrom(largestArea, 0)
  }
}