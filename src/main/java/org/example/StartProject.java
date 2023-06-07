package org.example;

import message.MessageService;
import ship.*;
import ship.spacecraft.MoonWalker;
import ship.spacecraft.MoonWalkerDefault;
import ship.spacecraft.RocketBrakeStage;
import ship.spacecraft.SpaceCraft;
import ship.stages.RocketStage;
import ship.stages.RocketStageDefault;
import threads.RunnableManager;

import java.util.HashMap;
import java.util.Map;

public class StartProject {
    public Rocket buildRocket() {
        //ступени ракеты
        Map<Integer, RocketStage> rocketStageMap = new HashMap() {{
            put(1, RocketStageDefault.builder()
                    .mass(30_000)
                    .fuelMass(430_000)
                    .remainingTime(120)
                    .fuelConsumptionSpeed(4000)
                    .speedGas(6000)
                    .num(1)
                    .build());
            put(2, RocketStageDefault.builder()
                    .mass(11_000)
                    .fuelMass(167_000)
                    .remainingTime(211)
                    .fuelConsumptionSpeed(1000)
                    .speedGas(5000)
                    .num(2)
                    .build());
            put(3, RocketStageDefault.builder()
                    .mass(43_000)
                    .fuelMass(93_000)
                    .remainingTime(240)
                    .fuelConsumptionSpeed(1080)
                    .speedGas(4250)
                    .num(3)
                    .build());
        }};
        //Тормозной блок
        RocketBrakeStage rocketBrakeStage = RocketBrakeStage.builder()
                .mass(600)
                .fuelMass(3000)
                .fuelConsumptionSpeed(30)
                .speedGas(3900)
                .build();

        //Луноход
        MoonWalker moonWalker = new MoonWalkerDefault(180);
        //Космический аппарат с тормозным блоком и луноходом
        SpaceCraft spaceCraft = SpaceCraft.builder()
                .brakeStage(rocketBrakeStage)
                .moonWalker(moonWalker)
                .build();

        //Сборка ракеты
        return Rocket.builder()
                .rocketStage(rocketStageMap)
                .distance(0)
                .speed(0)
                .spaceCraft(spaceCraft)
                .build();
    }
    public void action() {
        Rocket rocket = buildRocket();
        MessageService messageService = new MessageService();
        Thread threadManager = new Thread(new RunnableManager(rocket, messageService));
        threadManager.start();
    }
}
