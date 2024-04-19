package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.io.IOException;
import java.lang.module.ModuleFinder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.stream.Collectors.toList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameLoader extends Application {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();

    public Pane gameWindow;
    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServiceList;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;

    GameLoader(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServiceList, List<IPostEntityProcessingService> postEntityProcessingServices) {
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServiceList = entityProcessingServiceList;
        this.postEntityProcessingServices = postEntityProcessingServices;
    }

    public static void main(String[] args) {
//        var layer = createLayer(args[0], "dk.sdu.mmmi.cbse.common.bulletr");
//        var services = ServiceLoader.load(layer, Bullet.class);
//        services.stream()
//                .map(ServiceLoader.Provider::get)
//                .forEach(confProvider ->
//                        System.out.println(confProvider.getID())
//                );
        launch(GameLoader.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        Text text = new Text(10, 20, "Destroyed asteroids: 0");
        gameWindow = new Pane();
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(text);
        callScoreService("http://localhost:8080/resetScore", true);

        BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, null, null);
        Background background = new Background(backgroundFill);
        gameWindow.setBackground(background);

        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }

        });

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygon.setFill(Color.WHITE);
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);

        }

        render();

        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();

    }

    protected void render() {
        new AnimationTimer() {
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                long currentTime = System.nanoTime();
                double deltaTime = (currentTime - lastTime) / 1_000_000_000.5; //convert nanoseconds to seconds
                lastTime = currentTime;
                update(deltaTime);
                draw();
                gameData.getKeys().update();
            }

        }.start();
    }

    private void update(double deltaTime) {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world, deltaTime);
        }

        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world, deltaTime);
        }
    }

    private void draw() {
        List<Entity> entitiesToRemove = new ArrayList<>();

        for (Entity entity : polygons.keySet()) {
            Polygon polygon = polygons.get(entity);
            if (polygon != null) {
                if (entity.isEnabled()) {
                    polygon.setTranslateX(entity.getX());
                    polygon.setTranslateY(entity.getY());
                    polygon.setRotate(entity.getRotation());
                } else {
                    entitiesToRemove.add(entity);
                }
            }
        }

        for (Entity entity : polygons.keySet()){
            if (!world.getEntities().contains(entity)) {
                gameWindow.getChildren().remove(polygons.get(entity));
                polygons.remove(entity);
            }
        }


        for (Entity entity : world.getEntities()) {
            if (entity.isEnabled() && !polygons.containsKey(entity)) {
                Polygon polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
        }

        Text textNode = (Text) gameWindow.getChildren().get(0);
        textNode.setText("Destroyed asteroids: " + callScoreService("http://localhost:8080/getScore", false));
    }


    private Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private static ModuleLayer createLayer(String from, String module) {
        var finder = ModuleFinder.of(Paths.get(from));
        var parent = ModuleLayer.boot();
        var cf = parent.configuration().resolve(finder, ModuleFinder.of(), Set.of(module));
        return parent.defineModulesWithOneLoader(cf, ClassLoader.getSystemClassLoader());
    }

    public int callScoreService(String url, boolean isVoid){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        int scoreInt = 0;
        try {
            HttpResponse<String> score = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(isVoid != true) {
                scoreInt = Integer.parseInt(score.body());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return  scoreInt;
    }

}
