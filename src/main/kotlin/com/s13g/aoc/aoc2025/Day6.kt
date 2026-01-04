package com.s13g.aoc.aoc2025

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 6: Trash Compactor ---
 * https://adventofcode.com/2025/day/6
 */
class Day6 : Solver{
  override fun solve(lines: List<String>): Result {
    val problems = parseProblems(lines)
    println("Part one: ${problems}")

    var sumA = 0L
    for (problem in problems) {
      val nums = problem.first
      val op = problem.second
      var result = 0L
      for (i in 0 until nums.size) {
        if (i == 0) {
          result = nums[i]
        } else {
          if (op == '*') result *= nums[i]
          if (op == '+') result += nums[i]
        }
      }
      sumA += result
    }

    var sumB = 0L
    val maxCol = lines.maxOfOrNull { it.count() } ?: 0
    var currCalc = 0L
    var op = ' '
    for (c in 0 until maxCol) {
      var numStr = ""
      for (n in 0 until lines.size -1) {
        if (lines[n][c] != ' ') numStr += lines[n][c]
      }
      if (lines.last().length > c && lines.last()[c] != ' ') {
        sumB += currCalc
        op = lines.last()[c]
        currCalc = numStr.toLong()
      } else {
        if (numStr.isEmpty()) continue
        if (op == '+') currCalc += numStr.toLong()
        if (op == '*') currCalc *= numStr.toLong()
      }
    }
    sumB += currCalc

    return resultFrom(sumA, sumB)
  }

  private fun parseProblems(lines: List<String>): List<Pair<List<Long>, Char>> {
    val ops = lines.last().split("\\s+".toRegex()).filter { it.isNotBlank() }
    val num = ops.size
    println("Num problems: $num")

    val problems = mutableListOf<Pair<MutableList<Long>, Char>>()
    for (x in 0 until num) {
      problems.add(Pair(mutableListOf(), ops[x][0]))
    }

    for(line in lines) {
      if (line == lines.last()) continue
      val split = line.split("\\s+".toRegex()).filter { it.isNotBlank() }
      for (i in 0 until num) {
        problems[i].first.add(split[i].toLong())
      }
    }

    return problems
  }
}