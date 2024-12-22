package com.s13g.aoc.aoc2024

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY

/**
 * --- Day 14: Restroom Redoubt ---
 * https://adventofcode.com/2024/day/14
 */
class Day14 : Solver {
  override fun solve(lines: List<String>): Result {
    val bots = lines.map { Bot(it) }
    val width = 101
    val height = 103
//    val width = 11
//    val height = 7
    for (sec in 1..100) bots.forEach { it.step(width, height) }


    val botMap = bots.associate { XY(it.x, it.y) to 0 }.toMutableMap()
    for (bot in bots) {
      botMap[XY(bot.x, bot.y)] = botMap[XY(bot.x, bot.y)]!! + 1
    }

    // Count per quadrant.
    val qX = (width - 1) / 2
    val qY = (height - 1) / 2
    val numQ1 = bots.count { it.x < qX && it.y < qY }
    val numQ2 = bots.count { it.x > qX && it.y < qY }
    val numQ3 = bots.count { it.x < qX && it.y > qY }
    val numQ4 = bots.count { it.x > qX && it.y > qY }
    val result1 = numQ1 * numQ2 * numQ3 * numQ4


    var seconds = 100
    var result2 = 0
    while (result2 == 0) {
      bots.forEach { it.step(width, height) }
      seconds++
      val allPos = bots.map { XY(it.x, it.y) }.toSet()
      for (y in 0 until height) {
        var xcount = 0
        for (x in 0 until width) {
          if (XY(x, y) in allPos) xcount++
          else {
            if (xcount > 20) {
              result2 = seconds
//              val botMap = bots.associate { XY(it.x, it.y) to 0 }.toMutableMap()
//              println("WE FOUND SOMETHING ==> $seconds")
//              for (y in 0 until height) {
//                for (x in 0 until width) {
//                  val pos = XY(x, y)
//                  print(if (pos in botMap) botMap[pos] else ".")
//                }
//                print("\n")
//              }
              break;
            }
            xcount = 0
          }
        }
        if (result2 > 0) break;
      }
    }
    return Result("$result1", "$result2")
  }


  private fun Bot(line: String): Bot {
    // TODO: Replace with regex
    val parts = line.split(" ")
    val pos = parts[0].substring(2).split(",").map { it.toInt() }
    val vel = parts[1].substring(2).split(",").map { it.toInt() }
    return Bot(pos[0], pos[1], XY(vel[0], vel[1]))
  }

  private data class Bot(var x: Int, var y: Int, val v: XY)

  private fun Bot.step(w: Int, h: Int) {
    x = (x + v.x) % w
    y = (y + v.y) % h
    if (x < 0) x += w
    if (y < 0) y += h
  }
}