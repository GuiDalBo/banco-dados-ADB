package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Translator;
import model.exceptions.ValidationException;
import model.services.TranslatorService;

public class TranslatorFormController implements Initializable{
	
	private Translator entity;
	
	private TranslatorService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private TextField txtPhone;
	
	@FXML
	private TextField txtCellPhone;
	
	@FXML
	private TextField txtRg;
	
	@FXML
	private TextField txtCpf;
	
	@FXML
	private Label labelError;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setTranslator(Translator entity) {
		this.entity = entity;
	}
	
	public void setTranslatorService(TranslatorService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity ==null) {
			throw new IllegalStateException("Entidade vazia");
		}
		if (service == null) {
			throw new IllegalStateException("Serviço vazio");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch (DbException e) {
			Alerts.showAlert("Erro ao Salvar", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private Translator getFormData() {
		Translator obj = new Translator();
		
		ValidationException exception = new ValidationException("Validation Exception");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		obj.setEmail(txtEmail.getText());
		obj.setPhone(txtPhone.getText());
		obj.setCellphone(txtCellPhone.getText());
		obj.setRg(txtRg.getText());
		obj.setCpf(txtCpf.getText());
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
//		initializeNodes();
//		
	}
	
//	private void initializeNodes() {
//		
//		Constraints.setTextFieldInteger(txtPhone);
//		Constraints.setTextFieldInteger(txtCellPhone);
//		Constraints.setTextFieldInteger(txtRg);
//		Constraints.setTextFieldInteger(txtCpf);
//	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entitade está vazia!");
		}
		
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(String.valueOf(entity.getName()));
		txtEmail.setText(String.valueOf(entity.getEmail()));
		txtPhone.setText(String.valueOf(entity.getPhone()));
		txtCellPhone.setText(String.valueOf(entity.getCellphone()));
		txtRg.setText(String.valueOf(entity.getRg()));
		txtCpf.setText(String.valueOf(entity.getCpf()));
	}
	
}
