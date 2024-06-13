package task5.model.abilityInstance;

public class SpawnPlayerAbilityInstanceModel extends AbstractAbilityInstanceModel {
    private final String hostAddress;

    public SpawnPlayerAbilityInstanceModel(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getHostAddress() {
        return hostAddress;
    }
}
