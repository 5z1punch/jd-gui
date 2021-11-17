package org.jd.gui.model.container;

import org.jd.gui.api.API;
import org.jd.gui.api.model.Container;
import org.jd.gui.util.exception.ExceptionUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class DirContainer extends GenericContainer {

    public DirContainer(API api, Container.Entry parentEntry, Path rootPath) {
        super(api, parentEntry, rootPath);
        URI uri = parentEntry.getUri();
        try {
            this.root = new Entry(parentEntry, rootPath, new URI(uri.getScheme(), uri.getHost(), uri.getPath() + "/", null)) {
                public Entry newChildEntry(Path fsPath) {
                    return new Entry(parent, fsPath, null);
                }
            };
        }catch (URISyntaxException e) {
            assert ExceptionUtil.printStackTrace(e);
        }
    }
    public String getType() { return "dir"; }
}
