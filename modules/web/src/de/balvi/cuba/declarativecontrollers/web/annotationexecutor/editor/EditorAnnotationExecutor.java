package de.balvi.cuba.declarativecontrollers.web.annotationexecutor.editor;

import com.haulmont.cuba.gui.screen.StandardEditor;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AnnotationExecutor;

import java.lang.annotation.Annotation;

public interface EditorAnnotationExecutor<A extends Annotation> extends AnnotationExecutor {

    void init(A annotation, StandardEditor editor);
    void postInit(A annotation, StandardEditor editor);
}