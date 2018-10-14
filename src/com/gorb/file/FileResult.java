package com.gorb.file;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileResult {
    private static int COUNTER = 1;

    private File file;
    private boolean isSelected;
    private int id;

    public FileResult() {

    }

    public FileResult(File file) {
        this.file = file;
        if (!file.isDirectory()) {
            this.id = COUNTER;
            COUNTER++;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileResult that = (FileResult) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getId() {
        return id;
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
