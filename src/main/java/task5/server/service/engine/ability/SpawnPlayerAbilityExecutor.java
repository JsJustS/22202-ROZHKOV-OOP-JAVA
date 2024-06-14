package task5.server.service.engine.ability;

import task5.model.GameModel;
import task5.model.abilityInstance.AbstractAbilityInstanceModel;
import task5.model.abilityInstance.SpawnEntityAbilityInstanceModel;
import task5.model.abilityInstance.SpawnPlayerAbilityInstanceModel;
import task5.model.entity.BotEntityModel;
import task5.model.entity.PlayerEntityModel;
import task5.server.SocketServer;
import task5.server.service.engine.entity.BotService;

public class SpawnPlayerAbilityExecutor extends AbstractAbilityExecutor {
    @Override
    public void execute(AbstractAbilityInstanceModel abilityInstance, GameModel model, SocketServer network) {
        super.execute(abilityInstance, model, network);

        if (network.isClientPlaying(((SpawnPlayerAbilityInstanceModel)abilityInstance).getHostAddress())) {
            return;
        }

        BotEntityModel bot = BotService.getFirstBotEntity(model);
        if (bot == null) {
            return;
        }
        bot.setAlive(false);

        PlayerEntityModel player = new PlayerEntityModel();
        player.setClientUUID(
                ((SpawnPlayerAbilityInstanceModel)abilityInstance).getHostAddress()
        );
        player.setX(bot.getX());
        player.setY(bot.getY());
        player.setVelocity(bot.getVelocity());
        player.setDirection(bot.getDirection());
        player.setMoving(bot.isMoving());
        model.addAbilityInstance(
                new SpawnEntityAbilityInstanceModel(player)
        );

        network.setClientAsPlaying(player.getClientUUID());
    }
}
