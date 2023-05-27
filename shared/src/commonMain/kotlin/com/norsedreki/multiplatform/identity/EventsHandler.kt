package com.norsedreki.multiplatform.identity

interface EventsHandler<E : Event> {
    fun setup()
}
