/*
 * Copyright 2017 Sascha Haeberling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.s13g.aoc

import com.google.common.collect.ImmutableList
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsBytes
import kotlinx.coroutines.runBlocking
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.exists
import kotlin.io.path.writeBytes
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow


/**
 * Reads a file as string.
 */
@Throws(IOException::class)
fun readAsString(file: Path): List<String> {
  return try {
    ImmutableList.copyOf(file.toFile().readLines())
  } catch (e: FileNotFoundException) {
    System.err.println("Cannot read file: ${e.message}")
    emptyList()
  }
}

fun fetchAdventOfCodeInput(year: Int, day: Int, file: Path) {
  runBlocking {
    val fileLines = readAsString(Path.of("session_cookie"))
    if (fileLines.isEmpty() || fileLines.first().isBlank()) {
      throw RuntimeException("ERROR: Unable to read session_cookie file.")
    }
    val session = fileLines.first()

    val client = HttpClient(CIO)
    val url = "https://adventofcode.com/$year/day/$day/input"
    try {
      val response: HttpResponse = client.get(url) {
        headers { append("Cookie", "session=$session") }
      }
      if (!file.parent.exists()) file.parent.createDirectories()
      file.writeBytes(response.bodyAsBytes())
    } catch (e: Exception) {
      println("Error fetching input: ${e.message}")
      throw e
    } finally {
      client.close()
    }
  }
}

// Range that isn't depended on which one is larger
fun aocRange(from: Int, to: Int) =
  if (from < to) from..to
  else from downTo to

fun List<Long>.mul(): Long {
  var result: Long = this[0]
  for (idx in 1 until this.size) {
    result *= this[idx]
  }
  return result
}

fun List<Int>.mul(): Int {
  var result: Int = this[0]
  for (idx in 1 until this.size) {
    result *= this[idx]
  }
  return result
}

fun <E> Collection<Collection<E>>.intersectAll(): Set<E> {
  var common = this.first().toSet()
  this.map { it.toMutableSet() }.forEach { common = common.intersect(it) }
  return common
}

fun Int.toBinary(length: Int): String {
  return String.format("%4s", this.toString(2)).replace(" ".toRegex(), "0")
}

data class XY(var x: Int, var y: Int)

fun XY.addTo(other: XY) {
  this.x += other.x
  this.y += other.y
}

fun XY.subtract(other: XY) = XY(x - other.x, y - other.y)
fun XY.add(other: XY) = XY(x + other.x, y + other.y)
fun XY.mul(other: XY) = XY(x * other.x, y * other.y)

fun XY.max() = maxOf(x, y)

/** Rotates the point times*90 degrees around the origin. */
fun XY.rotate90(times: Int, left: Boolean): XY {
  val result = XY(this.x, this.y)
  for (t in 0 until times) {
    val tempX = result.x
    result.x = (if (left) -1 else 1) * result.y; result.y =
      (if (!left) -1 else 1) * tempX
  }
  return result
}

/** Manhattan distance to the origin */
fun XY.manhattan() = abs(this.x) + abs(this.y)

infix fun Int.`**`(exp: Int) = this.toDouble().pow(exp).toInt()
infix fun Long.`**`(exp: Int) = this.toDouble().pow(exp).toLong()

/** Lowest common multiple of a list. */
fun lcm(numbers: List<Long>) = numbers.reduce { acc, it -> lcm(acc, it) }

/** Lowest common multiple of two numbers. */
fun lcm(a: Long, b: Long): Long {
  val larger = max(a, b)
  val maxLcm = a * b
  var lcm = larger
  while (lcm <= maxLcm) {
    if (lcm % a == 0L && lcm % b == 0L) {
      return lcm
    }
    lcm += larger
  }
  return maxLcm
}