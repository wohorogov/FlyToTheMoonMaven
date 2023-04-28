package ship;

import lombok.Data;

@Data
public class RocketBrakeStage implements RocketStage {
    private double mass;
    private double fuelMass;
    private double speedGas;
    private double fuelConsumptionSpeed;
    private int remainingTime;

    public RocketBrakeStage(Rocket rocket, double mass, double fuelMass, double speedGas, double fuelConsumptionSpeed,
                            int remainingTime) {
        this.mass = mass;
        this.fuelMass = fuelMass;
        this.speedGas = speedGas;
        this.fuelConsumptionSpeed = fuelConsumptionSpeed;
        this.remainingTime = remainingTime;
        rocket.addBrakeRocketStage(this);
    }

    public double getAllMass() {
        return this.mass + this.fuelMass;
    }

    @Override
    public int getRemainingTime() {
        return this.remainingTime;
    }

    @Override
    public double getSpeedGas() {
        return 0;
    }

    @Override
    public double getFuelConsumptionSpeed() {
        return 0;
    }

    @Override
    public void minusTime(double minusTime) {

    }
}
