package com.appnew.basecode.common.delegate.binding

import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A property delegate for managing [ViewBinding] in a [ComponentActivity],
 * which initializes the binding lazily and automatically clears it when the activity is destroyed.
 *
 * This helps reduce boilerplate and ensures safe access to the binding during the activity's lifecycle.
 *
 * The binding is created using a [LayoutInflater] when first accessed.
 *
 * @param T The type of [ViewBinding]
 * @param activity The host [ComponentActivity]
 * @param bindingInflater A lambda that inflates the view binding, typically `MyActivityBinding::inflate`
 *
 * @see viewBinding for the recommended extension function
 *
 */
class ActivityViewBindingDelegate<T : ViewBinding>(
    activity: ComponentActivity,
    private val bindingInflater: (LayoutInflater) -> T
) : ReadOnlyProperty<ComponentActivity, T>, DefaultLifecycleObserver {

    private var binding: T? = null

    init {
        activity.lifecycle.addObserver(this)
    }

    override fun getValue(thisRef: ComponentActivity, property: KProperty<*>): T {
        binding?.let { return it }

        val lifecycle = thisRef.lifecycle
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            binding = bindingInflater(thisRef.layoutInflater)
            return binding!!
        } else {
            throw IllegalStateException("Should not attempt to get bindings when Activity views are destroyed.")
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        binding = null
    }
}

/**
 * Returns a [ViewBinding] delegate for an [ComponentActivity] using the provided [bindingInflater].
 *
 * This allows safe and lazy access to [ViewBinding] inside an activity without needing to manually call
 * `setContentView()` or store a nullable binding reference.
 *
 * The binding is automatically cleared when the activity is destroyed, helping prevent memory leaks.
 *
 * @param T The type of [ViewBinding]
 * @param bindingInflater A lambda that inflates the binding from a [LayoutInflater],
 * usually `MyActivityBinding::inflate`
 *
 * @return A delegated property that provides a lazily initialized [ViewBinding] instance
 *
 * Example usage:
 * ```
 * class MyActivity : ComponentActivity() {
 *     private val binding by viewBinding(MyActivityBinding::inflate)
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         setContentView(binding.root)
 *         binding.titleText.text = "Hello, ViewBinding!"
 *     }
 * }
 * ```
 */
inline fun <reified T : ViewBinding> ComponentActivity.viewBinding(
    noinline bindingInflater: (LayoutInflater) -> T
) = ActivityViewBindingDelegate(this, bindingInflater)

