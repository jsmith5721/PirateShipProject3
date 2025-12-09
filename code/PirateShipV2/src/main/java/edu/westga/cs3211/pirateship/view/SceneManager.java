package edu.westga.cs3211.pirateship.view;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manages scene changes
 * 
 * @author Kirya Duncan II
 * @version Fall 2025
 */
public class SceneManager {
	private static Stage mainStage;
	private static ArrayList<Scene> scenes = new ArrayList<Scene>();
	
	/*
	 * Sets the main stage to be used for the duration of the application
	 */
	public static void setMainStage(Stage mainStage) {
		SceneManager.mainStage = mainStage;
		SceneManager.scenes.clear();
	}
	
	/*
	 * Updates the stage to the given scene and title
	 */
	public static void moveToScene(Scene nextScene, String sceneTitle) {
		var currentScene = SceneManager.mainStage.getScene();
		SceneManager.scenes.add(currentScene);
		SceneManager.setToScene(nextScene, sceneTitle);
	}
	
	/*
	 * Updates the stage to the most recent scene of a given scene path
	 */
	public static void previousScene(String sceneTitle) {
		var lastScene = SceneManager.scenes.getLast();
		SceneManager.setToScene(lastScene);
		SceneManager.scenes.remove(lastScene);
		SceneManager.setTitle(sceneTitle);
	}
	
	private static void setToScene(Scene toScene) {
		SceneManager.mainStage.setScene(toScene);
		SceneManager.mainStage.show();
	}
	
	private static void setToScene(Scene toScene, String sceneTitle) {
		SceneManager.setToScene(toScene);
		SceneManager.setTitle(sceneTitle);
	}
	
	private static void setTitle(String sceneTitle) {
		SceneManager.mainStage.setTitle(sceneTitle);
	}
}
