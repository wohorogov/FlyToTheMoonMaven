package ship.stages;

public interface RocketStage {
    double getAllMass();
    int getRemainingTime();
    double getSpeedGas();
    double getFuelConsumptionSpeed();
    void burningGas(double minusTime);
    String test();
}
