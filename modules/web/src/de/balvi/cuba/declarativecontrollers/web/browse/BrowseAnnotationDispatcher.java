package de.balvi.cuba.declarativecontrollers.web.browse;

import com.haulmont.cuba.gui.screen.StandardLookup;

public interface BrowseAnnotationDispatcher {

    String NAME = "dbcdc_BrowseAnnotationExecutorService";

    void executeOnInit(StandardLookup browse);

    void onBeforeShow(StandardLookup browse);
}