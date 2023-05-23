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
    private static final int START_BRAKE_DISTANCE = 349_914_000;

    private static final int MAX_SPEED_LANDING = 100;

    boolean rocketOperable = true; //ракета цела
    boolean fuelIsEmpty = false; // осталось топливо
    boolean startBrake = false; //старт запуска тормозного блока
    boolean endBrake = false;
    boolean rightLanding = false;

    public void startFly(Rocket rocket) {
        int numRocketStage = 1;
        RocketStage rocketStage = rocket.getNextRocketStage(numRocketStage);
        RocketStage rocketStageBrake = rocket.getSpaceCraft().getBrakeStage();

        int time = Math.min(rocketStage.getRemainingTime(), MIN_TIME);
        int num_iter = 0;

        double distance;

        while (rocketOperable) {
            num_iter++;
            distance = calcDistance(rocket, rocketStage, time);

            if (num_iter < 5)
                checkStartFlying(distance);
            else checkDown(distance);


            if (startBrake && rocket.getSpeed() <= 30) {
                startBrake = false;
                endBrake = true;
                rightLanding = true;
                System.out.println("Ракета вышла на правильную скорость для посадки");
            }

            if (distance >= START_BRAKE_DISTANCE && !startBrake && !endBrake) {
                startBrake = true;
                fuelIsEmpty = true;
                rocketStage = rocketStageBrake;

                System.out.println("Начинаем тормозить.");
            } else {
                if (rocket.getDistance() >= FINAL_DISTANCE) {
                    if (rightLanding && rocket.getSpeed() < MAX_SPEED_LANDING) {
                        System.out.println("Ракета села на Луну");
                        rocketOperable = false;
                    }
                    else {
                        System.out.println("Ракета разбилась");
                        rocketOperable = false;
                    }
                } else {
                    if ((!fuelIsEmpty || startBrake) && !endBrake) {
                        time = Math.min(rocketStage.getRemainingTime(), MIN_TIME);

                        if (time == 0) {
                            if (!fuelIsEmpty) {
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
                            } else {
                                System.out.println("Топливо в тормозном блоке закончилось, ракета осталась без топлива");
                                startBrake = false;
                                endBrake = true;
                                time = MIN_TIME;
                            }
                        }
                    }
                }
            }
        }

        System.out.println(num_iter);
        System.out.println("Полет завершен");
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

    public double calcDistance(Rocket rocket, RocketStage rocketStage, int time) {
        double allMass = rocket.getAllMass();
        double distance = rocket.getDistance();

        double forceOfGravitationEarth = getEarthGravitation(allMass, distance);
        double forceOfGravitationMoon = getMoonGravitation(allMass, distance);
        double reactivePower = !fuelIsEmpty ? getReactivePower(rocketStage) : startBrake ? getReactivePower(rocketStage) * (-1) : 0;

        double boostNow = (forceOfGravitationMoon - forceOfGravitationEarth + reactivePower) / allMass;

        distance += rocket.getSpeed() * time + (boostNow * Math.pow(time, 2)) / 2;

        rocket.setDistance(distance);
        rocket.setSpeed(rocket.getSpeed() + boostNow * time);

        if (!fuelIsEmpty || startBrake)
            rocketStage.burningGas(time);

        print_info(rocket, forceOfGravitationEarth, forceOfGravitationMoon, reactivePower, boostNow);

        return distance;
    }

    private void checkStartFlying(double distance) {
        if (distance < 0) {
            rocketOperable = false;
            System.out.println("Ракета не может взлететь!");
        }
    }

    private void checkDown(double distance) {
        if (distance < 0) {
            rocketOperable = false;
            System.out.println("Ракета упала  на Землю!");
        }
    }
    private void print_info(Rocket rocket, double forceOfGravitationEarth, double forceOfGravitationMoon, double reactivePower, double boostNow) {
        System.out.println("Координата = " + rocket.getDistance() + ", масса = " +
                rocket.getAllMass() + ", сила земли " + forceOfGravitationEarth + ", сила луны " + forceOfGravitationMoon + ", реактивная сила " + reactivePower
                + ", усорение " + boostNow + ", скорость " + rocket.getSpeed());
    }

}
