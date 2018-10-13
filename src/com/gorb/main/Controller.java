package com.gorb.main;

import com.gorb.file.FileCellFactory;
import com.gorb.file.FileResult;
import com.gorb.search.SearchError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import com.gorb.search.ErrorMessage;
import com.gorb.file.FileListAssembler;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Stage primaryStage;

    @FXML
    private Button fileSearchButton;

    @FXML
    private Button deleteFiles;

    @FXML
    private Button deleteSelected;

    @FXML
    private TextField fileTextField;

    @FXML
    private TextField fileExtField;

    @FXML
    private CheckBox matchCheckbox;

    @FXML
    private CheckBox excludeCheckbox;

    @FXML
    private Label countTextLabel;

    @FXML
    private ListView<FileResult> filesListView;

    @FXML
    private RadioButton fileSearchRadioButtonName;

    @FXML
    private RadioButton fileSearchRadioButtonExt;

    private ToggleGroup toggleGroup = new ToggleGroup();
    private ObservableList<FileResult> items = FXCollections.observableArrayList();
    private File dir;

    public Controller() {
        this.deleteFiles = new Button();
        this.fileTextField = new TextField();
        this.countTextLabel = new Label();
        this.fileSearchButton = new Button();
        this.filesListView = new ListView();
        this.excludeCheckbox = new CheckBox();
        this.matchCheckbox = new CheckBox();
        this.deleteSelected = new Button();
        this.fileSearchRadioButtonExt = new RadioButton();
        this.fileSearchRadioButtonName = new RadioButton();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.fileSearchRadioButtonName.setToggleGroup(toggleGroup);
        this.fileSearchRadioButtonName.setSelected(true);
        this.fileSearchRadioButtonExt.setToggleGroup(toggleGroup);
        filesListView.setItems(items);
        filesListView.setCellFactory(new FileCellFactory(items));
    }

    public void onClickDirectoryButton() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        dir = directoryChooser.showDialog(primaryStage);
        System.out.println(dir.getAbsolutePath());
        fileTextField.setText(dir.getAbsolutePath());
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void doSearch() {
        items.clear();
        if (fileTextField.getText().isEmpty()) {
            dir = null;
        }
        if (fileExtField != null && !fileExtField.getText().isEmpty()) {
            if (fileExtField.getText().charAt(0) == '.') {
                fileExtField.setText(fileExtField.getText(1, fileExtField.getText().length()));
            }
        }
        String ext = fileExtField.getText();
        SearchError searchError = findErrors(dir, ext);
        if (searchError.hasError()) {
            countTextLabel.setText("");
            showError(searchError.getFirstError());
        } else {
            FileListAssembler fileListAssembler = new FileListAssembler(fileExtField.getText(), fileSearchRadioButtonName.isSelected(), matchCheckbox.isSelected(), excludeCheckbox.isSelected());
            fileListAssembler.assembleList(items, toFileResultList(Arrays.asList(this.dir.listFiles(fileListAssembler.getFileFilter()))));
            items.sort(Comparator.comparing(f -> f.getFile().getParent()));
            countTextLabel.setText("Found " + items.size() + " files.");
            System.out.println("Count Text: " + countTextLabel);
        }
    }

    public List<FileResult> toFileResultList(List<File> files) {
        List<FileResult> results = new ArrayList<>();
        for (File f : files) {
            results.add(new FileResult(f));
        }
        return results;
    }

    public void doDeleteFiles() {
        if (items.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm Delete");
            alert.setContentText("Delete " + items.size() + " files?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                for (FileResult file : items) {
                    file.getFile().delete();
                }
                doSearch();
            }
        }
    }

    public void doDeleteSelected() {
        List<FileResult> resultsToRemove = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isSelected()) {
                System.out.println("Confirmed: " + items.get(i).getId());
//                items.get(i).getFile().delete();
                items.get(i).setSelected(false);
                resultsToRemove.add(items.get(i));
            }
        }

        for (FileResult fr : resultsToRemove) {
            items.remove(fr);
        }

    }

    private void showError(ErrorMessage errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage.getValue());
        alert.showAndWait();
    }

    private SearchError findErrors(File file, String fileExt) {
        SearchError searchError = new SearchError();
        searchError.addError(fileExists(file));
        searchError.addError(isValidExtension(fileExt));
        return searchError;
    }

    private ErrorMessage fileExists(File file) {
        if (file == null) {
            return ErrorMessage.FILE_NOT_EXIST;
        }
        if (!file.exists()) {
            return ErrorMessage.FILE_NOT_EXIST;
        }
        return ErrorMessage.NONE;
    }

    private ErrorMessage isValidExtension(String text) {
        if (!text.isEmpty()) {
            if (text.charAt(text.length() - 1) == '.') {
                return ErrorMessage.EXT_EMPTY;
            }
            if (text.contains(".")) {
                return ErrorMessage.MULTIPLE_DOTS;
            }
        }
        return ErrorMessage.NONE;
    }
}
