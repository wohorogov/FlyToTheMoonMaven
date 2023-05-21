package fly;

import ship.Rocket;
import ship.RocketStage;

public class Flying {
    private static final int MIN_TIME = 1;
    private static final double EARTH_MASS = 5.9722 * Math.pow(10, 24);
    private static final double MOON_MASS = EARTH_MASS * 0.0123;
    private static final double EARTH_RADIUS = 6_371_300;
    private static final double MOON_RADIUS = EARTH_RADIUS * 0.273;
    private static final double GRAVITATIONAL_CONSTANT;

    static {
        GRAVITATIONAL_CONSTANT = 6.67430 * Math.pow(10, -11);
    }

    private static final double FINAL_DISTANCE = 350_000_000;
    private static final int START_BRAKE_DISTANCE = 300_000_000;

    public void startFly(Rocket rocket) {
        int numRocketStage = 1;
        RocketStage rocketStage = rocket.getNextRocketStage(numRocketStage);
        int time = Math.min(rocketStage.getRemainingTime(), MIN_TIME);
        int num_iter = 0;

        boolean rocketOperable = true; //ракета цела
        boolean fuelIsEmpty = false; // осталось топливо
        boolean startBrake = false;

        while (!fuelIsEmpty && rocketOperable && !startBrake) {
            num_iter++;
            double allMass = rocket.getAllMass();
            double distance = rocket.getDistance();

            double forceOfGravitationEarth = getEarthGravitation(allMass, distance);
            double forceOfGravitationMoon = getMoonGravitation(allMass, distance);
            double reactivePower = getReactivePower(rocketStage);

            double boostNow = (forceOfGravitationMoon - forceOfGravitationEarth + reactivePower) / allMass;

            distance += rocket.getSpeed() * time + (boostNow * Math.pow(time, 2)) / 2;

            if (distance < 0 && num_iter < 5) {
                System.out.println("Ракета не может взлететь!");
                return;
            }

            rocket.setDistance(distance);
            rocket.setSpeed(rocket.getSpeed() + boostNow * time);

            rocketStage.burningGas(time);

            System.out.println("Координата = " + rocket.getDistance() + ", масса = " +
                    rocket.getAllMass() + ", время = " + rocketStage.getRemainingTime() + ", сила земли " + forceOfGravitationEarth + ", сила луны " + forceOfGravitationMoon + ", реактивная сила " + reactivePower
                    + ", усорение " + boostNow + ", скорость " + rocket.getSpeed());

            if (distance >= START_BRAKE_DISTANCE) {
                startBrake = true;
                System.out.println("Начинаем тормозить.");
            }
            else {
                if (rocket.getDistance() >= FINAL_DISTANCE) {
                    System.out.println("Ракета разбилась");
                    rocketOperable = false;
                } else {
                    time = Math.min(rocketStage.getRemainingTime(), MIN_TIME);

                    if (time == 0) {
                        rocket.deleteRocketStage(numRocketStage);
                        numRocketStage++;
                        try {
                            rocketStage = rocket.getNextRocketStage(numRocketStage);
                            time = Math.min(rocketStage.getRemainingTime(), MIN_TIME);
                        } catch (Exception e) {
                            fuelIsEmpty = true;
                            time = MIN_TIME;
                            System.out.println("Топливо в ступенях закончилось.");
                        }
                    }
                }
            }
        }System.out.println(num_iter);
        System.out.println("Полет завершен");
        return;
        /*

        while (rocketOperable) {
            num_iter++;
            double allMass = rocket.getAllMass();
            double distance = rocket.getDistance();

            double forceOfGravitationEarth = getEarthGravitation(allMass, distance);
            double forceOfGravitationMoon = getMoonGravitation(allMass, distance);
            double reactivePower;

            if (!fuelEmpty)
                reactivePower = getReactivePower(rocketStage);
            else
                reactivePower = 0;

            //double boostNow = (forceOfGravitationMoon - forceOfGravitationEarth + reactivePower);// / allMass;
            double boostNow = (forceOfGravitationMoon - forceOfGravitationEarth + reactivePower) / allMass;

            distance += rocket.getSpeed() * time + (boostNow * Math.pow(time, 2)) / 2;
            if (distance < 0) {
                distance = 0;
            }

            rocket.setDistance(distance);
            rocket.setSpeed(rocket.getSpeed() + boostNow * time);

            if (!fuelEmpty) {
                rocketStage.burningGas(time);
                System.out.println("Координата = " + rocket.getDistance() + ", масса = " +
                        rocket.getAllMass() + ", время = " + rocketStage.getRemainingTime() + ", сила земли " + forceOfGravitationEarth + ", сила луны " + forceOfGravitationMoon + ", реактивная сила " + reactivePower
                        + ", усорение " + boostNow + ", скорость " + rocket.getSpeed());
            } else {
                System.out.println("Координата = " + rocket.getDistance() + ", масса = " +
                        rocket.getAllMass() + ", сила земли " + forceOfGravitationEarth + ", сила луны " + forceOfGravitationMoon + ", реактивная сила " + reactivePower
                        + ", усорение " + boostNow + ", скорость " + rocket.getSpeed());
            }


        }*/

    }

    public double getEarthGravitation(double mass, double distance) {
        return GRAVITATIONAL_CONSTANT * EARTH_MASS * mass / Math.pow((distance + EARTH_RADIUS), 2);
    }

    public double getMoonGravitation(double mass, double distance) {
        return GRAVITATIONAL_CONSTANT * MOON_MASS * mass / Math.pow(((FINAL_DISTANCE - distance) + MOON_RADIUS), 2);
    }

    public double getReactivePower(RocketStage rocketStage) {
        return rocketStage.getSpeedGas() * rocketStage.getFuelConsumptionSpeed();
    }
}
