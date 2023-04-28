package ship;

import java.util.ArrayList;

public class RocketBuildMaster implements RocketBuilder{
    ArrayList<RocketStageDefault> rocketStageDefaults;

    @Override
    public RocketBuilder addStage() {
        return null;
    }

    @Override
    public RocketBuilder addStage(RocketStageDefault rocketStageDefault) {
        rocketStageDefaults.add(rocketStageDefault);
        return this;
    }

    @Override
    public RocketBuilder addBrakeBlock() {
        return null;
    }

    @Override
    public RocketBuilder addMoonWalker() {
        return null;
    }

    @Override
    public Rocket build() {
        return null;
    }
}
