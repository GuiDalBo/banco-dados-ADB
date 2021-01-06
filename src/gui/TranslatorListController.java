package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Translator;
import model.services.TranslatorService;

public class TranslatorListController implements Initializable{
	
	private TranslatorService service;
	
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
	
	private ObservableList<Translator> obsList;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("Oi, Eu sou o Goku! Botão Novo Funciona");
	}
	
	public void setTranslatorService(TranslatorService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
		tableColumnCellPhone.setCellValueFactory(new PropertyValueFactory<>("Cellphone"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
		tableColumnRG.setCellValueFactory(new PropertyValueFactory<>("Rg"));
		tableColumnCPF.setCellValueFactory(new PropertyValueFactory<>("Cpf"));
		
		
		//Fazer isso para largura na tela inicial com os botões
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewTranslator.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Translator> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewTranslator.setItems(obsList);
	}
	
}
