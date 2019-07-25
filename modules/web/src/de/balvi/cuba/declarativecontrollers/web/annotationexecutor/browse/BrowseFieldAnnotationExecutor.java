package de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse;

import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.screen.StandardLookup;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AnnotationExecutor;

import java.lang.annotation.Annotation;

public interface BrowseFieldAnnotationExecutor<A extends Annotation, T extends Component> extends AnnotationExecutor {

    void init(A annotation, StandardLookup browse, T target);

    void ready(A annotation, StandardLookup browse, T target);

}