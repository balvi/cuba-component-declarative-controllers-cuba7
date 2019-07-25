package de.balvi.cuba.declarativecontrollers.web.browse;

import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;

import javax.inject.Inject;

public class AnnotatableAbstractLookup extends StandardLookup {

    @Inject
    protected BrowseAnnotationDispatcher browseAnnotationDispatcher;

    @Subscribe
    protected void onInit(InitEvent event) {
        browseAnnotationDispatcher.executeOnInit(this);
    }

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        browseAnnotationDispatcher.onBeforeShow(this);
    }
}
