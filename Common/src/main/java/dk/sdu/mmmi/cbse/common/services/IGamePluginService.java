package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * IGamePluginService interface, is an interface with methods for starting and stopping the game
 * <p>
 * Plugins that implement this interface can load into the game engine to add new functionality
 * </p>
 */
public interface IGamePluginService {

    /**
     * Starts the game.
     * <p>
     * Precondition: The gameData and world parameters is not null.
     * Postcondition: The game is started and initialized using gameData and world.
     * </p>
     *
     * @param gameData gameData contains and modifies information about the game config
     * @param world    world contains entities and their properties, (player, enemies, etc.)
     */
    void start(GameData gameData, World world);

    /**
     * Stops the game.
     * <p>
     * Precondition: game must be currently running.
     * Postcondition: game is stopped
     * </p>
     *
     * @param gameData gameData contains and modifies information about the game config
     * @param world    world contains entities and their properties, (player, enemies, etc.)
     */
    void stop(GameData gameData, World world);
}
