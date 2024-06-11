package task3.service.engine.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.model.GameModel;
import task3.model.abilityInstance.SpawnExplosionAbilityInstanceModel;
import task3.model.entity.BotEntityModel;
import task3.model.entity.Direction;
import task3.model.entity.EntityModel;
import task3.model.entity.PlayerEntityModel;
import task3.model.entity.blockentity.BlockEntityModel;
import task3.service.registry.EntityRegistry;
import task3.util.Pair;

import java.util.*;

public class BotService extends EntityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BotService.class);

    @Override
    public void useAbility(EntityModel entity, GameModel model) {
        super.useAbility(entity, model);
        model.addAbilityInstance(
                new SpawnExplosionAbilityInstanceModel(3, (int)entity.getX()+.5, (int)entity.getY()+.5, entity)
        );
    }

    @Override
    public void tick(EntityModel entity, GameModel model) {
        if (!(entity instanceof BotEntityModel)) {
            LOGGER.warn("Trying to use BotService.useAbility() for other entity");
            return;
        }

        BotEntityModel bot = (BotEntityModel) entity;

        tickBrain(bot, model);
        super.tick(entity, model);

        if (bot.getReceivedDamage() > 0) {
            LOGGER.warn(bot.getAttacker()+"");
            if (bot.getAttacker() != null) {
                EntityService service = EntityRegistry.getService(bot.getAttacker());
                if (service != null) {
                    service.onKill(bot, bot.getAttacker());
                }
            }
            kill(bot);
        }
    }

    @Override
    public void tickMovement(EntityModel entity, GameModel model) {
        if (!(entity instanceof BotEntityModel)) {
            LOGGER.warn("Trying to use BotService.tickMovement() for other entity");
            return;
        }

        BotEntityModel bot = (BotEntityModel) entity;
        if (bot.isMoving() && bot.getVelocity() == 0) {
            bot.setVelocity(bot.getSpeed());
        }

        super.tickMovement(entity, model);
    }

    private void tickBrain(BotEntityModel bot, GameModel model) {

        PlayerEntityModel player = lookForPlayer(bot, model);
        if (player != null) {
            if (Math.abs(bot.getX() - player.getX()) < 1.1 && Math.abs(bot.getY() - player.getY()) < 1.1) {
                useAbility(bot, model);
                kill(bot);
            }
            updateGoal(bot, model, new Pair<>((int)player.getX(), (int)player.getY()));
        }

        if (bot.getPath().empty()) {
            updateGoal(bot, model);
            return;
        }

        Pair<Integer, Integer> top = bot.getPath().peek();
        if (top.getFirst() + 0.5 == bot.getX() && top.getSecond() + 0.5 == bot.getY()) {
            bot.getPath().pop();
            bot.setMoving(false);
            if (bot.getPath().empty()) {
                updateGoal(bot, model);
                return;
            }
        }

        if (bot.isMoving()) return;

        top = bot.getPath().peek();
        moveTo(bot, top);
        if (EntityService.getBlockEntityByCoordinates(top.getFirst(), top.getSecond(), model) != null) {
            updateGoal(bot, model);
        }
    }

    private void moveTo(BotEntityModel bot, Pair<Integer, Integer> coordinate) {
        bot.setMoving(true);
        int deltaX = coordinate.getFirst() - (int)bot.getX();
        int deltaY = coordinate.getSecond() - (int)bot.getY();
        if (deltaX > 0) {
            bot.setDirection(Direction.RIGHT);
        } else if (deltaX < 0) {
            bot.setDirection(Direction.LEFT);
        } else if (deltaY > 0) {
            bot.setDirection(Direction.DOWN);
        } else if (deltaY < 0) {
            bot.setDirection(Direction.UP);
        }
    }

    private PlayerEntityModel lookForPlayer(BotEntityModel bot, GameModel model) {
        for (EntityModel entity : model.getEntities()) {
            if (!(entity instanceof PlayerEntityModel)) {
                continue;
            }
            if ((int)entity.getX() == (int)bot.getX()) {
                boolean isClear = true;
                for (int y = Math.min((int)entity.getY(), (int)bot.getY()); y < Math.max((int)entity.getY(), (int)bot.getY()); ++y) {
                    BlockEntityModel block = EntityService.getBlockEntityByCoordinates((int)bot.getX(), y, model);
                    if (block != null && block.isCollidable()) {
                        isClear = false;
                        break;
                    }
                }
                if (isClear) {
                    return (PlayerEntityModel) entity;
                }
            }

            if ((int)entity.getY() == (int)bot.getY()) {
                boolean isClear = true;
                for (int x = Math.min((int)entity.getX(), (int)bot.getX()); x < Math.max((int)entity.getX(), (int)bot.getX()); ++x) {
                    BlockEntityModel block = EntityService.getBlockEntityByCoordinates(x, (int)bot.getY(), model);
                    if (block != null && block.isCollidable()) {
                        isClear = false;
                        break;
                    }
                }
                if (isClear) {
                    return (PlayerEntityModel) entity;
                }
            }
        }
        return null;
    }

    private void updateGoal(BotEntityModel bot, GameModel model, Pair<Integer, Integer> goal) {
        if (goal == null) {
            bot.setGoal(randomNeighbourBlock(bot));
        } else {
            bot.setGoal(goal);
        }

        bot.setPath(buildPath(bot.getGoal(), bfs(bot, model)));
    }

    private void updateGoal(BotEntityModel bot, GameModel model) {
        this.updateGoal(bot, model, null);
    }

    private Pair<Integer, Integer> randomNeighbourBlock(BotEntityModel bot) {
        Random random = new Random();
        int deltaX = random.nextInt(3) - 1;
        int deltaY= random.nextInt(3) - 1;
        return new Pair<>((int)bot.getX() + deltaX, (int)bot.getY() + deltaY);
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

    private Map<Pair<Integer, Integer>, Pair<Integer, Integer>> bfs(BotEntityModel bot, GameModel model) {
        Pair<Integer, Integer> goal = bot.getGoal();
        Pair<Integer, Integer> start = new Pair<>((int)bot.getX(), (int)bot.getY());
        Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> graph = new HashMap<>();

        for (int x = 0; x < model.getBotMap().length; ++x) {
            for (int y = 0; y < model.getBotMap()[0].length; ++y) {
                if (model.getBotMap()[x][y] == 0) {
                    List<Pair<Integer, Integer>> temp = graph.getOrDefault(new Pair<>(x, y), new ArrayList<>());
                    temp.addAll(this.nextNodes(x, y, model));
                    graph.put(new Pair<>(x, y), temp);
                }
            }
        }
        List<Pair<Integer, Integer>> temp = graph.getOrDefault(start, new ArrayList<>());
        temp.addAll(this.nextNodes(start.getFirst(), start.getSecond(), model));
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

    private List<Pair<Integer, Integer>> nextNodes(int x, int y, GameModel model) {
        List<Pair<Integer, Integer>> answer = new ArrayList<>();
        for (int direction = 0; direction < 4; ++direction) {
            switch (direction) {
                case 0: {
                    if (0 <= x - 1 && x - 1 < model.getBotMap().length &&
                            0 <= y && y < model.getBotMap()[0].length &&
                            model.getBotMap()[x - 1][y] == 0) {
                        answer.add(new Pair<>(x - 1, y));
                    }
                    break;
                }
                case 1: {
                    if (0 <= x && x < model.getBotMap().length &&
                            0 <= y - 1 && y - 1 < model.getBotMap()[0].length &&
                            model.getBotMap()[x][y - 1] == 0) {
                        answer.add(new Pair<>(x, y - 1));
                    }
                    break;
                }
                case 2: {
                    if (0 <= x + 1 && x + 1 < model.getBotMap().length &&
                            0 <= y && y < model.getBotMap()[0].length &&
                            model.getBotMap()[x + 1][y] == 0) {
                        answer.add(new Pair<>(x + 1, y));
                    }
                    break;
                }
                case 3: {
                    if (0 <= x && x < model.getBotMap().length &&
                            0 <= y + 1 && y + 1 < model.getBotMap()[0].length &&
                            model.getBotMap()[x][y + 1] == 0) {
                        answer.add(new Pair<>(x, y+1));
                    }
                    break;
                }
            }
        }
        return answer;
    }
}
