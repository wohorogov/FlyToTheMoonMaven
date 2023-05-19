package ship;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;
@Builder
public class Rocket {

    private double distance;
    private double speed;
    Map<Integer, RocketStage> rocketStage = new HashMap();
    SpaceCraft spaceCraft;


    public Rocket(double distance, double speed, Map<Integer, RocketStage> rocketStages, SpaceCraft spaceCraft) {
        this.rocketStage = rocketStages;
        this.spaceCraft = spaceCraft;
        this.distance = distance;
        this.speed = speed;
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
    public void addRocketStage(RocketStage rocketStage) {
        this.rocketStage.put(this.rocketStage.size(), rocketStage);
    }

    public int getRocketStageCount() {
        return this.rocketStage.size();
    }
    public void addSpaceCraft(SpaceCraft spaceCraft) {
        this.spaceCraft = spaceCraft;
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
}
