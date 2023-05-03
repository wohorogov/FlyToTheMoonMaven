package ship;

import lombok.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@Builder
public class Rocket {

    private double coordinate;
    Map<Integer, RocketStage> rocketStage = new HashMap();
    RocketStage brake;


    public Rocket(double coordinate, Map<Integer, RocketStage> rocketStages, RocketStage rocketBrakeStage) {
        this.rocketStage = rocketStages;
        this.brake = rocketBrakeStage;
        this.coordinate = 0;
    }

    public Rocket(Map<Integer, RocketStage> rocketStages) {
        this.rocketStage = rocketStages;
        this.coordinate = 0;
    }
    public double getAllMass() {
        double sum = 0;
        for (RocketStage val : this.rocketStage.values()) {
            sum += val.getAllMass();
        }
//        this.rocketStage.forEach((key, value) -> sum += value.getAllMass());

        sum += this.brake.getAllMass();

        return sum;
    }

    public RocketStage getNextRocketStage(int num) {
        if (!this.rocketStage.isEmpty()) {
            return this.rocketStage.get(num);
        }
        else return null;
    }

    public double getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(double coordinate) {
        this.coordinate = coordinate;
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
    public void addBrakeRocketStage(RocketStage rocketStage) {
        this.brake = rocketStage;
    }
    public String test() {
        String result = null;
        for (RocketStage val : this.rocketStage.values()) {
            String stageTest = val.test();
            if (!stageTest.equals("OK")) {
                result += stageTest;
            }
        }
        if (result.isEmpty())
            return "OK";
        else return result;
    }
}
