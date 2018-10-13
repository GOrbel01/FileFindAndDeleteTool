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
        fileLabel = new Label();
        isSelected = new CheckBox();
        this.items = items;
        loadFXML();
        isSelected.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!getItem().isSelected()) {
                    System.out.println("Selected: " + getItem().getAbsolutePath() + " ID: " + getItem().getId());
//                    getItem().setSelected(true);
                    items.get(items.indexOf(getItem())).setSelected(true);
                } else {
                    System.out.println("UnSelected: " + getItem().getAbsolutePath() + " ID: " + getItem().getId());
                    items.get(items.indexOf(getItem())).setSelected(false);
                }
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
            isSelected.setSelected(false);
            fileLabel.setText(item.getAbsolutePath());
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
