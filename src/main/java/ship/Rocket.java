package ship;

import lombok.Builder;
import ship.spacecraft.SpaceCraft;
import ship.stages.RocketStage;

import java.util.HashMap;
import java.util.Map;
@Builder
public class Rocket {

    private double distance;
    private double speed;

    public boolean isFly() {
        return isFly;
    }

    public void setFly(boolean fly) {
        isFly = fly;
    }

    private boolean isFly = false;
    Map<Integer, RocketStage> rocketStage = new HashMap();
    SpaceCraft spaceCraft;


    public Rocket(double distance, double speed, boolean isFly, Map<Integer, RocketStage> rocketStages, SpaceCraft spaceCraft) {
        this.rocketStage = rocketStages;
        this.spaceCraft = spaceCraft;
        this.distance = distance;
        this.speed = speed;
        this.isFly = isFly;
    }

    public SpaceCraft getSpaceCraft() {
        return spaceCraft;
    }

    public double getSpeed() {
        return speed;
    }

    public Rocket(Map<Integer, RocketStage> rocketStages) {
        this.rocketStage = rocketStages;
        this.distance = 0;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public void print_full_info() {
        System.out.println("Характеристики запускаемой ракеты:");
        for (RocketStage val : this.rocketStage.values()) {
            val.print_full_info();
        }
        spaceCraft.print_full_info();

    }
}
