package de.balvi.cuba.declarativecontrollers.web.annotationexecutor;

import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.screen.FrameOwner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

abstract public class AbstractAnnotationDispatcherBean<T extends AnnotationExecutor, K extends AnnotationExecutor> {

    protected Field[] getDeclaredFields(FrameOwner frame) {
        return frame.getClass().getDeclaredFields();
    }

    protected Component getFieldValue(FrameOwner frame, Field field) {
        try {
            field.setAccessible(true);
            return (Component) field.get(frame);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    protected Annotation[] getClassAnnotations(FrameOwner frame) {
        return frame.getClass().getAnnotations();
    }

    protected Collection<? extends AnnotationExecutor> getSupportedAnnotationExecutors(Collection<? extends AnnotationExecutor> potentialAnnotationExecutors, Annotation annotation) {
        Collection<AnnotationExecutor> result = new ArrayList<AnnotationExecutor>();
        for (AnnotationExecutor annotationExecutor : potentialAnnotationExecutors) {
            if (annotationExecutor.supports(annotation)) {
                result.add(annotationExecutor);
            }
        }
        return result;

    }
}
