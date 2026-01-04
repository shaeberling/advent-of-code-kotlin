package com.s13g.aoc.aoc2025

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.mul
import com.s13g.aoc.resultFrom

/**
 * --- Day 7: Laboratories ---
 * https://adventofcode.com/2025/day/7
 */
class Day7 : Solver{
  override fun solve(lines: List<String>): Result {
    val startX = lines[0].indexOf('S')
    val currBeamXs = mutableMapOf<Int, Long>()
    currBeamXs[startX] = 1
    var splitNum = 0
    for (l in 1 .. lines.lastIndex) {
      for (b in currBeamXs.toMap()) {
        if (lines[l][b.key] == '^') {
          splitNum++
          currBeamXs.remove(b.key)
          if (!currBeamXs.contains(b.key-1)) {
            currBeamXs[b.key-1] = b.value
          } else {
            currBeamXs[b.key-1] = currBeamXs[b.key-1]!! * 2
          }
          if (!currBeamXs.contains(b.key+1)) {
            currBeamXs[b.key+1] = b.value
          } else {
            currBeamXs[b.key+1] = currBeamXs[b.key+1]!! * 2
          }
        }
      }
    }

    val resultB = currBeamXs.map { it.value }.sum()

    // too low: 16616742927488
    return resultFrom(splitNum, resultB)
  }
}