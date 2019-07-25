package de.balvi.cuba.declarativecontrollers.web.annotationexecutor.editor;

import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.screen.StandardEditor;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AnnotationExecutor;

import java.lang.annotation.Annotation;

public interface EditorFieldAnnotationExecutor<A extends Annotation, T extends Component> extends AnnotationExecutor {

    void init(A annotation, StandardEditor editor, T target);

    void postInit(A annotation, StandardEditor editor, T target);

}