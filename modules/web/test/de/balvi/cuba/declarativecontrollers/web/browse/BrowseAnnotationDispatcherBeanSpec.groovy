package de.balvi.cuba.declarativecontrollers.web.browse

import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse.BrowseAnnotationExecutor
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import spock.lang.Specification

class BrowseAnnotationDispatcherBeanSpec extends Specification {
    BrowseAnnotationDispatcher sut
    BrowseAnnotationExecutor executor = Mock(BrowseAnnotationExecutor)
    BrowseAnnotationExecutor executor2 = Mock(BrowseAnnotationExecutor)

    def setup() {
        sut = new BrowseAnnotationDispatcherMock([executor, executor2])
    }

    void 'executeOnInit does nothing if the browser has no annotations'() {

        given:
        AnnotatableAbstractLookup browse = Mock(AnnotatableAbstractLookup)

        when:
        sut.executeOnInit(browse)

        then:
        0 * executor.init(_, _)
    }

    void 'executeOnInit executes init if an Annotation is supported'() {

        given:
        AnnotatableAbstractLookup browse = new MyBrowseWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true

        when:
        sut.executeOnInit(browse)

        then:
        1 * executor.init(_, _)
        0 * executor2.init(_, _)
    }

    void 'executeInit executes init on every AnnotationExecutor that supports the Annotation'() {

        given:
        AnnotatableAbstractLookup browse = new MyBrowseWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true
        executor2.supports(_ as ToString) >> true

        when:
        sut.executeOnInit(browse)

        then:
        1 * executor.init(_, _)
        1 * executor2.init(_, _)
    }

    void 'executeInit does nothing if there is no AnnotationExecutor for this Annotation'() {

        given:
        AnnotatableAbstractLookup browse = new MyBrowseWithAnnotation()

        and:
        executor.supports(_ as EqualsAndHashCode) >> false

        when:
        sut.executeOnInit(browse)

        then:
        0 * executor.init(_, _)
    }


    void 'onBeforeShow does nothing if the browser has no annotations'() {

        given:
        AnnotatableAbstractLookup browse = Mock(AnnotatableAbstractLookup)

        when:
        sut.onBeforeShow(browse)

        then:
        0 * executor.ready(_, _)
    }

    void 'onBeforeShow executes init if an Annotation is supported'() {

        given:
        AnnotatableAbstractLookup browse = new MyBrowseWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true

        when:
        sut.onBeforeShow(browse)

        then:
        1 * executor.ready(_, _)
        0 * executor2.ready(_, _)
    }

    void 'onBeforeShow executes init on every AnnotationExecutor that supports the Annotation'() {

        given:
        AnnotatableAbstractLookup browse = new MyBrowseWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true
        executor2.supports(_ as ToString) >> true

        when:
        sut.onBeforeShow(browse)

        then:
        1 * executor.ready(_, _)
        1 * executor2.ready(_, _)
    }

    void 'onBeforeShow does nothing if there is no AnnotationExecutor for this Annotation'() {

        given:
        AnnotatableAbstractLookup browse = new MyBrowseWithAnnotation()

        and:
        executor.supports(_ as EqualsAndHashCode) >> false

        when:
        sut.onBeforeShow(browse)

        then:
        0 * executor.ready(_, _)
    }
}

class BrowseAnnotationDispatcherMock extends BrowseAnnotationDispatcherBean {

    Collection<BrowseAnnotationExecutor> annotationExecutorCollection

    BrowseAnnotationDispatcherMock(Collection<BrowseAnnotationExecutor> annotationExecutorCollection) {
        this.annotationExecutorCollection = annotationExecutorCollection
    }

    @Override
    protected Collection<BrowseAnnotationExecutor> getBrowseAnnotationExecutors() {
        return annotationExecutorCollection
    }
}

@ToString
class MyBrowseWithAnnotation extends AnnotatableAbstractLookup {
}
