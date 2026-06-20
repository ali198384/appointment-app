package core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.common.mvi.MviIntent
import core.common.mvi.MviState
import core.ui.effect.UiEffect
import core.ui.text.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseViewModel<Intent : MviIntent, State : MviState> : ViewModel() {

    // ---------------- STATE ----------------
    private val _state = MutableStateFlow(createInitialState())
    val state: StateFlow<State> = _state.asStateFlow()

    // ---------------- EFFECT (one-shot) ----------------

    private val _effect = Channel<UiEffect>(Channel.BUFFERED)
    val effect: Flow<UiEffect> = _effect.receiveAsFlow()

    // ---------------- ABSTRACT ----------------

    protected abstract fun createInitialState(): State
    protected abstract fun handleIntent(intent: Intent)

    // ---------------- PUBLIC ----------------

    fun sendIntent(intent: Intent) {
        handleIntent(intent)
    }

    // ---------------- STATE HELPERS ----------------

    protected fun setState(reducer: State.() -> State) {
        _state.update(reducer)
    }

    protected val currentState: State
        get() = _state.value

    // ---------------- EFFECT HELPERS ----------------

    protected fun sendEffect(uiEffect: () -> UiEffect) {
        viewModelScope.launch {
            _effect.send(uiEffect())
        }
    }

    // ---------------- COROUTINE SAFE LAUNCH ----------------

    /**
     * helper
     * auto:
     *  • loading
     *  • try/catch
     *  • error effect
     */
    protected fun launchSafe(
        showLoading: Boolean = true,
        onError: (Throwable) -> UiEffect? = { null },
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(IO) {
        try {
            if (showLoading) setLoading(true)
            block()
        } catch (e: Exception) {
            onError(e)?.let { sendEffect { it } }
        } finally {
            if (showLoading) setLoading(false)
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    protected fun <T, R> collectWithSearchAndLoad(
        sourceFlow: Flow<T>,
        debounceMs: Long = 400L,
        observeBlock: suspend (T) -> Flow<R>,
        onEachBlock: (R) -> Unit
    ) {
        sourceFlow
            .distinctUntilChanged()
            .let { if (debounceMs > 0) it.debounce(debounceMs) else it }
            .flatMapLatest { value ->
                observeBlock(value)
                    .onStart { setLoading(true) }
            }
            .onEach { items ->
                setLoading(false)
                onEachBlock (items)
            }
            .catch { e ->
                sendEffect { UiEffect.ShowSnackBar(UiText.Dynamic(e.message ?: "")) }
            }
            .launchIn(viewModelScope)
    }
    // ---------------- LOADING DEFAULT SUPPORT ----------------

    /**
     * اگر State تو loading داشته باشه، این اتوماتیک کار می‌کنه
     */
    protected open fun setLoading(value: Boolean) {}
}
