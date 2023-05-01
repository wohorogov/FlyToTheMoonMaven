package fly;

import ship.Rocket;
import ship.RocketStage;

public class Flying {
    private static final double EARTH_MASS =  5.9722 * 10e24;
    private static final double MOON_MASS =  EARTH_MASS * 0.0123;
    private static final double EARTH_RADIUS = 6371.3;
    private static final double MOON_RADIUS = EARTH_RADIUS * 0.273;
    private static final double GRAVITATIONAL_CONSTANT;

    static {
        GRAVITATIONAL_CONSTANT = 6.67430 * Math.pow(10, -11);
    }

    private static final double FINAL_COORDINATE = 350_000;
    public void startFly(Rocket rocket) {
        int numRocketStage = 0;
        RocketStage rocketStage = rocket.getNextRocketStage(numRocketStage);
        int time = Math.min(rocketStage.getRemainingTime(), 10);

        while (rocket.getRocketStageCount() > 0) {
            double allMass = rocket.getAllMass();
            double coordinate = rocket.getCoordinate();
            
            double forceOfGravitationEarth = getEarthGravitation(allMass, coordinate);
            double forceOfGravitationMoon = getMoonGravitation(allMass, coordinate);
            double reactivePower = getReactivePower(rocketStage);
            double boostNow = (forceOfGravitationMoon - forceOfGravitationEarth + reactivePower) / allMass;

            coordinate += (boostNow * Math.pow(time, 2)) / 2;
            rocket.setCoordinate(coordinate);
            rocketStage.minusTime(time);

            if (rocket.getCoordinate() >= FINAL_COORDINATE)
                System.out.println("Ракета разбилась");
            else {
                time = Math.min(rocketStage.getRemainingTime(), 10);

                if (time == 0) {
                    rocket.deleteRocketStage(numRocketStage);
                    rocketStage = rocket.getNextRocketStage(++numRocketStage);
                    time = Math.min(rocketStage.getRemainingTime(), 10);
                }
            }
        }
    }
    public double getEarthGravitation(double mass, double distance) {
        return GRAVITATIONAL_CONSTANT * EARTH_MASS * mass / Math.pow(distance, 2);
    }

    public double getMoonGravitation(double mass, double distance) {
        return GRAVITATIONAL_CONSTANT * MOON_MASS * mass / Math.pow(distance, 2);
    }

    public double getReactivePower(RocketStage rocketStage) {
        return rocketStage.getSpeedGas() * rocketStage.getFuelConsumptionSpeed();
    }
}
