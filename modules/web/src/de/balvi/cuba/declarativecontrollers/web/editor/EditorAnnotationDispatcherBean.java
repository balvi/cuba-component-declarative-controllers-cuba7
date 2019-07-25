package de.balvi.cuba.declarativecontrollers.web.editor;

import com.haulmont.cuba.core.global.AppBeans;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AbstractAnnotationDispatcherBean;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.editor.EditorAnnotationExecutor;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.editor.EditorFieldAnnotationExecutor;
import groovy.transform.CompileStatic;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

@CompileStatic
@Component(EditorAnnotationDispatcher.NAME)
class EditorAnnotationDispatcherBean extends AbstractAnnotationDispatcherBean implements EditorAnnotationDispatcher {


    @Override
    public void executeOnInit(AnnotatableStandardEditor editor) {
        executeInitForClassAnnotations(editor);
        executeInitForFieldAnnotations(editor);
    }

    @Override
    public void executeonBeforeShow(AnnotatableStandardEditor editor) {
        executePostInitForClassAnnotations(editor);
        executePostInitForFieldAnnotations(editor);
    }


    private void executeInitForClassAnnotations(AnnotatableStandardEditor editor) {
        for (Annotation annotation : getClassAnnotations(editor)) {

            Collection<EditorAnnotationExecutor> supportedAnnotationExecutors = getSupportedEditorAnnotationExecutors(annotation);

            for (EditorAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                annotationExecutor.init(annotation, editor);
            }
        }
    }

    private void executePostInitForClassAnnotations(AnnotatableStandardEditor editor) {

        for (Annotation annotation : getClassAnnotations(editor)) {

            Collection<EditorAnnotationExecutor> supportedAnnotationExecutors = getSupportedEditorAnnotationExecutors(annotation);

            for (EditorAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                annotationExecutor.postInit(annotation, editor);
            }
        }
    }


    private void executeInitForFieldAnnotations(AnnotatableStandardEditor editor) {
        for (Field field : getDeclaredFields(editor)) {
            Annotation[] fieldAnnotations = field.getAnnotations();
            for (Annotation annotation : fieldAnnotations) {

                Collection<EditorFieldAnnotationExecutor> supportedAnnotationExecutors = getSupportedEditorFieldAnnotationExecutors(annotation);

                if (supportedAnnotationExecutors != null && supportedAnnotationExecutors.size() > 0) {
                    com.haulmont.cuba.gui.components.Component fieldValue = getFieldValue(editor, field);
                    for (EditorFieldAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                        annotationExecutor.init(annotation, editor, fieldValue);
                    }
                }

            }
        }
    }

    private void executePostInitForFieldAnnotations(AnnotatableStandardEditor editor) {

        for (Field field : getDeclaredFields(editor)) {
            Annotation[] fieldAnnotations = field.getAnnotations();
            for (Annotation annotation : fieldAnnotations) {
                Collection<EditorFieldAnnotationExecutor> supportedAnnotationExecutors = getSupportedEditorFieldAnnotationExecutors(annotation);

                if (supportedAnnotationExecutors != null && supportedAnnotationExecutors.size() > 0) {
                    com.haulmont.cuba.gui.components.Component fieldValue = getFieldValue(editor, field);
                    for (EditorFieldAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                        annotationExecutor.postInit(annotation, editor, fieldValue);
                    }
                }
            }
        }
    }


    protected Collection<EditorAnnotationExecutor> getSupportedEditorAnnotationExecutors(Annotation annotation) {
        Collection<EditorAnnotationExecutor> annotationExecutors = getEditorAnnotationExecutors();
        return (Collection<EditorAnnotationExecutor>) getSupportedAnnotationExecutors(annotationExecutors, annotation);
    }

    protected Collection<EditorFieldAnnotationExecutor> getSupportedEditorFieldAnnotationExecutors(Annotation annotation) {
        Collection<EditorFieldAnnotationExecutor> annotationExecutors = getEditorFieldAnnotationExecutors();
        return (Collection<EditorFieldAnnotationExecutor>) getSupportedAnnotationExecutors(annotationExecutors, annotation);

    }

    protected Collection<EditorAnnotationExecutor> getEditorAnnotationExecutors() {
        return AppBeans.getAll(EditorAnnotationExecutor.class).values();
    }

    protected Collection<EditorFieldAnnotationExecutor> getEditorFieldAnnotationExecutors() {
        return AppBeans.getAll(EditorFieldAnnotationExecutor.class).values();
    }

}