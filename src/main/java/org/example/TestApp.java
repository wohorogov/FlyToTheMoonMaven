package org.example;

import port.Port;
import port.SpacePort;
import ship.*;

import java.util.HashMap;
import java.util.Map;

public class TestApp {
    public void action() {
        Map<Integer, RocketStage> rocketStageMap = new HashMap() {{
            put(1, RocketStageDefault.builder()
                    .mass(30_000)
                    .fuelMass(430_000)
                    .speedGas(2800)
                    .remainingTime(120)
                    .fuelConsumptionSpeed(3600)
                    .num(1)
                    .build());
            put(2, RocketStageDefault.builder()
                    .mass(11_000)
                    .fuelMass(167_000)
                    .remainingTime(211)
                    .fuelConsumptionSpeed(800)
                    .speedGas(3000)
                    .num(2)
                    .build());
            put(3, RocketStageDefault.builder()
                    .mass(43_000)
                    .fuelMass(43_000)
                    .remainingTime(240)
                    .fuelConsumptionSpeed(180)
                    .speedGas(3.25)
                    .num(3)
                    .build());
        }};

        RocketStage rocketBrakeStage = RocketBrakeStage.builder()
                .mass(300)
                .fuelMass(300)
                .fuelConsumptionSpeed(30)
                .speedGas(3000)
                .build();
        SpaceCraft spaceCraft = SpaceCraft.builder()
                .brakeStage((RocketBrakeStage) rocketBrakeStage)
                .moonWalker(new MoonWalker() {

                    double mass = 180;
                    @Override
                    public void moveForward() {
                        System.out.println("Шаг вперед");
                    }

                    @Override
                    public void moveBack() {
                        System.out.println("Шаг назад");
                    }

                    @Override
                    public void turnLeft() {
                        System.out.println("Шаг влево");
                    }

                    @Override
                    public void turnRight() {
                        System.out.println("Шаг вправо");
                    }

                    @Override
                    public void takeShot() {
                        System.out.println("Снимок сделан");
                    }

                    @Override
                    public double getMass() {
                        return mass;
                    }
                })
                .build();

        Rocket rocket = Rocket.builder()
                .rocketStage(rocketStageMap)
                .coordinate(0)
                .speed(0)
                .spaceCraft(spaceCraft)
                .build();

        Port port = new SpacePort();
        port.mount(rocket);

        //тест ракеты
//        switch (System.in) {
//            case 1: String testResult = port.test();
//                    break;
//            case 2:
//        }
        String testResult = port.test();
        if (testResult == "OK") {
            System.out.println("Тест ракеты прошел успешно. Осуществляется запуск ракеты.");
            port.launch();
        }
//        if (testResult == "OK") {
//            System.out.println("Тестирование ракеты прошло успешно. Выполняется запуск ракеты.");
//            port.launch();
//        } else
//            System.out.println(testResult);


    }
}
