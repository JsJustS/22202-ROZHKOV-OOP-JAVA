package task3.model.entity;

import task3.model.abilityInstance.Ability;

public class BotEntityModel extends EntityModel {

    public BotEntityModel() {
        setWidth(0.9);
        setHeight(0.9);
        setAbility(Ability.EXPLOSION);
        setRenderLayer(RenderLayer.PLAYERS);
    }
}
