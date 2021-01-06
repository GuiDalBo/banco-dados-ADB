package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.TranslatorService;

public class MainViewController implements Initializable{
	
	//@FXML
	//private MenuItem menuItemBuscaPorNome;
	
	@FXML
	private Button btSearch;
	
	@FXML
	private Button btAdd;
	
	@FXML
	private Button btAbout;
	
	@FXML
	private Button btTranslators;
	
	@FXML
	public void onBtSearchAction() {
		loadView("/gui/About.fxml", x -> {});
		System.out.println("Oi, Eu sou o Goku! Botão Busca Funciona");
	}
	
	@FXML
	public void onBtAddAction() {
		System.out.println("Oi, Eu sou o Goku! Botão Adicionar Funciona");
	}
	
	@FXML
	public void onBtTradutorAction() {
		loadView("/gui/TranslatorList.fxml", (TranslatorListController controller) -> {
			controller.setTranslatorService(new TranslatorService());
			controller.updateTableView();
		});
		System.out.println("Oi eu não sou o Goku! Botão Tradutores Funciona");
	}
	
	@FXML
	public void onBtAboutAction() {
		loadView("/gui/About.fxml", x -> {});
		System.out.println("Oi, Eu sou o Goku! Botão Sobre Funciona");
	}
	
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}
