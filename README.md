# 🟩 Othello (Reversi) Java Project

---

## 🎮 Overview

This project is a **console-based** implementation of the classic board game **Othello (Reversi)** in Java. Play against an AI opponent powered by the Minimax algorithm with alpha-beta pruning.

---

## 🧩 Features

- 🟦 **8x8 Othello board** with correct initial setup
- 👤 **Human vs 🤖 AI** gameplay
- 🧠 **AI uses Minimax** with alpha-beta pruning for smart moves
- ✅ **Move validation** and automatic flipping of pawns
- 📊 **Board scoring** and move history display
- 🏁 **Game end detection** (win, lose, draw)

---

## ▶️ How to Run

1. **Compile the project:**
   ```sh
   javac -d bin src/ce326/hw2/*.java
   ```

2. **Run the main class:**
   ```sh
   java -cp bin ce326.hw2.HW2
   ```

---

## 🕹️ Gameplay Instructions

- On start, **choose your color** (`black`/`white` or shortcuts like `b`, `w`, `O`, `X`).
- Enter the **number of moves** the AI should look ahead (**1-9**).
- Enter your moves in the format `c2` (**column letter + row number**).
- The board and move history are displayed after each turn.

### Example Board

```
  a b c d e f g h
1 . . . . . . . .
2 . . . . . . . .
3 . . . . . . . .
4 . . . X O . . .
5 . . . O X . . .
6 . . . . . . . .
7 . . . . . . . .
8 . . . . . . . .
```

- `X` = White pawn
- `O` = Black pawn
- `*` = Available move
- `.` = Empty cell

---

## 🗂️ Code Structure

- `HW2.java` — Main class, handles game loop and user interaction
- `Board.java` — Board logic, move validation, scoring, and AI algorithm
- `Pawn.java` and subclasses — Represent board pieces
- `Move.java` — Represents a move made by a player
- `evalObject.java` — Helper for Minimax evaluation
- `AvailableMove.java`, `NoPawn.java`, `PawnBlack.java`, `PawnWhite.java` — Piece types

---

## ⚙️ Requirements

- Java 8 or higher

---

## 💡 Notes

- Only **console interaction** is supported
- **AI difficulty** is controlled by the lookahead depth
- All code is **object-oriented** and modular for easy extension
- Error handling for invalid inputs is included