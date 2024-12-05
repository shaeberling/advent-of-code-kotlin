package com.s13g.aoc.aoc2024

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY

typealias Plane = List<String>

/**
 * --- Day 4: Ceres Search ---
 * https://adventofcode.com/2024/day/4
 */
class Day4 : Solver {
  override fun solve(lines: Plane): Result {
    var result1 = 0
    var result2 = 0
    for (y in 0 until lines.size) {
      for (x in 0 until lines[0].length) {
        if (lines[y][x] == 'X') result1 += lines.countXmas(x, y)
        if (lines[y][x] == 'A') result2 += lines.countMas(x, y)
      }
    }
    return Result("$result1", "$result2")
  }
}

fun Plane.countXmas(x: Int, y: Int): Int {
  val dirs = listOf<XY>(
    1 xy 0, -1 xy 0, 0 xy 1, 0 xy -1, -1 xy -1,
    1 xy -1, -1 xy 1, 1 xy 1
  )
  return dirs.count { dir -> this.walkCheck(x, y, dir.x, dir.y, "MAS") }
}

fun Plane.countMas(x: Int, y: Int): Int {
  val diag1 =
    ((get(x - 1, y - 1) == 'M' && get(x + 1, y + 1) == 'S') ||
        (get(x - 1, y - 1) == 'S' && get(x + 1, y + 1) == 'M'))
  val diag2 = ((get(x + 1, y - 1) == 'M' && get(x - 1, y + 1) == 'S')
      || (get(x + 1, y - 1) == 'S' && get(x - 1, y + 1) == 'M'))
  return if (diag1 && diag2) 1 else 0
}

fun Plane.walkCheck(x: Int, y: Int, dX: Int, dY: Int, str: String): Boolean {
  for (i in 1..str.length) {
    if (get(x + (dX * i), y + (dY * i)) != str[i - 1]) return false
  }
  return true
}

fun Plane.get(x: Int, y: Int): Char {
  try {
    return this[y][x]
  } catch (e: Exception) {
    return ' '
  }
}

private infix fun Int.xy(that: Int): XY = XY(this, that)
