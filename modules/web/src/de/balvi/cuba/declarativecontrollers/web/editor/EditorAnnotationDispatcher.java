package de.balvi.cuba.declarativecontrollers.web.editor;

public interface EditorAnnotationDispatcher {

    String NAME = "dbcdc_EditorAnnotationExecutorService";

    void executeOnInit(AnnotatableStandardEditor editor);

    void executeonBeforeShow(AnnotatableStandardEditor editor);
}