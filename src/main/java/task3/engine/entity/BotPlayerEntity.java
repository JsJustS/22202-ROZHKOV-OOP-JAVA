package task3.engine.entity;

import task3.controller.NetworkS2CController;
import task3.engine.ability.ExplosionAbilityInstance;
import task3.engine.block.Block;
import task3.model.GameModel;
import task3.util.Pair;

import java.util.*;

public class BotPlayerEntity extends PlayerEntity {
    private Stack<Pair<Integer, Integer>> path;
    private Pair<Integer, Integer> goal;
    private final byte[][] map;
    private final Random random;

    public BotPlayerEntity() {
        this(0, 0, null);
    }

    public BotPlayerEntity(double x, double y, byte[][] map) {
        super(x, y);
        this.path = new Stack<>();
        this.map = map;
        random = new Random();
    }

    @Override
    public void tick(GameModel model, NetworkS2CController network) {
        this.tickBrain(model);
        super.tick(model, network);
    }

    private void tickBrain(GameModel model) {
        PlayerEntity player = this.seenPlayer(model);
        if (player != null) {
            if (Math.abs(this.getX() - player.getX()) < 1.1 && Math.abs(this.getY() - player.getY()) < 1.1) {
                model.addAbilityInstance(
                        new ExplosionAbilityInstance(3, (int)this.getX(), (int)this.getY(), this)
                );
            }
            updateGoal(new Pair<>((int)player.getX(), (int)player.getY()));
        }

        if (this.path.empty()) {
            updateGoal();
            return;
        }

        Pair<Integer, Integer> top = this.path.peek();
        if (top.getFirst() + 0.5 == this.x && top.getSecond() + 0.5 == this.y) {
            this.path.pop();
            this.setMoving(false);
            if (this.path.empty()) {
                updateGoal();
                return;
            }
        }
        if (this.isMoving()) return;

        top = this.path.peek();
        this.moveTo(top);
        if (model.getBlock(top.getFirst(), top.getSecond()) != null) {
            updateGoal();
        }
    }

    private void moveTo(Pair<Integer, Integer> coordinate) {
        this.setMoving(true);
        int deltaX = coordinate.getFirst() - (int)this.x;
        int deltaY = coordinate.getSecond() - (int)this.y;
        if (deltaX > 0) {
            this.setDirection(Direction.RIGHT);
        } else if (deltaX < 0) {
            this.setDirection(Direction.LEFT);
        } else if (deltaY > 0) {
            this.setDirection(Direction.DOWN);
        } else if (deltaY < 0) {
            this.setDirection(Direction.UP);
        }
    }

    private void updateGoal(Pair<Integer, Integer> goal) {
        if (goal == null) {
            this.goal = this.randomNeighbourBlock();
        } else {
            this.goal = goal;
        }

        this.path = this.buildPath(this.goal, this.bfs(this.goal));
        //System.out.println(this.goal);
        //System.out.println(this.path);
    }

    private void updateGoal() {
        this.updateGoal(null);
    }

    private PlayerEntity seenPlayer(GameModel model) {
        for (Entity entity : model.getEntities()) {
            if ((entity instanceof BotPlayerEntity)) {
                continue;
            }
            if (!(entity instanceof PlayerEntity)) {
                continue;
            }
            if ((int)entity.getX() == (int)this.getX()) {
                boolean isClear = true;
                for (int y = Math.min((int)entity.getY(), (int)this.getY()); y < Math.max((int)entity.getY(), (int)this.getY()); ++y) {
                    Block block = model.getBlock((int)this.x, y);
                    if (block != null) {
                        isClear = false;
                        break;
                    }
                }
                if (isClear) {
                    return (PlayerEntity) entity;
                }
            }

            if ((int)entity.getY() == (int)this.getY()) {
                boolean isClear = true;
                for (int x = Math.min((int)entity.getX(), (int)this.getX()); x < Math.max((int)entity.getX(), (int)this.getX()); ++x) {
                    Block block = model.getBlock(x, (int)this.y);
                    if (block != null) {
                        isClear = false;
                        break;
                    }
                }
                if (isClear) {
                    return (PlayerEntity) entity;
                }
            }
        }
        return null;
    }

