package threads;

import fly.Flying;
import ship.Rocket;
import ship.stages.RocketStage;

import java.util.concurrent.Exchanger;

public class RunnableShip implements Runnable{
    private static final double MIN_TIME = 1;
    private static final int START_BRAKE_DISTANCE = 349_914_000;
    private static final double FINAL_DISTANCE = 350_000_000;
    private static final int MAX_SPEED_LANDING = 100;




    Rocket rocket;

    public RunnableShip(Rocket rocket) {
        this.rocket = rocket;
    }

    @Override
    public void run() {
        Exchanger<String> exchanger = new Exchanger<String>();
        System.out.println("Мы в потоке Ракеты");

        boolean rocketOperable = true;
        Flying flying = new Flying();

        int numRocketStage = 1;
        RocketStage rocketStage = rocket.getNextRocketStage(numRocketStage);
        RocketStage rocketStageBrake = rocket.getSpaceCraft().getBrakeStage();

        double time = Math.min(rocketStage.getRemainingTime(), MIN_TIME);
        int num_iter = 0;
        while (flying.isRocketOperable()) {
            num_iter++;
            rocketOperable = flying.calcFly(rocket, rocketStage, time);

            if (rocketOperable) {
                if (num_iter < 5)
                    flying.checkStartFlying(rocket.getDistance());
                else flying.checkDown(rocket.getDistance());


                if (flying.isStartBrake() && rocket.getSpeed() <= 0) {
                    flying.setStartBrake(false);
                    flying.setEndBrake(true);
                    flying.setRightLanding(true);
                    System.out.println("Ракета вышла на правильную скорость для посадки");
                }

                if (rocket.getDistance() >= START_BRAKE_DISTANCE && !flying.isStartBrake() && !flying.isEndBrake()) {
                    flying.setStartBrake(true);
                    flying.setFuelIsEmpty(true);
                    rocketStage = rocketStageBrake;

                    System.out.println("Начинаем тормозить.");
                } else {
                    if (rocket.getDistance() >= FINAL_DISTANCE) {
                        if (flying.isRightLanding() && rocket.getSpeed() < MAX_SPEED_LANDING) {
                            System.out.println("Ракета села на Луну");
                            flying.setRocketOperable(false);
                        } else {
                            System.out.println("Ракета разбилась");
                            flying.setRocketOperable(false);
                        }
                    } else {
                        if ((!flying.isFuelIsEmpty() || flying.isStartBrake()) && !flying.isEndBrake()) {
                            time = Math.min(rocketStage.getRemainingTime(), MIN_TIME);

                            if (time == 0) {
                                if (!flying.isFuelIsEmpty()) {
                                    rocket.deleteRocketStage(numRocketStage);
                                    numRocketStage++;
                                    try {
                                        rocketStage = rocket.getNextRocketStage(numRocketStage);
                                        time = Math.min(rocketStage.getRemainingTime(), MIN_TIME);
                                    } catch (Exception e) {
                                        flying.setFuelIsEmpty(true);
                                        time = MIN_TIME;
                                        System.out.println("Топливо в ступенях закончилось.");
                                    }
                                } else {
                                    System.out.println("Топливо в тормозном блоке закончилось, ракета осталась без топлива");
                                    flying.setStartBrake(false);
                                    flying.setEndBrake(true);
                                    time = MIN_TIME;
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Полет завершен");
        System.out.println(num_iter);
    }
}
