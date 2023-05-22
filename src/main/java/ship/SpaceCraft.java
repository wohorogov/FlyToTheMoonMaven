package ship;

import lombok.Builder;

@Builder
public class SpaceCraft {
    private MoonWalker moonWalker;
    private RocketBrakeStage brakeStage;

    public SpaceCraft(MoonWalker moonWalker, RocketBrakeStage brakeStage) {
        this.moonWalker = moonWalker;
        this.brakeStage = brakeStage;
    }

    public MoonWalker getMoonWalker() {
        return moonWalker;
    }

    public RocketBrakeStage getBrakeStage() {
        return brakeStage;
    }

    public double getAllMass() {
        return brakeStage.getAllMass() + moonWalker.getMass();
    }
}
