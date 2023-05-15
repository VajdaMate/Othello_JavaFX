# Othello Game

This is a two-player board game created using JavaFX. The game is based on the classic board game Othello also known as reversi.

## Rules
- Played on a 8x8 board, starting with 4 disks in the middle, 2 black 2 white
- A disk can only be placed in a way, that it encloses at least one disk of the opponent's disk
- If a player does not have any valid moves, the enemy moves again
- The game ends, if neither player has a valid move, the board is full, or only one color remains
- One with the most disks at the end win, draws are possible, and if the scores are X:0, it means 64:0

## Requirements

- Java 8 or higher
- JavaFX

## How to Run

1. Clone this repository.
2. Open the project in your preferred IDE.
   - Run the `main.GameMain.java` file.
   - Or package the game with Maven and run the JAR

