
import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidPlugin;
import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Asteroid {
    requires Common;
    requires CommonAsteroid;
    uses AsteroidSPI;
    provides IGamePluginService with AsteroidPlugin;
    provides AsteroidSPI with dk.sdu.mmmi.cbse.asteroidsystem.AsteroidControlSystem;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.asteroidsystem.AsteroidControlSystem;
    
}
