package ship.spacecraft;

public interface MoonWalker {
    String moveForward();
    String moveBack();
    String turnLeft();
    String turnRight();
    String takeShot();
    double getMass();
    void printFullInfo();
}
