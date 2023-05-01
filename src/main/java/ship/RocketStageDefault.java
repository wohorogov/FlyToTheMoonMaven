package ship;

import lombok.Builder;

@Builder
public class RocketStageDefault implements RocketStage {
    private static int seq = 1;
    private double num;
    private double mass;
    private double fuelMass;
    private double speedGas;
    private int remainingTime;
    private double fuelConsumptionSpeed;

    @Override
    public double getSpeedGas() {
        return speedGas;
    }

    @Override
    public double getFuelConsumptionSpeed() {
        return fuelConsumptionSpeed;
    }

    public RocketStageDefault(Rocket rocket, double mass, double fuelMass, int remainingTime, double speedGas, double fuelConsumptionSpeed) {
        this.num = seq++;
        this.mass = mass;
        this.speedGas = speedGas;
        this.fuelMass = fuelMass;
        this.remainingTime = remainingTime;
        this.fuelConsumptionSpeed = fuelConsumptionSpeed;
        rocket.addRocketStage(this);
    }

    @Override
    public double getAllMass() {
        return this.mass + this.fuelMass;
    }

    @Override
    public void minusTime(double minusTime) {
        this.remainingTime -= minusTime;
    }

    @Override
    public int getRemainingTime() {
        return remainingTime;
    }
}
