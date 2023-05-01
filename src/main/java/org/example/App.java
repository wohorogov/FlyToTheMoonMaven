package org.example;

import ship.Rocket;
import ship.RocketStage;
import ship.RocketStageDefault;

import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Map<int, RocketStage> rocketStageMap = new HashMap<int, RocketStage>() {{
            put(1, RocketStageDefault.builder().build());
            put(2, RocketStageDefault.builder().build());
            put(3, RocketStageDefault.builder().build());
            put(4, RocketStageDefault.builder().build());
        }};
        Rocket rocket = Rocket.builder().rocketStage(rocketStageMap).build();

    }
}
