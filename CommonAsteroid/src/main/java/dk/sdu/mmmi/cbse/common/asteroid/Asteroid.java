package dk.sdu.mmmi.cbse.common.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author Emil
 */
public class Asteroid extends Entity {

    int splitCounter = 2;

    public int getSplitCounter() {
        return splitCounter;
    }

    public void setSplitCounter(int splitCounter) {
        this.splitCounter = splitCounter;
    }
}
