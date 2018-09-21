package com.gorb.file;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class FileListCell extends ListCell<FileResult> {
    @FXML
    private Label fileLabel;

    @FXML
    private CheckBox isSelected;

    private ObservableList<FileResult> items;

    public FileListCell(ObservableList<FileResult> items) {
        this.items = items;
        loadFXML();
        isSelected.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Selected: " + getItem().getAbsolutePath());
            }
        });
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("file_cell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void updateItem(FileResult item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            fileLabel.setText(item.getAbsolutePath());
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
