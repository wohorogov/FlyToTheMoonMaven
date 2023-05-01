package org.example;

import port.Port;
import port.SpacePort;
import ship.Rocket;
import ship.RocketBrakeStage;
import ship.RocketStage;
import ship.RocketStageDefault;

import java.util.HashMap;
import java.util.Map;

public class TestApp {
    public void action() {
        Map<Integer, RocketStage> rocketStageMap = new HashMap<Integer, RocketStage>() {{
            put(1, RocketStageDefault.builder().mass(30_000).fuelMass(430_000).remainingTime(120).fuelConsumptionSpeed(3600).speedGas(2.8).build());
            put(2, RocketStageDefault.builder().mass(11_000).fuelMass(167_000).remainingTime(211).fuelConsumptionSpeed(800).speedGas(3).build());
            put(3, RocketStageDefault.builder().mass(43_000).fuelMass(43_000).remainingTime(240).fuelConsumptionSpeed(180).speedGas(3.25).build());
        }};

        Rocket rocket = Rocket.builder().rocketStage(rocketStageMap).build();

        RocketStage rocketBrakeStage = RocketBrakeStage.builder().mass(300).fuelMass(300).fuelConsumptionSpeed(30).speedGas(3).build();
        Port port = new SpacePort();
        port.mount(rocket);

        //тест ракеты
        String testResult = port.test();
        if (testResult == "OK") {
            System.out.println("Тестирование ракеты прошло успешно");
            port.launch();
        } else
            System.out.println(testResult);


    }
}
