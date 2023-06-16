package fly;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ship.Rocket;
import ship.stages.RocketStage;
import threads.RunnableManager;
import threads.RunnableShip;

@Getter
@Setter
@NoArgsConstructor
public class Flying {
    private static final double EARTH_MASS = 5.9722 * Math.pow(10, 24);
    private static final double MOON_MASS = EARTH_MASS * 0.0123;
    private static final double EARTH_RADIUS = 6_371_300;
    private static final double MOON_RADIUS = EARTH_RADIUS * 0.273;
    private static final String BRAKE_CHECK = "BRAKE_CHECK";
    private static final String BOOST = "BOOST";
    private static final double GRAVITATIONAL_CONSTANT;

    static {
        GRAVITATIONAL_CONSTANT = 6.67430 * Math.pow(10, -11);
    }

    private static final int ERROR_RATE = 100; // Погрешность при расчете расстояния торможения ракеты
    private static final double FINAL_DISTANCE = 350_000_000; // Расстояние до Луны
    boolean rocketOperable = true; //ракета цела
    boolean fuelIsEmpty = false; // осталось топливо
    boolean startBrake = false; //старт запуска тормозного блока
    boolean endBrake = false; //окончание торможения
    boolean rightLanding = false;//посадка

    private double getEarthGravitation(double mass, double distance) {
        return GRAVITATIONAL_CONSTANT * EARTH_MASS * mass / Math.pow((distance + EARTH_RADIUS), 2);
    }

    private double getMoonGravitation(double mass, double distance) {
        return GRAVITATIONAL_CONSTANT * MOON_MASS * mass / Math.pow(((FINAL_DISTANCE - distance) + MOON_RADIUS), 2);
    }

    private double getReactivePower(RocketStage rocketStage) {
        return rocketStage.getSpeedGas() * rocketStage.getFuelConsumptionSpeed();
    }

    public void calcDistance(Rocket rocket, RocketStage rocketStage, double time) {
        double allMass = rocket.getAllMass();
        double distance = rocket.getDistance();

        double boostNow = getBoost(allMass, distance, rocketStage, BOOST);

        distance += rocket.getSpeed() * time + (boostNow * Math.pow(time, 2)) / 2;

        rocket.setDistance(distance);
        rocket.setSpeed(rocket.getSpeed() + boostNow * time);

        if (!fuelIsEmpty || startBrake)
            rocketStage.burningGas(time);

//        printInfo(rocket, boostNow);
    }

    public void checkStartFlying(double distance) {
        if (distance < 0) {
            rocketOperable = false;
            System.out.println("Ракета не может взлететь!");
        }
    }


    public void checkDown(double distance) {
        if (distance < 0) {
            rocketOperable = false;
            System.out.println("Ракета упала  на Землю!");
        }
    }
    public double getBoost(double allMass, double distance, RocketStage rocketStage, String flag) {
        double forceOfGravitationEarth = getEarthGravitation(allMass, distance);
        double forceOfGravitationMoon = getMoonGravitation(allMass, distance);
        double reactivePower;

        if (flag.equals(BRAKE_CHECK))
            reactivePower = getReactivePower(rocketStage) * (-1);
        else
            reactivePower = !fuelIsEmpty ? getReactivePower(rocketStage) : startBrake ? getReactivePower(rocketStage) * (-1) : 0;

        return (forceOfGravitationMoon - forceOfGravitationEarth + reactivePower) / allMass;
    }
    public boolean checkStartBrake(Rocket rocket) {
        boolean result = false;
        double allMass = rocket.getAllMass();
        double distance = rocket.getDistance();
        double speed = rocket.getSpeed();
        double boost = getBoost(allMass, distance, rocket.getSpaceCraft().getBrakeStage(), BRAKE_CHECK);
        double needTimeForBrake = Math.abs(speed / boost);

        double brakeDistance = speed * needTimeForBrake + (boost * Math.pow(needTimeForBrake, 2)) / 2;

        if (brakeDistance >= FINAL_DISTANCE - distance - ERROR_RATE) {// && needTimeForBrake <= rocket.getSpaceCraft().getBrakeStage().getRemainingTime())
            result = true;
        }

        return result;
    }
}
