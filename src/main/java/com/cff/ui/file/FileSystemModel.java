package com.cff.ui.file;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXBusyLabel;

public class FileSystemModel implements TreeModel {
    I_fileSystem theRoot;
    char fileType = I_fileSystem.DIRECTORY;

    public FileSystemModel(I_fileSystem fs) {
        theRoot = fs;
    }

    public Object getRoot() {
        return theRoot;
    }

    public Object getChild(Object parent, int index) {
        return ((I_fileSystem) parent).getChild(fileType, index);
    }

    public int getChildCount(Object parent) {
        return ((I_fileSystem) parent).getChildCount(fileType);
    }

    public boolean isLeaf(Object node) {
        return ((I_fileSystem) node).isLeaf(fileType);
    }

    public int getIndexOfChild(Object parent, Object child) {
        return ((I_fileSystem) parent).getIndexOfChild(fileType, child);
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    public void addTreeModelListener(TreeModelListener l) {
    }

    public void removeTreeModelListener(TreeModelListener l) {
    }
}