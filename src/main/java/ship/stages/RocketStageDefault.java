package ship.stages;

import lombok.Builder;

@Builder
public class RocketStageDefault implements RocketStage {
    private static String OK = "OK";
    private double mass;
    private double fuelMass;
    private double speedGas;
    private int remainingTime;
    private double fuelConsumptionSpeed;
    private int num;

    @Override
    public double getSpeedGas() {
        return speedGas;
    }

    @Override
    public double getFuelConsumptionSpeed() {
        return fuelConsumptionSpeed;
    }

    public RocketStageDefault(double mass, double fuelMass, double speedGas, int remainingTime, double fuelConsumptionSpeed, int num) {
        this.num = num;
        this.mass = mass;
        this.fuelMass = fuelMass;
        this.speedGas = speedGas;
        this.remainingTime = remainingTime;
        this.fuelConsumptionSpeed = fuelConsumptionSpeed;
    }
    @Override
    public double getAllMass() {
        return mass + fuelMass;
    }

    @Override
    public String test() {
        if (mass > 0 && fuelMass > 0 && remainingTime > 0 && fuelConsumptionSpeed > 0) {
            System.out.println("Тест ступение ракеты №" + num + " выполнен успешно.");
            return OK;
        }
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
    public void print_full_info() {
        System.out.println("Ступень №" + num + ", масса ступени = " + mass + ", масса топлива = " + fuelMass + ", скорость расхода топлива = " + fuelConsumptionSpeed + ", скорость газов = " + speedGas);
    }

    @Override
    public int getRemainingTime() {
        return (int) (fuelMass / fuelConsumptionSpeed);
    }
    @Override
    public void burningGas(double time) {
        fuelMass -= fuelConsumptionSpeed * time;
    }
}
