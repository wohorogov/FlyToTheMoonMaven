package threads;

import fly.Flying;
import lombok.Getter;
import lombok.Setter;
import message.MessageService;
import ship.Rocket;
import ship.stages.RocketStage;
@Getter
@Setter
public class RunnableShip implements Runnable {
    private static final double MIN_TIME = 1;
    private static final int START_BRAKE_DISTANCE = 349_881_000;
    private static final double FINAL_DISTANCE = 350_000_000;
    private static final int MAX_SPEED_LANDING = 100;
    private static final int MIN_SPEED_LANDING = 0;
    Rocket rocket;
    RocketStage rocketStage;
    MessageService messageService;

    public RunnableShip(Rocket rocket, MessageService messageService) {
        this.rocket = rocket;
        this.messageService = messageService;
    }

    @Override
    public void run() {
        String message;
        System.out.println("Мы в потоке Ракеты");

        Flying flying = new Flying();

        int numRocketStage = 1;

//        try {
//            message = messageService.get();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

//        if (!message.equals("Запуск")) {
//            System.out.println("Полет откладывается.");
//            rocket.setFly(false);
//        }
//        else {
//            rocket.setFly(true);
//        }

        rocket.setFly(true);
        rocketStage = rocket.getNextRocketStage(numRocketStage);
        RocketStage rocketStageBrake = rocket.getSpaceCraft().getBrakeStage();

        double time = Math.min(rocketStage.getRemainingTime(), MIN_TIME);

        int numIter = 0;

        while (flying.isRocketOperable() && rocket.isFly()) {
            numIter++;
            flying.calcDistance(rocket, rocketStage, time);
            if (numIter == 134455)
                System.out.println("дошли");

            if (flying.isRocketOperable()) {
                if (numIter < 5)
                    flying.checkStartFlying(rocket.getDistance());
                else flying.checkDown(rocket.getDistance());

                if (flying.isStartBrake())
                    checkRightLandingSpeed(flying);

                if (flying.checkStartBrake(rocket) && !flying.isStartBrake()) {//(rocket.getDistance() >= START_BRAKE_DISTANCE && !flying.isStartBrake() && rocket.getSpeed() > MAX_SPEED_LANDING) {
                    flying.setStartBrake(true);
                    flying.setFuelIsEmpty(true);
                    rocketStage = rocketStageBrake;

                    System.out.println("Начинаем тормозить.");
                } else {
                    if (rocket.getDistance() >= FINAL_DISTANCE) {
                        getFlyingResult(flying);
                    } else {
                        if ((!flying.isFuelIsEmpty() || flying.isStartBrake()) && !flying.isEndBrake()) {
                            time = calcTimeStep(flying);
                        }
                    }
                }

            }
        }//задача торможения автомобиля
        rocket.setFly(false);

        System.out.println("Полет завершен");
        System.out.println(numIter);
    }
    public void checkRightLandingSpeed(Flying flying) {
        if (rocket.getSpeed() <= MIN_SPEED_LANDING) {
            flying.setStartBrake(false);
            flying.setEndBrake(true);
            flying.setRightLanding(true);
            System.out.println("Ракета вышла на правильную скорость для посадки");
        }
    }
    public double calcTimeStep(Flying flying) {
        double time = Math.min(rocketStage.getRemainingTime(), MIN_TIME);

        if (time == 0) {
            if (!flying.isFuelIsEmpty()) {
                int numRocketStage = rocketStage.getNum();
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
        return time;
    }

    public void getFlyingResult(Flying flying) {
        if (flying.isRightLanding() && rocket.getSpeed() < MAX_SPEED_LANDING) {
            System.out.println("Ракета села на Луну");
            flying.setRocketOperable(false);
        } else {
            System.out.println("Ракета разбилась");
            flying.setRocketOperable(false);
        }
    }
}
