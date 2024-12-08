package com.s13g.aoc.aoc2024

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.add
import com.s13g.aoc.mul
import com.s13g.aoc.subtract

/**
 * --- Day 8: Resonant Collinearity ---
 * https://adventofcode.com/2024/day/8
 */
class Day8 : Solver {
  override fun solve(lines: List<String>): Result {
    val width = lines[0].length
    val height = lines.size

    // Parse to map antenna frequency to locations.
    val antennas = mutableMapOf<Char, MutableList<XY>>()
    for (y in 0 until height) {
      for (x in 0 until width) {
        val c = lines[y][x]
        if (c != '.') {
          antennas.putIfAbsent(c, mutableListOf())
          antennas[c]!!.add(XY(x, y))
        }
      }
    }

    val antinodes1 = mutableSetOf<XY>()
    val antinodes2 = mutableSetOf<XY>()
    // Now go through each frequency to create the anti-nodes.
    for (freq in antennas.keys) {
      val nodes = antennas[freq]!!
      // Look at all pairs.
      for (i in 0 until nodes.size) {
        antinodes2.add(nodes[i])  // Antennas themselves count in part 2.
        for (j in i + 1 until nodes.size) {
          // Add the resonance frequencies until we're out of the map
          var r = 0
          var stillAdding = true  // While we're generating inside the bounds.
          while (stillAdding) {
            r++
            val delta = nodes[i].subtract(nodes[j]).mul(XY(r, r))
            val newPos1 = nodes[i].add(delta)
            val newPos2 = nodes[j].subtract(delta)

            stillAdding = false
            if ((newPos1.x in 0 until width) && (newPos1.y in 0 until height)) {
              if (r == 1) antinodes1.add(newPos1)
              antinodes2.add(newPos1)
              stillAdding = true
            }
            if ((newPos2.x in 0 until width) && (newPos2.y in 0 until height)) {
              if (r == 1) antinodes1.add(newPos2)
              antinodes2.add(newPos2)
              stillAdding = true
            }
          }
        }
      }
    }
    return Result("${antinodes1.size}", "${antinodes2.size}")
  }
}