package org.jd.gui.service.fileloader;

import org.jd.gui.api.API;

import java.io.File;

public class DirLoaderProvider extends AbstractFileLoaderProvider{
    @Override public String[] getExtensions() { return null; }
    @Override public String getDescription() { return "Load From Directory (*)"; }

    @Override
    public boolean accept(API api, File file) {
        return file.exists() && file.isDirectory() && file.canRead();
    }

    @Override
    public boolean load(API api, File file) {
        return load(api, file, file.toPath()) != null;
    }
}
