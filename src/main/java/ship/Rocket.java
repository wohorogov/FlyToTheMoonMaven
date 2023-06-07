package ship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ship.spacecraft.SpaceCraft;
import ship.stages.RocketStage;

import java.util.HashMap;
import java.util.Map;
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Rocket {
    private double distance;
    private double speed;
    private boolean isFly = false;
    Map<Integer, RocketStage> rocketStage = new HashMap();
    SpaceCraft spaceCraft;
    public double getAllMass() {
        double sum = 0;
        for (RocketStage val : this.rocketStage.values()) {
            sum += val.getAllMass();
        }
//        this.rocketStage.forEach((key, value) -> sum += value.getAllMass());

        sum += this.spaceCraft.getAllMass();

        return sum;
    }
    public RocketStage getNextRocketStage(int num) {
        if (!this.rocketStage.isEmpty()) {
            return this.rocketStage.get(num);
        }
        else return null;
    }
    public void deleteRocketStage(int num) {
        rocketStage.remove(num);
    }
    public boolean test() {
        String result = null;
        for (RocketStage val : this.rocketStage.values()) {
            String stageTest = val.test();
            if (!stageTest.equals("OK")) {
                result += stageTest;
            }
        }
        if (result == null) {
            System.out.println("Тестирование ракеты выполнено успешно. Можно производить запуск.");
            return true;
        }
        else {
            System.out.println("Тестирование ракеты выполнено с ошибками.");
            System.out.println(result);
            return false;
        }
    }

    public void printFullInfo() {
        System.out.println("Характеристики запускаемой ракеты:");
        for (RocketStage val : this.rocketStage.values()) {
            val.printFullInfo();
        }
        spaceCraft.printFullInfo();

    }
}
