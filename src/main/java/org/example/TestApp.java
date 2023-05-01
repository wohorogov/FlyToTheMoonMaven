package org.example;

import ship.Rocket;
import ship.RocketStage;
import ship.RocketStageDefault;

import java.util.HashMap;
import java.util.Map;

public class TestApp {
    public void action() {
        Map<Integer, RocketStage> rocketStageMap = new HashMap<Integer, RocketStage>() {{
            put(1, RocketStageDefault.builder().build());
            put(2, RocketStageDefault.builder().build());
            put(3, RocketStageDefault.builder().build());
            put(4, RocketStageDefault.builder().build());
        }};
        Rocket rocket = Rocket.builder().rocketStage(rocketStageMap).build();
    }
}
