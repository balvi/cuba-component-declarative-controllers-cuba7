package de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse;

import com.haulmont.cuba.gui.screen.StandardLookup;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AnnotationExecutor;

import java.lang.annotation.Annotation;

public interface BrowseAnnotationExecutor<A extends Annotation> extends AnnotationExecutor {

    void init(A annotation, StandardLookup browse);

    void ready(A annotation, StandardLookup browse);
}