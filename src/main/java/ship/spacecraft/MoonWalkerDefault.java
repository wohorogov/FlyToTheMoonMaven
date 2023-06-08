package ship.spacecraft;

import ship.spacecraft.MoonWalker;

public class MoonWalkerDefault implements MoonWalker {
    double mass;

    public MoonWalkerDefault(double mass) {
        this.mass = mass;
    }

    @Override
    public String moveForward() {
        return "Шаг вперед";
    }

    @Override
    public String moveBack() {
        return "Шаг назад";
    }

    @Override
    public String turnLeft() {
        return "Шаг влево";
    }

    @Override
    public String turnRight() {
        return "Шаг вправо";
    }

    @Override
    public String takeShot() {
        return "Снимок сделан";
    }

    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public void printFullInfo() {
        System.out.println("Масса лунохода = " + mass);
    }
}