    private Pair<Integer, Integer> newRandomGoal() {
        int cycles = 16;
        while (cycles-- > 0) {
            int x = random.nextInt(this.map.length-1)+1;
            int y = random.nextInt(this.map[0].length-1)+1;
            if (this.map[x][y] == 0) {
                return new Pair<>(x, y);
            }
        }
        return new Pair<>((int)this.x, (int)this.y);
    }

    private Stack<Pair<Integer, Integer>> buildPath(Pair<Integer, Integer> goal, Map<Pair<Integer, Integer>, Pair<Integer, Integer>> visited) {
        Stack<Pair<Integer, Integer>> path = new Stack<>();
        if (!visited.containsKey(goal)) {
            return path;
        }
        path.add(goal);
        Pair<Integer, Integer> current = visited.get(goal);
        while (current != null) {
            path.add(current);
            current = visited.get(current);
        }
        return path;
    }

    private Map<Pair<Integer, Integer>, Pair<Integer, Integer>> bfs(Pair<Integer, Integer> goal) {
        Pair<Integer, Integer> start = new Pair<>((int)this.x, (int)this.y);
        Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> graph = new HashMap<>();

        for (int x = 0; x < this.map.length; ++x) {
            for (int y = 0; y < this.map[0].length; ++y) {
                if (this.map[x][y] == 0) {
                    List<Pair<Integer, Integer>> temp = graph.getOrDefault(new Pair<>(x, y), new ArrayList<>());
                    temp.addAll(this.nextNodes(x, y));
                    graph.put(new Pair<>(x, y), temp);
                }
            }
        }
        List<Pair<Integer, Integer>> temp = graph.getOrDefault(start, new ArrayList<>());
        temp.addAll(this.nextNodes(start.getFirst(), start.getSecond()));
        graph.put(start, temp);

        Queue<Pair<Integer, Integer>> queue = new ArrayDeque<>();
        queue.add(start);
        Map<Pair<Integer, Integer>, Pair<Integer, Integer>> visited = new HashMap<>();
        visited.put(start, null);

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> current = queue.poll();
            if (current.equals(goal)) {
                break;
            }
            List<Pair<Integer, Integer>> nextNodes = graph.get(current);
            if (nextNodes == null) continue;
            for (Pair<Integer, Integer> node : nextNodes) {
                if (!visited.containsKey(node)) {
                    queue.add(node);
                    visited.put(node, current);
                }
            }
        }
        return visited;
    }

    private List<Pair<Integer, Integer>> nextNodes(int x, int y) {
        List<Pair<Integer, Integer>> answer = new ArrayList<>();
        for (int direction = 0; direction < 4; ++direction) {
            switch (direction) {
                case 0: {
                    if (0 <= x - 1 && x - 1 < this.map.length &&
                        0 <= y && y < this.map[0].length &&
                        this.map[x - 1][y] == 0) {
                        answer.add(new Pair<>(x - 1, y));
                    }
                    break;
                }
                case 1: {
                    if (0 <= x && x < this.map.length &&
                        0 <= y - 1 && y - 1 < this.map[0].length &&
                        this.map[x][y - 1] == 0) {
                        answer.add(new Pair<>(x, y - 1));
                    }
                    break;
                }
                case 2: {
                    if (0 <= x + 1 && x + 1 < this.map.length &&
                        0 <= y && y < this.map[0].length &&
                        this.map[x + 1][y] == 0) {
                        answer.add(new Pair<>(x + 1, y));
                    }
                    break;
                }
                case 3: {
                    if (0 <= x && x < this.map.length &&
                        0 <= y + 1 && y + 1 < this.map[0].length &&
                        this.map[x][y + 1] == 0) {
                        answer.add(new Pair<>(x, y+1));
                    }
                    break;
                }
            }
        }
        return answer;
    }

    private Pair<Integer, Integer> randomNeighbourBlock() {
        int deltaX = random.nextInt(3) - 1;
        int deltaY= random.nextInt(3) - 1;
        return new Pair<>((int)this.getX() + deltaX, (int)this.getY() + deltaY);
    }
}
