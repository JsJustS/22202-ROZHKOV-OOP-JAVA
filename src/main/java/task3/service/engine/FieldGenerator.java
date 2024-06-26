package task3.service.engine;

import task3.model.entity.blockentity.Block;
import task3.util.Pair;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class FieldGenerator {
    private final Random random;
    private final int row;
    private final int col;

    private final byte[] range = new byte[]{0, 1, 2, 3};
    public FieldGenerator(long seed, int row, int col) {
        this.random = new Random(seed);
        this.row = row;
        this.col = col;
    }

    public byte[][] generateField(byte[][] botMap) {
        int quarterWidth = (row%2==0)?row/2:row/2+1;
        int quarterHeight = (col%2==0)?col/2:col/2+1;
        byte[][] quarter = new byte[quarterWidth-1][quarterHeight-1];

        byte wall = 1;
        byte empty = 0;

        this.recursiveBacktracking(quarter);
        byte[][] field = new byte[row][col];
        for (int i = 0; i < quarterWidth; ++i) {
            for (int j = 0; j < quarterHeight; ++j) {
                byte value;
                if (i == 0 || j == 0) {
                    value = wall;
                    this.updateBotMap(botMap, i, j, wall);
                } else if (quarterWidth - 2 <= i && quarterHeight -2 <= j && i != j) {
                    value = empty;
                    this.updateBotMap(botMap, i, j, empty);
                } else {
                    value = (byte) ((quarter[i-1][j-1] + 1) % 2);
                    this.updateBotMap(botMap, i, j, value);
                }
                //quarter[i][j] = (byte) ((quarter[i][j] + 1) % 2);

                List<Block> blocksToChooseFrom;
                if (value == wall) {
                    blocksToChooseFrom = Block.getWallBlocks();
                } else {
                    blocksToChooseFrom = Block.getPathBlocks();
                }
                value = (byte)blocksToChooseFrom.get(random.nextInt(blocksToChooseFrom.size())).ordinal();

                if (value == Block.BRICK.ordinal()) {
                    this.updateBotMap(botMap, i, j, empty);
                }

                // 2x2 free space
                if ((0 < i && i < 3) && (0 < j && j < 3)) {
                    value = empty;
                    this.updateBotMap(botMap, i, j, value);
                }
                if (i == 0 || j == 0) {
                    value = (byte)Block.BEDROCK.ordinal();
                    this.updateBotMap(botMap, i, j, wall);
                }

                field[i][j] = value;
                field[row-i-1][j] = value;
                field[i][col-j-1] = value;
                field[row-i-1][col-j-1] = value;
            }
        }
        return field;
    }

    private void updateBotMap(byte[][] map, int i, int j, byte value) {
        map[i][j] = value;
        map[row-i-1][j] = value;
        map[i][col-j-1] = value;
        map[row-i-1][col-j-1] = value;
    }

    private void recursiveBacktracking(byte[][] maze) {
        Stack<Pair<Integer, Integer>> stack = new Stack<>();

        Integer x = 2;
        Integer y = 2;
        maze[x][y] = 1;
        while (x != null & y != null) {
            while (x != null & y != null) {
                stack.add(new Pair<>(x, y));

                Pair<Integer, Integer> coordinates = this.createWalk(maze, x, y);
                x = coordinates.getFirst();
                y = coordinates.getSecond();
            }

            Pair<Integer, Integer> coordinates = this.createBacktrack(maze, stack);
            x = coordinates.getFirst();
            y = coordinates.getSecond();
        }
    }

    private Pair<Integer, Integer> createWalk(byte[][] maze, int x, int y) {
        shuffleRanges();
        for (byte idx : this.range) {
            Pair<Integer, Integer> coordinates = this.dirTwo(idx, x, y);
            int tx = coordinates.getFirst();
            int ty = coordinates.getSecond();

            if (!this.outOfBounds(tx, ty, maze.length, maze[0].length) && maze[tx][ty] == 0) {
                maze[tx][ty] = 1;
                Pair<Integer, Integer> coordinates1 = this.dirOne(idx, x, y);
                maze[coordinates1.getFirst()][coordinates1.getSecond()] = 1;
                return coordinates;
            }
        }

        return new Pair<>(null, null);
    }

    private Pair<Integer, Integer> createBacktrack(byte[][] maze, Stack<Pair<Integer, Integer>> stack) {
        while (!stack.empty()) {
            Pair<Integer, Integer> coordinates = stack.pop();
            int x = coordinates.getFirst();
            int y = coordinates.getSecond();
            for (int idx = 0; idx < 4; ++idx) {
                Pair<Integer, Integer> coordinates1 = this.dirTwo(idx, x, y);
                int tx = coordinates1.getFirst();
                int ty = coordinates1.getSecond();
                if (!this.outOfBounds(tx, ty, maze.length, maze[0].length) && maze[tx][ty] == 0) {
                    return coordinates;
                }
            }
        }

        return new Pair<>(null, null);
    }

    private boolean outOfBounds(int x, int y, int upperW, int upperH) {
        return x < 0 || y < 0 || x >= upperW || y >= upperH;
    }

    private void shuffleRanges() {
        for (int i = 3; i > 0; --i) {
            int index = this.random.nextInt(i + 1);
            // Simple swap
            byte a = this.range[index];
            this.range[index] = this.range[i];
            this.range[i] = a;
        }
    }

    private Pair<Integer, Integer> dirOne(int idx, int x, int y) {
        switch (idx) {
            case 0: return new Pair<>(x+1, y);
            case 1: return new Pair<>(x-1, y);
            case 2: return new Pair<>(x, y+1);
            case 3: return new Pair<>(x, y-1);
            default: return new Pair<>(x, y);
        }
    }

    private Pair<Integer, Integer> dirTwo(int idx, int x, int y) {
        switch (idx) {
            case 0: return new Pair<>(x+2, y);
            case 1: return new Pair<>(x-2, y);
            case 2: return new Pair<>(x, y+2);
            case 3: return new Pair<>(x, y-2);
            default: return new Pair<>(x, y);
        }
    }
}
