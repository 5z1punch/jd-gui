/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package org.jd.gui.service.sourcesaver;

import org.jd.gui.api.API;
import org.jd.gui.api.model.Container;
import org.jd.gui.spi.SourceSaver;
import org.jd.gui.util.exception.ExceptionUtil;
import org.jd.gui.util.log.ServiceLogImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class DirectorySourceSaverProvider extends AbstractSourceSaverProvider {

    @Override public String[] getSelectors() { return appendSelectors("*:dir:*"); }

    @Override public String getSourcePath(Container.Entry entry) { return entry.getPath() + ".src.zip"; }

    @Override public int getFileCount(API api, Container.Entry entry) { return getFileCount(api, entry.getChildren()); }

    protected int getFileCount(API api, Collection<Container.Entry> entries) {
        int count = 0;
        int all = entries.size();
        ServiceLogImpl.logger.debug("There are "+all+" entries in the current working directory");
        ServiceLogImpl.logger.debug("Start traversing the directory...");
// For Debug
// int a=0;
        for (Container.Entry e : entries) {
            try {
                if (count % 256 == 0) {
                    ServiceLogImpl.logger.debug("[" + count + "/" + all + "] DOWN");
                    ServiceLogImpl.logger.debug("Current working on " + e.getPath());
                }
//if(e.getPath().equals("apps_res/plugin/USBKey/js/i18n/zh-tw.js")){
//    ServiceLogImpl.logger.debug("Current working on "+e.getPath());
//    a=1;
//}
//if(a==1){
//    ServiceLogImpl.logger.debug("Current working on "+e.getPath());
//}
                SourceSaver sourceSaver = api.getSourceSaver(e);

                if (sourceSaver != null) {
                    count += sourceSaver.getFileCount(api, e);
                }
            }
            catch (Throwable err){
                ServiceLogImpl.logger.error("Throw error when getFileCount, will skip and continue: ");
                assert ExceptionUtil.printStackTrace(err);
            }
        }

        return count;
    }

    @Override
    public void save(API api, SourceSaver.Controller controller, SourceSaver.Listener listener, Path rootPath, Container.Entry entry) {
        Path path = rootPath.resolve(entry.getPath());

        try {
            Files.createDirectories(path);
            saveContent(api, controller, listener, rootPath, path, entry);
        } catch (Throwable e) {
            assert ExceptionUtil.printStackTrace(e);
        }
    }

    @Override
    public void saveContent(API api, SourceSaver.Controller controller, SourceSaver.Listener listener, Path rootPath, Path path, Container.Entry entry) {
        for (Container.Entry e : getChildren(entry)) {
            try {
                if (controller.isCancelled()) {
                    break;
                }
                if(e==null){
                    continue;
                }

                SourceSaver sourceSaver = api.getSourceSaver(e);

                if (sourceSaver != null) {
                    sourceSaver.save(api, controller, listener, rootPath, e);
                }
            }
            catch (Throwable err){
                ServiceLogImpl.logger.error("Throw error when saveContent, will skip and continue: ");
                assert ExceptionUtil.printStackTrace(err);
            }
        }
    }

    protected Collection<Container.Entry> getChildren(Container.Entry entry) {
        try{
            return entry.getChildren();
        }
        catch (Throwable e){
            ServiceLogImpl.logger.error("Throw error when getChildren, will skip and continue: ");
            assert ExceptionUtil.printStackTrace(e);
            return null;
        }
    }
}
