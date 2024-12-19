package com.s13g.aoc.aoc2024

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.min

/**
 * --- Day 9: Disk Fragmenter ---
 * https://adventofcode.com/2024/day/9
 */
class Day9 : Solver {
  override fun solve(lines: List<String>): Result {
    // Parse into regions. id=-1 is a space.
    val ranges = mutableListOf<Range>()
    for ((idx, chs) in lines[0].windowed(2, 2, true).withIndex()) {
      ranges.add(Range(idx, chs[0].digitToInt()))
      // If there is a space after this region, indicate it as id=-1.
      if (chs.length > 1) ranges.add(Range(-1, chs[1].digitToInt()))
    }
    return Result("${defragSimple(ranges)}", "${defragWholeFiles(ranges)}")
  }

  private fun defragWholeFiles(ranges: List<Range>): Long {
    val allIds = ranges.map { range -> range.id }.filter { it != -1 }
    val workspace = ranges.toMutableList()

    // Go through all the ids in reverse order and move files to the front.
    for (id in allIds.reversed()) {
      val fileIdx = workspace.indexOfLast { it.id == id }
      val file = workspace[fileIdx]
      val fittingSpaceIdx =
        workspace.indexOfFirst { it.id == -1 && it.len >= file.len }
      if (fittingSpaceIdx == -1 || fittingSpaceIdx > fileIdx) continue
      val spaceLen = workspace[fittingSpaceIdx].len
      // Move the file. Put space where the file was
      workspace.removeAt(fileIdx)
      workspace.add(fileIdx, Range(-1, file.len))
      workspace.removeAt(fittingSpaceIdx)
      workspace.add(fittingSpaceIdx, Range(file.id, file.len))
      if (spaceLen > file.len) {
        workspace.add(fittingSpaceIdx + 1, Range(-1, spaceLen - file.len))
      }
    }

    // Calculate the checksum
    var checksum = 0L
    var idx = 0
    for (file in workspace) {
      for (i in 0 until file.len) {
        if (file.id != -1) {
          checksum += (i + idx) * file.id
        }
      }
      idx += file.len
    }
    return checksum
  }

  private fun defragSimple(ranges: List<Range>): Long {
    val totalSize = ranges.sumOf { it.len }
    val boxes = IntArray(totalSize)
    var b = 0
    for (range in ranges) {
      for (bb in b until b + range.len) boxes[bb] = range.id
      b += range.len
    }

    // Defrag
    var frontIdx = 0
    var backIdx = boxes.lastIndex
    while (frontIdx < backIdx) {
      while (boxes[frontIdx] != -1) frontIdx++
      while (boxes[backIdx] == -1) backIdx--
      boxes[frontIdx] = boxes[backIdx]
      boxes[backIdx] = -1
    }


    // Calculate the checksum
    var checksum = 0L
    for ((idx, v) in boxes.filter { it != -1 }.withIndex()) {
      checksum += (idx * v)
    }
    return checksum
  }

  private data class Range(val id: Int, val len: Int)
}

