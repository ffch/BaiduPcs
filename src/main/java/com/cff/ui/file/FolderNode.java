package com.cff.ui.file;

import java.io.File;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

public class FolderNode implements I_fileSystem {
    private static boolean showHiden = true;;
    private PcsFile theFile;
    private Vector<PcsFile> all = new Vector<PcsFile>();
    private Vector<PcsFile> folder = new Vector<PcsFile>();
    
    /**
    * set that whether apply hiden file.
    * @param ifshow
       */
    public void setShowHiden(boolean ifshow) {
        showHiden = ifshow;
    }

    public Icon getIcon() {
        return PcsFileSystem.getFileIcon(String.valueOf(theFile.getFileType()));
    }

    public String toString() {
        return theFile.getFileName();
    }
    
    public String getAbsPath(){
    	return theFile.getPath();
    }

    /**
    * create a root node. by default, it should be the DeskTop in window file system.
       */
    public FolderNode() {
        theFile = new PcsFile(I_fileSystem.DIRECTORY,"/");
        prepareChildren();
    }
    
    private void prepareChildren() {
    	PcsFile[] files = PcsFileSystem.getFiles(theFile, showHiden);
    	if(files == null)return;
        for (int i = 0; i < files.length; i++) {
            all.add(files[i]);
            if (files[i].isDirectory() && !files[i].toString().toLowerCase().endsWith(".lnk")) {
                folder.add(files[i]);
            }
        }
    }

    private FolderNode(PcsFile file) {
        theFile = file;
        prepareChildren();
    }

    public FolderNode getChild(char fileType, int index) {
        if (I_fileSystem.DIRECTORY == fileType) {
            return new FolderNode(folder.get(index));
        } else if (I_fileSystem.ALL == fileType) {
            return new FolderNode(all.get(index));
        } else if (I_fileSystem.FILE == fileType) {
            return null;
        } else {
            return null;
        }
    }

    public int getChildCount(char fileType) {
        if (I_fileSystem.DIRECTORY == fileType) {
            return folder.size();
        } else if (I_fileSystem.ALL == fileType) {
            return all.size();
        } else if (I_fileSystem.FILE == fileType) {
            return -1;
        } else {
            return -1;
        }
    }

    public boolean isLeaf(char fileType) {
        if (I_fileSystem.DIRECTORY == fileType) {
            return folder.size() == 0;
        } else if (I_fileSystem.ALL == fileType) {
            return all.size() == 0;
        } else if (I_fileSystem.FILE == fileType) {
            return true;
        } else {
            return true;
        }
    }

    public int getIndexOfChild(char fileType, Object child) {
        if (child instanceof FolderNode) {
            if (I_fileSystem.DIRECTORY == fileType) {
                return folder.indexOf(((FolderNode) child).theFile);
            } else if (I_fileSystem.ALL == fileType) {
                return all.indexOf(((FolderNode) child).theFile);
            } else if (I_fileSystem.FILE == fileType) {
                return -1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}

