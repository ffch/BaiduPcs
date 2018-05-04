package com.cff.ui.file;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 * 右侧文件model，负责文件选择
 * @author fufei
 *
 */
public class FileListModel implements ListModel {
    FileList theList;
    I_fileSystem node;
    char fileType = I_fileSystem.ALL;
    public void setNode(I_fileSystem node) {
        this.node = node;
    }

    public Object getElementAt(int index) {
        if (node != null) {
            return ((I_fileSystem) node).getChild(fileType, index);
        } else {
            return null;
        }
    }

    public int getSize() {
        if (node != null) {
            return ((I_fileSystem) node).getChildCount(fileType);
        } else {
            return 0;
        }
    }

    public void addListDataListener(ListDataListener l) {

    }

    public void removeListDataListener(ListDataListener l) {
    }
}

