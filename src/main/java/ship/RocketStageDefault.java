package ship;

import lombok.Builder;

@Builder
public class RocketStageDefault implements RocketStage {
    private static String OK = "OK";
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
    public String test() {
        if (mass > 0 && fuelMass > 0 && remainingTime > 0 && fuelConsumptionSpeed > 0)
            return OK;
        else if (mass <= 0) {
            return "Масса ступени ракеты №" + num + "  не может быть меньше 0.";
        } else if (fuelMass <= 0) {
            return "Ступень ракеты №" + num + "  не может быть незаправленной.";
        } else if (remainingTime <= 0) {
            return "Время полета ступени ракеты №" + num + " не может быть меньше или равно 0.";
        } else if (fuelConsumptionSpeed <= 0) {
            return "Скорость расхода топлива ступени ракеты №" + num + "не может быть меньше или равно 0.";
        }
        else return "Произошли ошибки при тестировании ступени ракеты №" + num;
    }

    @Override
    public int getRemainingTime() {
        return remainingTime;
    }
}
