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
    private static final String SENDER = "ROCKET";
    private static final String MESSAGE_TO = "MANAGER";
    Rocket rocket;
    RocketStage rocketStage;
    MessageService messageService;

    public RunnableShip(Rocket rocket, MessageService messageService) {
        this.rocket = rocket;
        this.messageService = messageService;
    }

    @Override
    public void run() {
        Flying flying = new Flying();
        Thread t = Thread.currentThread();
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
//            try {
//                t.sleep(10);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }

            flying.calcDistance(rocket, rocketStage, time);

            if (flying.isRocketOperable()) {
                if (numIter < 5)
                    flying.checkStartFlying(rocket.getDistance());
                else flying.checkDown(rocket.getDistance());

                if (flying.isStartBrake()) {
                    try {
                        checkRightLandingSpeed(flying);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (flying.checkStartBrake(rocket) && !flying.isStartBrake() && rocket.getSpeed() > MAX_SPEED_LANDING) {//(rocket.getDistance() >= START_BRAKE_DISTANCE && !flying.isStartBrake() && rocket.getSpeed() > MAX_SPEED_LANDING) {
                    flying.setStartBrake(true);
                    flying.setFuelIsEmpty(true);
                    rocketStage = rocketStageBrake;

                    try {
                        sendMessage("Начинаем тормозить.");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    if (rocket.getDistance() >= FINAL_DISTANCE) {
                        getFlyingResult(flying);
                    } else {
                        if ((!flying.isFuelIsEmpty() || flying.isStartBrake()) && !flying.isEndBrake()) {
                            try {
                                time = calcTimeStep(flying);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }
        rocket.setFly(false);

        try {
            sendMessage("Полет завершен");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sendFlyInfo();
    }
    public void checkRightLandingSpeed(Flying flying) throws InterruptedException {
        if (rocket.getSpeed() <= MIN_SPEED_LANDING) {
            flying.setStartBrake(false);
            flying.setEndBrake(true);
            flying.setRightLanding(true);
            sendMessage("Ракета вышла на правильную скорость для посадки");
            sendFlyInfo();
        }
    }
    public double calcTimeStep(Flying flying) throws InterruptedException {
        double time = Math.min(rocketStage.getRemainingTime(), MIN_TIME);

        if (time == 0) {
            if (!flying.isFuelIsEmpty()) {
                sendMessage("Топливо в ступени №" + rocketStage.getNum() + " закончилось.");
                int numRocketStage = rocketStage.getNum();
                rocket.deleteRocketStage(numRocketStage);
                numRocketStage++;
                try {
                    rocketStage = rocket.getNextRocketStage(numRocketStage);
                    time = Math.min(rocketStage.getRemainingTime(), MIN_TIME);
                    sendMessage("Запущена ступень №" + rocketStage.getNum());

                    sendFlyInfo();
                } catch (Exception e) {
                    flying.setFuelIsEmpty(true);
                    time = MIN_TIME;
                    sendMessage("Топливо в ступенях закончилось.");

                    sendFlyInfo();
                }
            } else {
                sendMessage("Топливо в тормозном блоке закончилось, ракета осталась без топлива");
                flying.setStartBrake(false);
                flying.setEndBrake(true);
                time = MIN_TIME;

//                sendFlyInfo();
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
    private void sendFlyInfo() {
        String flyInfo = "Пройденное расстояние = " + rocket.getDistance() + "м, масса = " +
                rocket.getAllMass() + "кг, скорость " + rocket.getSpeed() + "м/с";

        try {
            sendMessage(flyInfo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(String text) throws InterruptedException {
        messageService.putMessage(text, SENDER, MESSAGE_TO);
    }
}
