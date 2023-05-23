package ship;

public class MoonWalkerDefault implements MoonWalker{
    double mass;

    public MoonWalkerDefault(double mass) {
        this.mass = mass;
    }

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
}
