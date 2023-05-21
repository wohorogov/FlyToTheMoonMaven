package ship;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RocketBrakeStage implements RocketStage {
    private static String OK = "OK";
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
    }

    public RocketBrakeStage(double mass, double fuelMass, double speedGas, double fuelConsumptionSpeed, int remainingTime) {
        this.mass = mass;
        this.fuelMass = fuelMass;
        this.speedGas = speedGas;
        this.fuelConsumptionSpeed = fuelConsumptionSpeed;
        this.remainingTime = remainingTime;
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
    public void burningGas(double time) {
        fuelMass -= fuelConsumptionSpeed * time;
    }

    public String test() {
        if (mass > 0 && fuelMass > 0 && remainingTime > 0 && fuelConsumptionSpeed > 0)
            return OK;
        else if (mass <= 0) {
            return "Масса тормозного блока не может быть меньше 0.";
        } else if (fuelMass <= 0) {
            return "тормозной блок не может быть незаправленным.";
        } else if (remainingTime <= 0) {
            return "Время полета тормозного блока не может быть меньше или равно 0.";
        } else if (fuelConsumptionSpeed <= 0) {
            return "Скорость расхода топлива тормозного блока не может быть меньше или равно 0.";
        }
        else return "Произошли ошибки при тестировании тормозного блока";
    }
}
