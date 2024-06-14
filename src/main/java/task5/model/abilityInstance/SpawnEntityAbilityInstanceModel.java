package task5.model.abilityInstance;

import task5.model.entity.EntityModel;
import task5.model.entity.EntityType;
import task5.model.entity.PlayerEntityModel;

public class SpawnEntityAbilityInstanceModel extends AbstractAbilityInstanceModel {
    private final EntityModel entityToSpawn;
    private final EntityType entityType;
    private final String boundUUID;


    public SpawnEntityAbilityInstanceModel(EntityModel entityToSpawn, EntityType entityType) {
        this.entityToSpawn = entityToSpawn;
        this.entityType = entityType;
        this.boundUUID = null;
    }

    public SpawnEntityAbilityInstanceModel(PlayerEntityModel playerToSpawn) {
        this.entityToSpawn = playerToSpawn;
        this.entityType = EntityType.Player;
        this.boundUUID = playerToSpawn.getClientUUID();
    }

    public EntityModel getEntityToSpawn() {
        return entityToSpawn;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String getBoundUUID() {
        return boundUUID;
    }
}
