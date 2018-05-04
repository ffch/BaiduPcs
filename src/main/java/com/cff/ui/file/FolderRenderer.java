package com.cff.ui.file;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class FolderRenderer extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = 1L;

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row,    boolean hasFocus) {
        I_fileSystem node = (I_fileSystem) value;
        Icon icon = node.getIcon();

        setLeafIcon(icon);
        setOpenIcon(icon);
        setClosedIcon(icon);

        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    }
}
