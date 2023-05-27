package com.norsedreki.multiplatform.identity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class EventsDispatcher<E : Event> {

/*    private val eventsSubject = PublishSubject.create<E>()

    internal val events = eventsSubject.hide()

    internal inline fun <reified T : E> ofType(): Observable<T> {
        return events
            .ofType(T::class.java)
    }

    internal inline fun <reified T : E> ofType(scheduler: Scheduler): Observable<T> {
        return ofType<T>()
            .observeOn(scheduler)
    }

    operator fun invoke(event: E) = eventsSubject.onNext(event)*/

    //private val appCoroutineScope: CoroutineScope = GlobalScope + Dispatchers.Default
    private val appCoroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    val subscribingScope = CoroutineScope(Unconfined) //CoroutineScope(SupervisorJob())

    operator fun invoke(event: E) {
        /*eventSubject
            .onEach { println("123123 $it") }
            .onCompletion { cause -> println("222222 $cause") }
            .catch { cause -> println("33333 $cause") }
            .launchIn(subscribingScope)

        val c = eventSubject.subscriptionCount.value
        println("Subs $c")
        val e = eventSubject.tryEmit(event)*/
        //println("Emitted? $e for $event")

        subscribingScope.launch {
            val c = eventSubject.subscriptionCount.value
            println("Subs $c, event $event")
            eventSubject.emit(event)
        }
    }

    internal inline fun <reified T : E> ofType(): Flow<T> {
        return eventSubject
            .filterIsInstance()
    }

    private val stateSubject = newBehaviorFlow<State>()
    val eventSubject = MutableSharedFlow<E>(
       // replay = 2,
       // onBufferOverflow = BufferOverflow.DROP_OLDEST,
       // extraBufferCapacity = 1
    )

    /*protected val requireState: State
        get() = stateSubject.first()

    fun getStateObservable() = stateSubject.asStateFlow()*/

    fun getEventObservable() = eventSubject.asSharedFlow()

    protected fun sendEvent(event: E) {
        eventSubject.tryEmit(event)
        //eventSubject.emit(event)
    }

    protected fun setState(state: State) {
        stateSubject.tryEmit(state)
    }

    /*Comparison with Rx
    StateFlow → BehaviorSubject
    SharedFlow → PublishSubject*/

    fun aaa() {
        getEventObservable() // called on onAttach()

            //.flowOn(Dispatchers.Main)
            .launchIn(GlobalScope)
            //.launchIn(this@MyActivity.lifecycleScope)
            //.launchIn(Dispatchers.Default)
            //.launchIn(CoroutineScope())
            //.onEach(this::handleEvent)
            //.catch(this::defaultHandleException)
            //.launchIn(viewLifecycleOwner.lifecycleScope or viewModelScope) // from lifecycle-viewmodel-ktx:2.6.1
    }

    /*
    Since Coroutines 1.4.0 (Nov 2020), SharedFlow and StateFlow are the new equivalents for RxJava Subjects.

    PublishSubject can be replaced with MutableSharedFlow().
    ReplaySubject can be replaced with MutableSharedFlow(Int.MAX_VALUE)
    BehaviorSubject can be replaced with MutableStateFlow(), but with a special caveat!

    MutableStateFlow will ignore equal items. It is the same as BehaviorSubject().distinctUntilChanged() If you want equal values to be emitted, you'll need a special MutableSharedFlow
*/
    fun <T> newBehaviorFlow(init: T? = null) = MutableSharedFlow<T>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    ).also { init?.run { it.tryEmit(init) } }
}
