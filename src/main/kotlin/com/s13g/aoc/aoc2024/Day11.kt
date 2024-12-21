package com.s13g.aoc.aoc2024

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 11: Plutonian Pebbles ---
 * https://adventofcode.com/2024/day/11
 */
class Day11 : Solver {
  override fun solve(lines: List<String>): Result {
    // Map stone number to the number of occurrences of that stone.
    val stones = lines[0].split(" ").groupingBy { it }.eachCount()
      .map { it.key to it.value.toLong() }.toMap()

    val stones25 = iterate25(stones, 25)
    val stones75 = iterate25(stones25, 50)
    val result25 = stones25.values.sum()
    var result75 = stones75.values.sum()

    return Result("$result25", "$result75")
  }

  private fun iterate25(
    stonesArg: Map<String, Long>,
    iterations: Int
  ): Map<String, Long> {
    var stones = stonesArg
    for (i in 1..iterations) stones = iterate(stones)
    return stones
  }


  private fun iterate(stones: Map<String, Long>): Map<String, Long> {
    val result = mutableMapOf<String, Long>()
    for (stonekv in stones) {
      val stone = stonekv.key
      val stoneCount = stonekv.value
      if (stone == "0") result.addCount("1", stoneCount)
      else if (stone.length % 2 == 0) {
        result.addCount(stone.substring(0, stone.length / 2), stoneCount)
        val second = stone.substring(stone.length / 2, stone.length)
        result.addCount(second.toLong().toString(), stoneCount)
      } else {
        result.addCount((stone.toLong() * 2024).toString(), stoneCount)
      }
    }
    return result
  }

  private fun MutableMap<String, Long>.addCount(stone: String, n: Long) {
    if (stone !in this) this[stone] = 0L
    this[stone] = this[stone]!! + n
  }
}