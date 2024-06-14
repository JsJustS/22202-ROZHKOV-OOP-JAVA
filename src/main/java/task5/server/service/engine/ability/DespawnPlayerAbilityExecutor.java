package task5.server.service.engine.ability;

import task5.model.GameModel;
import task5.model.abilityInstance.AbstractAbilityInstanceModel;
import task5.model.abilityInstance.DespawnPlayerAbilityInstanceModel;
import task5.model.abilityInstance.SpawnEntityAbilityInstanceModel;
import task5.model.entity.BotEntityModel;
import task5.model.entity.PlayerEntityModel;
import task5.server.SocketServer;

public class DespawnPlayerAbilityExecutor extends AbstractAbilityExecutor {

    @Override
    public void execute(AbstractAbilityInstanceModel abilityInstance, GameModel model, SocketServer network) {
        super.execute(abilityInstance, model, network);

        DespawnPlayerAbilityInstanceModel despawnAbility = (DespawnPlayerAbilityInstanceModel) abilityInstance;
        PlayerEntityModel player = despawnAbility.getPlayerEntity();
        player.setAlive(false);

        BotEntityModel bot = new BotEntityModel();
        bot.setX(player.getX());
        bot.setY(player.getY());
        bot.setVelocity(player.getVelocity());
        bot.setDirection(player.getDirection());
        bot.setMoving(player.isMoving());
        model.addAbilityInstance(
                new SpawnEntityAbilityInstanceModel(bot, bot.getEntityType())
        );
    }
}
