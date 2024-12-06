package com.s13g.aoc.aoc2024

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.add
import com.s13g.aoc.subtract


/**
 * --- Day 6: Guard Gallivant ---
 * https://adventofcode.com/2024/day/6
 */
class Day6 : Solver {
  val dirs = listOf<XY>(XY(0, -1), XY(1, 0), XY(0, 1), XY(-1, 0))

  override fun solve(lines: List<String>): Result {
    val boxes = mutableSetOf<XY>()
    var guardPos = XY(-1, -1)
    for (y in 0 until lines.size) {
      for (x in 0 until lines[0].length) {
        if (lines[y][x] == '#') boxes.add(XY(x, y))
        if (lines[y][x] == '^') guardPos = XY(x, y)
      }
    }

    // Simulate, Part1, until the guard exists the playing field.
    val (_, guardVisited) = runAndCheckForLoop(
      guardPos,
      lines[0].length,
      lines.size,
      boxes
    )

    // Place a box in every location the guard originally travelled, and run the
    // simulation from scratch to detect a loop.
    var numLoopsCreated = 0
    for (newBoxPos in guardVisited.keys) {
      if (newBoxPos == guardPos) continue
      val newBoxes = boxes.toMutableSet().apply { add(newBoxPos) }
      if (runAndCheckForLoop(
          guardPos,
          lines[0].length,
          lines.size,
          newBoxes
        ).isLooping
      ) {
        numLoopsCreated++
      }
    }

    return Result("${guardVisited.size + 1}", "$numLoopsCreated")
  }

  data class LoopResult(val isLooping: Boolean, val visited: Map<XY, Set<XY>>)

  private fun runAndCheckForLoop(
    gp: XY,
    width: Int,
    height: Int,
    boxes: Set<XY>
  ): LoopResult {
    val guardVisited = mutableMapOf<XY, MutableSet<XY>>()
    var guardPos = gp
    var dirIdx = 0
    // Run while within the bounds of the playing field.
    while (guardPos.x >= 0 && guardPos.x < width && guardPos.y >= 0 && guardPos.y < height) {
      // Check if the guard hit a box.
      if (guardPos in boxes) {
        // If so, return to position before he hit the box and turn clockwise.
        guardPos = guardPos.subtract(dirs[dirIdx])
        dirIdx = (dirIdx + 1) % dirs.size
        // We've been here going the same way, loop detected!
        if (guardPos != gp && guardPos in guardVisited &&
          guardVisited[guardPos]!!.contains(dirs[dirIdx])
        ) {
          return LoopResult(true, guardVisited)
        }
      } else {
        if (guardPos != gp) {
          // We've been here going the same way, loop detected!
          if (guardPos in guardVisited && guardVisited[guardPos]!!.contains(dirs[dirIdx])) {
            return LoopResult(true, guardVisited)
          }
          // If not, add the position and direction.
          guardVisited.apply { putIfAbsent(guardPos, mutableSetOf()) }
          guardVisited[guardPos]!!.add(dirs[dirIdx])
        }
      }
      guardPos = guardPos.add(dirs[dirIdx])  // Move one step.
    }
    return LoopResult(false, guardVisited)
  }
}