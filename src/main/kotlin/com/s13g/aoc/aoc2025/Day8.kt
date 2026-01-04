package com.s13g.aoc.aoc2025

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom
import kotlin.math.sqrt

/**
 * --- Day 8: Playground ---
 * https://adventofcode.com/2025/day/8
 */
class Day8 : Solver {
  private data class XYZ(val x: Long, val y: Long, val z: Long)

  private fun XYZ.dist(o: XYZ): Double {
    val dx = (this.x - o.x).toDouble()
    val dy = (this.y - o.y).toDouble()
    val dz = (this.z - o.z).toDouble()
    return sqrt(dx * dx + dy * dy + dz * dz)
  }

  override fun solve(lines: List<String>): Result {
    val allBoxes = lines
      .map { line -> line.split(',') }
      .map { split -> split.map { it.toLong() } }
      .map { XYZ(it[0], it[1], it[2]) }
    val connections = mutableListOf(mutableSetOf<XYZ>())

    val distancePairs = allDistancesSorted(allBoxes).sortedBy { it.second }
    var count = 0
    var resultA = 0
    var resultB = 0L
    for (pair in distancePairs) {
      val boxes = pair.first.toList()
      val set1 = connections.firstOrNull { it.contains(boxes[0]) }
      val set2 = connections.firstOrNull { it.contains(boxes[1]) }

      // Combine the circuits that might already exist for these boxes and to
      // a bigger circuit - and remove the individual ones, if they exist.
      val newSet = mutableSetOf<XYZ>()
      newSet.addAll(boxes)
      if (set1 != null) newSet.addAll(set1)
      if (set2 != null) newSet.addAll(set2)

      if (set1 != null) connections.remove(set1)
      if (set2 != null) connections.remove(set2)

      connections.add(newSet)
      if (++count == 1_000) {
        val circuits = connections.sortedByDescending { it.size }
        resultA = circuits[0].size * circuits[1].size * circuits[2].size
      }
      // One big circuit is formed when a set is created that equals the count
      // of the input boxes.
      if (newSet.size == allBoxes.size) {
        resultB = boxes[0].x * boxes[1].x
        break;
      }
    }
    return resultFrom(resultA, resultB)
  }

  private fun allDistancesSorted(boxes: List<XYZ>): List<Pair<Set<XYZ>, Double>> {
    val result = mutableListOf<Pair<Set<XYZ>, Double>>()
    for (i1 in 0..boxes.lastIndex) {
      for (i2 in i1 + 1..boxes.lastIndex) {
        val dist = boxes[i1].dist(boxes[i2])
        result.add(Pair(setOf(boxes[i1], boxes[i2]), dist))
      }
    }
    return result
  }
}