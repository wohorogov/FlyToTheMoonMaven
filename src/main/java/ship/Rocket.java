package ship;

import lombok.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@Builder
public class Rocket {

    private double coordinate = 0;
    private double speed;
    private double totalMass;
    private int rocketStageCount;
    Map<int, RocketStage> rocketStage = new HashMap();
    RocketStage brake;


    public Rocket(Map<int, RocketStage> rocketStages, RocketBrakeStage rocketBrakeStage) {
        this.rocketStage = rocketStages;
        this.brake = rocketBrakeStage;
    }

    public Rocket() {
        super();
    }

    public Rocket(Map<int, RocketStage> rocketStages) {
        this.rocketStage = rocketStages;
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
        this.rocketStageCount = this.rocketStage.size();
        this.rocketStage.put(rocketStageCount, rocketStage);
        this.rocketStageCount++;
    }

    public int getRocketStageCount() {
        return rocketStageCount;
    }
    public void addBrakeRocketStage(RocketStage rocketStage) {
        this.brake = rocketStage;
    }
}
