package com.rana.neonfilesystem.fileManager;

import android.os.Environment;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileManagerHelper {

    private static final String TAG = FileManagerHelper.class.getSimpleName();

    public File[] scan(String path) {
        File root = new File(path);
        File[] filesAndFolders = root.listFiles();
        return filesAndFolders;
    }
}
