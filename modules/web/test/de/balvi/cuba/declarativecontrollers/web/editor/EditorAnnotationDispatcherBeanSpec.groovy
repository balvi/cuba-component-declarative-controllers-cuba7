package de.balvi.cuba.declarativecontrollers.web.editor

import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.editor.EditorAnnotationExecutor
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import spock.lang.Specification

class EditorAnnotationDispatcherBeanSpec extends Specification {
    EditorAnnotationDispatcher editorAnnotationExecutorService
    EditorAnnotationExecutor executor
    EditorAnnotationExecutor executor2

    def setup() {
        executor = Mock()
        executor2 = Mock()

        editorAnnotationExecutorService = new EditorAnnotationDispatcherMock([executor, executor2])
    }

    void 'executeInit does nothing if the editor has no annotations'() {

        given:
        AnnotatableStandardEditor editor = Mock(AnnotatableStandardEditor)

        when:
        editorAnnotationExecutorService.executeOnInit(editor)

        then:
        0 * executor.init(_, _)
    }

    void 'executeInit executes init if an Annotation is supported'() {

        given:
        AnnotatableStandardEditor editor = new MyEditorWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true

        when:
        editorAnnotationExecutorService.executeOnInit(editor)

        then:
        1 * executor.init(_, _)
        0 * executor2.init(_, _)
    }

    void 'executeInit executes init on every AnnotationExecutor that supports the Annotation'() {

        given:
        AnnotatableStandardEditor editor = new MyEditorWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true
        executor2.supports(_ as ToString) >> true

        when:
        editorAnnotationExecutorService.executeOnInit(editor)

        then:
        1 * executor.init(_, _)
        1 * executor2.init(_, _)
    }

    void 'executeInit does nothing if there is no AnnotationExecutor for this Annotation'() {

        given:
        AnnotatableStandardEditor editor = new MyEditorWithAnnotation()

        and:
        executor.supports(_ as EqualsAndHashCode) >> false

        when:
        editorAnnotationExecutorService.executeOnInit(editor)

        then:
        0 * executor.init(_, _)
    }


    void 'executePostInit does nothing if the editor has no annotations'() {

        given:
        AnnotatableStandardEditor editor = Mock()

        when:
        editorAnnotationExecutorService.executeonBeforeShow(editor)

        then:
        0 * executor.postInit(_, _)
    }

    void 'executePostInit executes init if an Annotation is supported'() {

        given:
        AnnotatableStandardEditor editor = new MyEditorWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true

        when:
        editorAnnotationExecutorService.executeonBeforeShow(editor)

        then:
        1 * executor.postInit(_, _)
    }

    void 'executePostInit executes init on every AnnotationExecutor that supports the Annotation'() {

        given:
        AnnotatableStandardEditor editor = new MyEditorWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true
        executor2.supports(_ as ToString) >> true

        when:
        editorAnnotationExecutorService.executeonBeforeShow(editor)

        then:
        1 * executor.postInit(_, _)
        1 * executor2.postInit(_, _)
    }

    void 'executePostInit does nothing if there is no AnnotationExecutor for this Annotation'() {

        given:
        AnnotatableStandardEditor editor = new MyEditorWithAnnotation()

        and:
        executor.supports(_) >> false

        when:
        editorAnnotationExecutorService.executeonBeforeShow(editor)

        then:
        0 * executor.postInit(_, _)
    }
}

class EditorAnnotationDispatcherMock extends EditorAnnotationDispatcherBean {

    Collection<EditorAnnotationExecutor> annotationExecutorCollection

    EditorAnnotationDispatcherMock(Collection<EditorAnnotationExecutor> annotationExecutorCollection) {
        this.annotationExecutorCollection = annotationExecutorCollection
    }

    @Override
    protected Collection<EditorAnnotationExecutor> getEditorAnnotationExecutors() {
        return annotationExecutorCollection
    }
}

@ToString
class MyEditorWithAnnotation extends AnnotatableStandardEditor {
}
