package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Translator;
import model.services.TranslatorService;

public class TranslatorListController implements Initializable, DataChangeListener{
	
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
	private TableColumn<Translator, Translator> tableColumnEDIT;
	
	@FXML
	TableColumn<Translator, Translator> tableColumnREMOVE;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Translator> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Translator obj = new Translator();
		createDialogForm(obj, "/gui/TranslatorForm.fxml", parentStage);
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
		initRemoveButtons();
		initEditButtons();
	}

	@Override
	public void onDataChanged() {
		updateTableView();
		
	}
	
	private void createDialogForm(Translator obj, String absoluteName, Stage parentStage) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			TranslatorFormController controller = loader.getController();
			controller.setTranslator(obj);
			controller.setTranslatorService(new TranslatorService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Cadastro de Tradutor");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
			
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar janela", e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Translator, Translator>() {
			private final Button button = new Button("Ver/Editar");

			@Override
			protected void updateItem(Translator obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/TranslatorForm.fxml", Utils.currentStage(event)));
			}
			
		});
		
	}
	
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Translator, Translator>() {
			private final Button button = new Button("Remover");

			@Override
			protected void updateItem(Translator obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Translator obj) {
		Optional <ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza que gostaria de deletar esse Tradutor?");
		
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Erro ao remover tradutor", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
	
}
