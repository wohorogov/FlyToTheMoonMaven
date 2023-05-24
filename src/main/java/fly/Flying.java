package fly;

import ship.Rocket;
import ship.stages.RocketStage;

public class Flying {
    private static final double EARTH_MASS = 5.9722 * Math.pow(10, 24);
    private static final double MOON_MASS = EARTH_MASS * 0.0123;
    private static final double EARTH_RADIUS = 6_371_300;
    private static final double MOON_RADIUS = EARTH_RADIUS * 0.273;
    private static final double GRAVITATIONAL_CONSTANT;

    static {
        GRAVITATIONAL_CONSTANT = 6.67430 * Math.pow(10, -11);
    }

    private static final double FINAL_DISTANCE = 350_000_000;
    boolean rocketOperable = true; //ракета цела
    boolean fuelIsEmpty = false; // осталось топливо
    boolean startBrake = false; //старт запуска тормозного блока
    boolean endBrake = false;
    boolean rightLanding = false;

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

//        return distance;
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

    private void print_info(Rocket rocket, double forceOfGravitationEarth, double forceOfGravitationMoon, double reactivePower, double boostNow) {
        System.out.println("Координата = " + rocket.getDistance() + ", масса = " +
                rocket.getAllMass() + ", сила земли " + forceOfGravitationEarth + ", сила луны " + forceOfGravitationMoon + ", реактивная сила " + reactivePower
                + ", усорение " + boostNow + ", скорость " + rocket.getSpeed());
    }

    public boolean isFuelIsEmpty() {
        return fuelIsEmpty;
    }

    public boolean isStartBrake() {
        return startBrake;
    }

    public boolean isEndBrake() {
        return endBrake;
    }

    public boolean isRightLanding() {
        return rightLanding;
    }

    public void setRocketOperable(boolean rocketOperable) {
        this.rocketOperable = rocketOperable;
    }

    public void setFuelIsEmpty(boolean fuelIsEmpty) {
        this.fuelIsEmpty = fuelIsEmpty;
    }

    public void setStartBrake(boolean startBrake) {
        this.startBrake = startBrake;
    }

    public void setEndBrake(boolean endBrake) {
        this.endBrake = endBrake;
    }

    public void setRightLanding(boolean rightLanding) {
        this.rightLanding = rightLanding;
    }

    public boolean isRocketOperable() {
        return rocketOperable;
    }
}
