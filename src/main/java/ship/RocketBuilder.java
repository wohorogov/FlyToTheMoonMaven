package ship;

public interface RocketBuilder {
    RocketBuilder addStage();

    RocketBuilder addStage(RocketStageDefault rocketStageDefault);

    RocketBuilder addBrakeBlock();
    RocketBuilder addMoonWalker();
    Rocket build();
}
