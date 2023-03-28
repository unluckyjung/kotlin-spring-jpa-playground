package com.example.kopring.member.event

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class DomainEventPublisher private constructor(
    applicationEventPublisher: ApplicationEventPublisher,
) {
    init {
        DomainEventPublisher.applicationEventPublisher = applicationEventPublisher
    }
    companion object{
        private lateinit var applicationEventPublisher: ApplicationEventPublisher

        fun publishEvent(event: Any){
            applicationEventPublisher.publishEvent(event)
        }
    }
}
