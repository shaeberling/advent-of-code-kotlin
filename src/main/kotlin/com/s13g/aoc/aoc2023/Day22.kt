package com.s13g.aoc.aoc2023

import com.google.common.collect.Range
import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 22: Sand Slabs ---
 * https://adventofcode.com/2023/day/22
 */
class Day22 : Solver {
  override fun solve(lines: List<String>): Result {
    val bricks = lines.map { it.split('~') }
      .map { (start, end) -> Brick(parsePos(start), parsePos(end)) }
      .sortedBy { brick -> brick.start.z }

    val (newBrickPositions, _) = letThemFall(bricks)

    // Now we have to find out which brick supports which other brick above it.
    // Create a map of brick -> which bricks support it.
    // (using index in 'newBrickPositions')
    val brickSupportedBy = List(newBrickPositions.size) { mutableSetOf<Int>() }
    for (i in 0 until newBrickPositions.size) {
      // Move this brick up and see which other bricks its colliding with. Those
      // bricks are therefore supported by this i-th brick.
      val upOne = newBrickPositions[i].moveZ(1)
      // Now check, if it overlaps with any other brick.
      // Only look at bricks further up.
      for (j in i + 1 until newBrickPositions.size) {
        if (doOverlap(newBrickPositions[j], upOne)) {
          brickSupportedBy[j].add(i)
        }
      }
    }

    // Now check the supports. If there is any brick that is only supported by a
    // single other brick, then that other brick cannot be disintegrated safely.
    val canBeDisintegrated = (0 until newBrickPositions.size).toMutableSet()
    for (supports in brickSupportedBy) {
      if (supports.size == 1) {
        canBeDisintegrated.remove(supports.first())
      }
    }

    // For part2, take the fallen pieces and remove one by one. Then take the
    // new state and simulate the fall again. Count all the pieces that move
    // when the one piece was removed and add it all up.
    var totalMovedPart2 = 0
    for (brickToRemove in newBrickPositions) {
      val removed =
        newBrickPositions.toMutableList().apply { remove(brickToRemove) }
      val (_, numMoved) = letThemFall(removed)
      totalMovedPart2 += numMoved
    }
    return Result("${canBeDisintegrated.size}", "$totalMovedPart2")
  }
}

private fun letThemFall(bricks: List<Brick>): Pair<List<Brick>, Int> {
  val newBrickPositions = mutableListOf<Brick>()
  // Go through all brick, starting with the most bottom one, and move them as
  // far as they can go.
  val bricksThatMoved = mutableSetOf<Brick>()
  for (brick in bricks) {
    var prevBrick = brick
    // Move brick as far down as possible
    while (true) {
      // Move one layer down.
      var newPos = prevBrick.moveZ(-1)
      // Already at the bottom or new position would collide with existing brick.
      if (newPos.start.z == 0 || newBrickPositions.any { nbp ->
          doOverlap(
            nbp,
            newPos
          )
        }) {
        newBrickPositions.add(prevBrick)
        break
      }
      bricksThatMoved.add(brick)
      // If the brick position is legal, re-iterate until we hit something.
      prevBrick = newPos
    }
  }
  return return Pair(newBrickPositions, bricksThatMoved.size)
}

private fun doOverlap(a: Brick, b: Brick): Boolean {
  val overlapX = Range.closed<Int>(a.start.x, a.end.x)
    .isConnected(Range.closed<Int>(b.start.x, b.end.x))
  val overlapY = Range.closed<Int>(a.start.y, a.end.y)
    .isConnected(Range.closed<Int>(b.start.y, b.end.y))
  val overlapZ = Range.closed<Int>(a.start.z, a.end.z)
    .isConnected(Range.closed<Int>(b.start.z, b.end.z))
  return overlapX && overlapY && overlapZ
}


private data class Brick(val start: XYZ, val end: XYZ)

private fun Brick.moveZ(delta: Int): Brick {
  val newStart = XYZ(start.x, start.y, start.z + delta)
  val newEnd = XYZ(end.x, end.y, end.z + delta)
  return Brick(newStart, newEnd)
}

private data class XYZ(val x: Int, val y: Int, val z: Int)

private fun parsePos(str: String): XYZ {
  val (x, y, z) = str.split(',').map { it.toInt() }
  return XYZ(x, y, z)
}
