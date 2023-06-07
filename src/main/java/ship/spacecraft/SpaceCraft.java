package ship.spacecraft;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class SpaceCraft {
    private MoonWalker moonWalker;
    private RocketBrakeStage brakeStage;
    public double getAllMass() {
        return brakeStage.getAllMass() + moonWalker.getMass();
    }
    public void printFullInfo() {
        brakeStage.printFullInfo();
        moonWalker.printFullInfo();
    }
}
