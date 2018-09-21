package com.gorb.file;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public class FileListAssembler {
    private FileFilter fileFilter;

    public FileListAssembler(String searchText, boolean isNameMode, boolean isExactMatch, boolean isExcludeSearch) {
        this.fileFilter = buildFileFilter(searchText, isNameMode, isExactMatch, isExcludeSearch);
    }

    public void assembleList(List<FileResult> itemList, List<FileResult> buildList) {
        for (FileResult file : buildList) {
            if (file.isDirectory()) {
                assembleList(itemList, file.listFiles(fileFilter));
            } else {
                itemList.add(file);
            }
        }
    }

    private FileFilter buildFileFilter(String searchText, boolean isNameMode, boolean isExactMatch, boolean isExcludeSearch) {
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                String fName = null;
                if (isNameMode) {
                    if (!name.contains(".")) fName = name;
                    else fName = name.substring(0, name.indexOf("."));
                    if (searchText.equalsIgnoreCase("")) {
                        return true;
                    } else {
                        if (pathname.isDirectory()) {
                            return true;
                        }
                        if (searchText.length() >= 3) {
                            if (isExcludeSearch) {
                                if (isExactMatch) return !fName.equalsIgnoreCase(searchText);
                                else return !fName.contains(searchText);
                            } else {
                                if (isExactMatch) return fName.equalsIgnoreCase(searchText);
                                else return fName.contains(searchText);
                            }
                        } else {
                            return fName.equalsIgnoreCase(searchText);
                        }
                    }
                } else {
                    if (searchText == null || searchText.isEmpty()) {
                        return true;
                    }
                    if (name.contains(".")) {
                        fName = name.substring(name.lastIndexOf("."), name.length());

                        if (fName.length() > 1) {
                            if (isExcludeSearch) {
                                if (isExactMatch) return !fName.equalsIgnoreCase("." + searchText);
                                else return !fName.contains(searchText);
                            } else {
                                if (isExactMatch) return fName.equalsIgnoreCase("." + searchText);
                                else return fName.contains(searchText);
                            }
                        } else {
                            if (pathname.isDirectory()) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    } else {
                        if (name.equalsIgnoreCase(searchText) || pathname.isDirectory()) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }

            }
        };

        return fileFilter;
    }

    public FileFilter getFileFilter() {
        return fileFilter;
    }
}
