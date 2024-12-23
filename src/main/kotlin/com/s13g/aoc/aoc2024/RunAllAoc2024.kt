package com.s13g.aoc.aoc2024

import com.s13g.aoc.PuzzleRunner

private const val ONLY_RUN_NEW = true

/**
 * ---- Advent of Code 2024 ----
 * https://adventofcode.com/2024
 */
fun main() {
  val runner = PuzzleRunner(ONLY_RUN_NEW, 2024)
  runner.addProblem(1, Day1(), "2815556", "23927637")
  runner.addProblem(2, Day2(), "287", "354")
  runner.addProblem(3, Day3(), "175615763", "74361272")
  runner.addProblem(4, Day4(), "2344", "1815")
  runner.addProblem(5, Day5(), "6242", "5169")
  runner.addProblem(6, Day6(), "4647", "1723")
  runner.addProblem(7, Day7(), "6392012777720", "61561126043536")
  runner.addProblem(8, Day8(), "396", "1200")
  runner.addProblem(9, Day9(), "6421128769094", "6448168620520")
  runner.addProblem(10, Day10(), "472", "969")
  runner.addProblem(11, Day11(), "186203", "221291560078593")
//  runner.addProblem(12, Day12(), "", "", true)
//  runner.addProblem(13, Day13(), "", "", true)
  runner.addProblem(14, Day14(), "221616000", "7572", true)
//  runner.addProblem(15, Day15(), "", "", true)
//  runner.addProblem(16, Day16(), "", "", true)
//  runner.addProblem(17, Day17(), "", "", true)
//  runner.addProblem(18, Day18(), "", "", true)
  runner.addProblem(19, Day19(), "322", "715514563508258")
//  runner.addProblem(20, Day20(), "", "", true)
//  runner.addProblem(21, Day21(), "", "", true)
//  runner.addProblem(22, Day22(), "", "", true)
//  runner.addProblem(23, Day23(), "", "", true)
//  runner.addProblem(24, Day24(), "", "", true)
//  runner.addProblem(25, Day25(), "", "", true)
  runner.run()
}
