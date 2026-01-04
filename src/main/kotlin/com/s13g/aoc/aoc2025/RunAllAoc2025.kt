package com.s13g.aoc.aoc2025

import com.s13g.aoc.PuzzleRunner


private const val ONLY_RUN_NEW = true

/**
 * ---- Advent of Code 2025 ----
 * https://adventofcode.com/2025
 */
fun main() {
  val runner = PuzzleRunner(ONLY_RUN_NEW, 2025)
  runner.addProblem(1, Day1(), "1141", "6634")
  runner.addProblem(2, Day2(), "18595663903", "19058204438")
  runner.addProblem(3, Day3(), "17412", "172681562473501")
  runner.addProblem(4, Day4(), "1540", "8972")
  runner.addProblem(5, Day5(), "525", "333892124923577")
  runner.addProblem(6, Day6(), "6957525317641", "13215665360076")
//  runner.addProblem(7, Day7(), "1560", "")
  runner.addProblem(8, Day8(), "50568", "36045012")
  runner.addProblem(9, Day9(), "4781546175", "")
//  runner.addProblem(10, Day10(), "", "")
//  runner.addProblem(11, Day11(), "", "")
//  runner.addProblem(12, Day12(), "", "", true)
  runner.run()
}
