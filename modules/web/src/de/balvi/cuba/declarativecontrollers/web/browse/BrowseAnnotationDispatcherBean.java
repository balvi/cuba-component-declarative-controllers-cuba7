package de.balvi.cuba.declarativecontrollers.web.browse;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.screen.StandardLookup;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AbstractAnnotationDispatcherBean;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse.BrowseAnnotationExecutor;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse.BrowseFieldAnnotationExecutor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

@Component(BrowseAnnotationDispatcher.NAME)
class BrowseAnnotationDispatcherBean extends AbstractAnnotationDispatcherBean<BrowseAnnotationExecutor, BrowseFieldAnnotationExecutor> implements BrowseAnnotationDispatcher {

    @Override
    public void executeOnInit(StandardLookup browse) {
        executeInitForClassAnnotations(browse);
        executeInitForFieldAnnotations(browse);
    }

    private void executeInitForFieldAnnotations(StandardLookup browse) {
        for (Field field : getDeclaredFields(browse)) {
            Annotation[] fieldAnnotations = field.getAnnotations();

            for (Annotation annotation : fieldAnnotations) {

                Collection<BrowseFieldAnnotationExecutor> supportedAnnotationExecutors = getSupportedBrowseFieldAnnotationExecutors(annotation);

                if (supportedAnnotationExecutors != null && supportedAnnotationExecutors.size() > 0) {
                    com.haulmont.cuba.gui.components.Component fieldValue = getFieldValue(browse, field);
                    for (BrowseFieldAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                        annotationExecutor.init(annotation, browse, fieldValue);
                    }
                }

            }
        }
    }

    private void executeInitForClassAnnotations(StandardLookup browse) {
        for (Annotation annotation : getClassAnnotations(browse)) {
            Collection<BrowseAnnotationExecutor> supportedAnnotationExecutors = getSupportedBrowseAnnotationExecutors(annotation);

            for (BrowseAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                annotationExecutor.init(annotation, browse);
            }
        }
    }



    @Override
    public void onBeforeShow(StandardLookup browse) {
        executeReadyForClassAnnotations(browse);
        executeReadyForFieldAnnotations(browse);
    }

    private void executeReadyForFieldAnnotations(StandardLookup browse) {
        for (Field field : getDeclaredFields(browse)) {

            Annotation[] fieldAnnotations = field.getAnnotations();
            for (Annotation annotation : fieldAnnotations) {

                Collection<BrowseFieldAnnotationExecutor> supportedAnnotationExecutors = getSupportedBrowseFieldAnnotationExecutors(annotation);

                if (supportedAnnotationExecutors != null && supportedAnnotationExecutors.size() > 0) {
                    com.haulmont.cuba.gui.components.Component fieldValue = getFieldValue(browse, field);
                    for (BrowseFieldAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                        annotationExecutor.ready(annotation, browse, fieldValue);
                    }
                }
            }
        }
    }

    private void executeReadyForClassAnnotations(StandardLookup browse) {
        for (Annotation annotation : getClassAnnotations(browse)) {

            Collection<BrowseAnnotationExecutor> supportedAnnotations = getSupportedBrowseAnnotationExecutors(annotation);

            for (BrowseAnnotationExecutor annotationExecutor : supportedAnnotations) {
                annotationExecutor.ready(annotation, browse);
            }
        }
    }


    protected Collection<BrowseAnnotationExecutor> getSupportedBrowseAnnotationExecutors(Annotation annotation) {
        Collection<BrowseAnnotationExecutor> annotationExecutors = getBrowseAnnotationExecutors();
        return (Collection<BrowseAnnotationExecutor>) getSupportedAnnotationExecutors(annotationExecutors, annotation);
    }

    protected Collection<BrowseFieldAnnotationExecutor> getSupportedBrowseFieldAnnotationExecutors(Annotation annotation) {
        Collection<BrowseFieldAnnotationExecutor> annotationExecutors = getBrowseFieldAnnotationExecutors();
        return (Collection<BrowseFieldAnnotationExecutor>) getSupportedAnnotationExecutors(annotationExecutors, annotation);
    }

    protected Collection<BrowseAnnotationExecutor> getBrowseAnnotationExecutors() {
        return AppBeans.getAll(BrowseAnnotationExecutor.class).values();
    }


    protected Collection<BrowseFieldAnnotationExecutor> getBrowseFieldAnnotationExecutors() {
        return AppBeans.getAll(BrowseFieldAnnotationExecutor.class).values();
    }


}