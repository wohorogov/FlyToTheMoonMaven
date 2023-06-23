package ship.spacecraft;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ship.Rocket;
import ship.stages.RocketStage;

@Data
@Builder
@AllArgsConstructor
public class RocketBrakeStage implements RocketStage {
    private static String OK = "OK";
    private double mass;
    private double fuelMass;
    private double speedGas;
    private double fuelConsumptionSpeed;
    public double getAllMass() {
        return mass + fuelMass;
    }

    @Override
    public int getRemainingTime() {
        return (int) (fuelMass / fuelConsumptionSpeed);
    }

    @Override
    public double getSpeedGas() {
        return speedGas;
    }
    @Override
    public void burningGas(double time) {
        fuelMass -= fuelConsumptionSpeed * time;
    }

    public String test() {
        if (mass > 0 && fuelMass > 0 && fuelConsumptionSpeed > 0) {
            System.out.println("Тест тормозного блока выполнен успешно.");
            return OK;
        }
        else if (mass <= 0) {
            return "Масса тормозного блока не может быть меньше 0.";
        } else if (fuelMass <= 0) {
            return "тормозной блок не может быть незаправленным.";
        } else if (fuelConsumptionSpeed <= 0) {
            return "Скорость расхода топлива тормозного блока не может быть меньше или равно 0.";
        }
        else return "Произошли ошибки при тестировании тормозного блока";
    }

    @Override
    public void printFullInfo() {
        System.out.println("Тормозной блок, масса блока = " + mass + ", масса топлива = " + fuelMass + ", скорость расхода топлива = " + fuelConsumptionSpeed + ", скорость газов = " + speedGas);
    }

    @Override
    public int getNum() {
        return 0;
    }
}
