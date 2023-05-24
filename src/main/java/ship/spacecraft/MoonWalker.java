package ship.spacecraft;

public interface MoonWalker {
    void moveForward();
    void moveBack();
    void turnLeft();
    void turnRight();
    void takeShot();
    double getMass();
    void print_full_info();
}
