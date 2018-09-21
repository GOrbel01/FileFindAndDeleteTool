package com.gorb.file;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileResult {
    private File file;
    private boolean isSelected;

    public FileResult() {

    }

    public FileResult(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAbsolutePath() {
        return file.getAbsolutePath();
    }

    public boolean isDirectory() {
        return file.isDirectory();
    }

    public List<FileResult> listFiles(FileFilter filter) {
        List<FileResult> fileResults = new ArrayList<>();
        List<File> filterList = Arrays.asList(file.listFiles(filter));
        for (File f : filterList) {
            fileResults.add(new FileResult(f));
        }
        return fileResults;
    }
}
