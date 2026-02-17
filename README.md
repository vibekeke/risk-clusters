# risk-clusters
Script for identifying risk clusters in 2D grid

This program takes a file containing an n x n grid of integers, and evaluates 4 konsekutive cells positioned
next to each other - horizontally, vertically and diagonally. 

A cluster is considered significant if the average risk value of the four cells is greater than or equal to 70.

## Requirements

- JDK 23 (tested with 23.0.2)

## How to Run

javac *.java
java Main


## Command-line arguments

java Main <fileName> <segmentSize> <minAverage>

By default the program will:
- test the file riskgrid.txt
- check cell-segments at a length of 4
- evaluate a cluster as significant 


## Assumptions

Must be an n x n grid. Non-square grids will throw an error.
Grid must be at least the size of 2.
Segment size can not exceed grid size (n).


## Design Notes

RiskMatrix handles file parsing and grid traversal
RiskCluster models cluster data and sorting logic
Direction enum contains traversal vectors
Coordinate represents a position in the 2D grid (x, y)

