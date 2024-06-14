package task5.model.abilityInstance;

import task5.model.entity.PlayerEntityModel;

public class DespawnPlayerAbilityInstanceModel extends AbstractAbilityInstanceModel {
    private final PlayerEntityModel playerEntity;

    public DespawnPlayerAbilityInstanceModel(PlayerEntityModel playerEntity) {
        this.playerEntity = playerEntity;
    }

    public PlayerEntityModel getPlayerEntity() {
        return playerEntity;
    }
}
