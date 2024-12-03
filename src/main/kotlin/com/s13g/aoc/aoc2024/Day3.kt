package com.s13g.aoc.aoc2024

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 3: Mull It Over ---
 * https://adventofcode.com/2024/day/3
 */
class Day3 : Solver {
  override fun solve(lines: List<String>): Result {
    val re = """^(\d+),(\d+)\)""".toRegex()
    var result1 = 0
    var result2 = 0

    var prevCan = ""
    var enabled = true
    for (line in lines) {
      val candidates = line.split("mul(")
      for (can in candidates) {
        val doOrDont = prevCan.indexOf("don't()") - prevCan.indexOf("do()")
        if (doOrDont != 0) enabled = doOrDont < 0
        val matchResult = re.find(can)
        if (matchResult != null) {
          val (n1, n2) = matchResult.destructured
          result1 += n1.toInt() * n2.toInt()
          if (enabled) result2 += n1.toInt() * n2.toInt()
        }
        prevCan = can
      }
    }
    return Result("$result1", "$result2")
  }
}