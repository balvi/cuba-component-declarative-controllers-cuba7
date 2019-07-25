package de.balvi.cuba.declarativecontrollers.web.editor;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;

import javax.inject.Inject;

public class AnnotatableStandardEditor<T extends Entity> extends StandardEditor<T> {

    @Inject
    protected EditorAnnotationDispatcher editorAnnotationDispatcher;

    @Subscribe
    private void onInit(InitEvent event) {
        editorAnnotationDispatcher.executeOnInit(this);

    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        editorAnnotationDispatcher.executeonBeforeShow(this);

    }


}
