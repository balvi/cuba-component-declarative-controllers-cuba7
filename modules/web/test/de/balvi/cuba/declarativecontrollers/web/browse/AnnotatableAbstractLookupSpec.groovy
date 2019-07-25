package de.balvi.cuba.declarativecontrollers.web.browse

import spock.lang.Specification

class AnnotatableAbstractLookupSpec extends Specification {

    AnnotatableAbstractLookup sut
    BrowseAnnotationDispatcher browseAnnotationExecutorService

    def setup() {

        browseAnnotationExecutorService = Mock(BrowseAnnotationDispatcher)

        sut = new AnnotatableAbstractLookup(
                browseAnnotationDispatcher: browseAnnotationExecutorService
        )
    }

    def "onInit triggers the execution of the annotations"() {

        when:
        sut.onInit(null)

        then:
        1 * browseAnnotationExecutorService.executeOnInit(sut)
    }

    def "onBeforeShow triggers the execution of the annotations"() {

        when:
        sut.onBeforeShow()

        then:
        1 * browseAnnotationExecutorService.onBeforeShow(sut)
    }



}