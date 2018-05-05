package com.cff.ui.file;

import java.awt.Component;
import java.io.File;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXBusyLabel;

/**
* @author root
   *左侧文件树
   */
public class FileTree extends JTree {

    /**
    * 
    */
    public FileTree() {
    }

    static final long serialVersionUID = 0;

    private FileList theList;

    public FileTree(FileList list) {
        theList = list;
        setModel(new FileSystemModel(new FolderNode()));
        this.setCellRenderer(new FolderRenderer());
        addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent tse) {
            }
        });
        this.setSelectionRow(0);
    }

    public void fireValueChanged(TreeSelectionEvent tse) {
        TreePath tp = tse.getNewLeadSelectionPath();
        Object o = tp.getLastPathComponent();
        // theList.fireTreeSelectionChanged((PathNode)o);
        theList.fireTreeSelectionChanged((FolderNode) o);
    }

    public void fireTreeCollapsed(TreePath path) {
        super.fireTreeCollapsed(path);
        TreePath curpath = getSelectionPath();
        if (path.isDescendant(curpath)) {
            setSelectionPath(path);
        }
    }

    public void fireTreeWillExpand(TreePath path) {
        System.out.println("Path will expand is " + path);
    }

    public void fireTreeWillCollapse(TreePath path) {
        System.out.println("Path will collapse is " + path);
    }

    class ExpansionListener implements TreeExpansionListener {
        FileTree tree;

        public ExpansionListener(FileTree ft) {
            tree = ft;
        }

        public void treeCollapsed(TreeExpansionEvent tee) {
        }

        public void treeExpanded(TreeExpansionEvent tee) {
        }
    }
}
