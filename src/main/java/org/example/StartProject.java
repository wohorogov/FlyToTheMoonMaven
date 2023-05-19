package org.example;

import port.Port;
import port.SpacePort;
import ship.*;

import java.util.HashMap;
import java.util.Map;

public class StartProject {
    public void action() {
        //ступени ракеты
        Map<Integer, RocketStage> rocketStageMap = new HashMap() {{
            put(1, RocketStageDefault.builder()
                    .mass(30_000)
                    .fuelMass(430_000)
                    .remainingTime(120)
                    .fuelConsumptionSpeed(3600)
                    .speedGas(8000)
                    .num(1)
                    .build());
            put(2, RocketStageDefault.builder()
                    .mass(11_000)
                    .fuelMass(167_000)
                    .remainingTime(211)
                    .fuelConsumptionSpeed(800)
                    .speedGas(4000)
                    .num(2)
                    .build());
            put(3, RocketStageDefault.builder()
                    .mass(43_000)
                    .fuelMass(43_000)
                    .remainingTime(240)
                    .fuelConsumptionSpeed(180)
                    .speedGas(3250)
                    .num(3)
                    .build());
        }};
        //Тормозной блок
        RocketStage rocketBrakeStage = RocketBrakeStage.builder()
                .mass(300)
                .fuelMass(300)
                .fuelConsumptionSpeed(30)
                .speedGas(3000)
                .build();

        //Луноход
        MoonWalker moonWalker = new MoonWalkerDefault(180);
        //Космический аппарат с тормозным блоком и луноходом
        SpaceCraft spaceCraft = SpaceCraft.builder()
                .brakeStage((RocketBrakeStage) rocketBrakeStage)
                .moonWalker(moonWalker)
                .build();

        //Сборка ракеты
        Rocket rocket = Rocket.builder()
                .rocketStage(rocketStageMap)
                .distance(0)
                .speed(0)
                .spaceCraft(spaceCraft)
                .build();

        Port port = new SpacePort();
        port.mount(rocket);

        if (port.test()) {
            port.launch();
        }
    }
}
