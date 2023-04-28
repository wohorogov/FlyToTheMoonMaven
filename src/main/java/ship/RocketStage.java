package ship;

public interface RocketStage {
    double getAllMass();
    int getRemainingTime();
    double getSpeedGas();
    double getFuelConsumptionSpeed();
    void minusTime(double minusTime);
}
