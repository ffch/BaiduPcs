package com.cff.ui.file;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
* @author root
*右侧文件显示
*/
public class FileList extends JList {
    //PathNode theNode;
    FileListModel dataModel;
    static final long serialVersionUID = 10;

    /**
    * 
    */
    public FileList() {
        dataModel = new FileListModel();
        setModel(dataModel);
        this.setCellRenderer(new MyCellRenderer());
    }

    public void fireTreeSelectionChanged(I_fileSystem node) {
        //Vector files = node.getFiles();
        //theNode = node;
        dataModel.setNode(node);
        updateUI();
    }
}
