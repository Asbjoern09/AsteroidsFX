package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface defines the contract for processing entities after the main entity processing, such as collision based on new position.
 */
public interface IPostEntityProcessingService {

    /**
     * Processes entities after the main entity processing step based on the provided game data and world state, such as collision based on new position.
     *
     * Preconditions: gameData must not be null, world must not be null, dt must be greater than or equal to 0
     * Postconditions: the entities have been handled in their updated state, e.g. collisioncheck
     *
     * @param gameData gameData contains and modifies information about the game config, displayheight, keyinputs etc.
     * @param world    world contains entities and their properties, (player, enemies, etc.)
     * @param dt       the time since the last update in seconds (delta time), necessary for the game to run properly on linux.
     */
    void process(GameData gameData, World world, double dt);
}
