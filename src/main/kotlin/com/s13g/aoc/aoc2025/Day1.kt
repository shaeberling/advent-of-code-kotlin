package com.s13g.aoc.aoc2025

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 1: Secret Entrance ---
 * https://adventofcode.com/2025/day/1
 */
class Day1 : Solver{
  private data class Turn(val dir: Int, val num: Int)

  override fun solve(lines: List<String>): Result {
    val turns = lines.map { Turn((if (it[0] == 'L')  - 1 else 1) , it.substring(1).toInt()) }

    var pos = 50
    var countZeroA = 0
    var countZeroB = 0
    for (t in turns) {
      for (x in 0 until t.num) {
        pos += t.dir
        if (pos < 0) pos += 100
        pos %= 100
        if (pos == 0) countZeroB++
      }
      if (pos == 0) countZeroA++
    }
    return resultFrom(countZeroA, countZeroB)
  }
}
