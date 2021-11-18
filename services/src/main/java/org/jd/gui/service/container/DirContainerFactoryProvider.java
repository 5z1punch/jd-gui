package org.jd.gui.service.container;

import org.jd.gui.api.API;
import org.jd.gui.api.model.Container;
import org.jd.gui.model.container.DirContainer;
import org.jd.gui.model.container.JarContainer;
import org.jd.gui.spi.ContainerFactory;

import java.nio.file.Files;
import java.nio.file.Path;

public class DirContainerFactoryProvider implements ContainerFactory {
    @Override
    public String getType() {
        return "dir";
    }

    @Override
    public boolean accept(API api, Path rootPath) {
        return rootPath.getFileName()!=null && Files.isDirectory(rootPath);
    }

    @Override
    public Container make(API api, Container.Entry parentEntry, Path rootPath) {
        return new DirContainer(api, parentEntry, rootPath);
    }
}
