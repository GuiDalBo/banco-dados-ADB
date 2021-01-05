package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Translator;

public class TranslatorListController implements Initializable{
	
	@FXML
	private TableView<Translator> tableViewTranslator;
	
	@FXML
	private TableColumn<Translator, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Translator, String> tableColumnName;
	
	@FXML
	private TableColumn<Translator, String> tableColumnPhone;
	
	@FXML
	private TableColumn<Translator, String> tableColumnCellPhone;
	
	@FXML
	private TableColumn<Translator, String> tableColumnEmail;
	
	@FXML
	private TableColumn<Translator, String> tableColumnRG;
	
	@FXML
	private TableColumn<Translator, String> tableColumnCPF;
	
	@FXML
	private Button btNew;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("Oi, Eu sou o Goku! Botão Novo Funciona");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewTranslator.prefHeightProperty().bind(stage.heightProperty());
		
		
	}

}
