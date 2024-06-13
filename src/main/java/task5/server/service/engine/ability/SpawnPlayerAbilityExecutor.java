package task5.server.service.engine.ability;

import task5.model.GameModel;
import task5.model.abilityInstance.AbstractAbilityInstanceModel;
import task5.model.abilityInstance.SpawnPlayerAbilityInstanceModel;
import task5.model.entity.BotEntityModel;
import task5.model.entity.PlayerEntityModel;
import task5.server.service.engine.entity.BotService;

public class SpawnPlayerAbilityExecutor extends AbstractAbilityExecutor {
    @Override
    public void execute(AbstractAbilityInstanceModel abilityInstance, GameModel model) {
        super.execute(abilityInstance, model);

        BotEntityModel bot = BotService.getFirstBotEntity(model);
        if (bot == null) {
            return;
        }
        model.removeEntity(bot);

        PlayerEntityModel player = new PlayerEntityModel();
        player.setHostAddress(
                ((SpawnPlayerAbilityInstanceModel)abilityInstance).getHostAddress()
        );
        player.setX(bot.getX());
        player.setY(bot.getY());
        player.setId(model.getLastEntityId() + 1);
        model.setLastEntityId(player.getId());
        model.addEntity(player);
    }
}
