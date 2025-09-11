package com.appnew.basecode.common.delegate.binding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A property delegate for managing [ViewBinding] in a [Fragment],
 * which automatically clears the binding when the Fragment's view is destroyed.
 *
 * This helps avoid memory leaks and follows the Fragment's view lifecycle,
 * ensuring safe access to the binding.
 *
 * This delegate should be used when the layout is already inflated
 * (e.g. when using `Fragment(R.layout.my_layout)` constructor or inflating manually in `onCreateView()`).
 *
 * Use this with the generated `MyFragmentBinding.bind(view)` method to bind an already-inflated view.
 *
 * @param T The type of [ViewBinding]
 * @param fragment The Fragment in which this binding is used
 * @param viewBindingFactory A lambda that returns a ViewBinding from a View
 *
 */
class FragmentViewBindingDelegate<T : ViewBinding>(
    private val fragment: Fragment,
    private val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(this)
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val view = thisRef.view
            ?: throw IllegalStateException("Cannot access binding. Fragment's view is null.")

        if (binding == null) {
            binding = viewBindingFactory(view)

            // Automatically clear the binding when view is destroyed
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    binding = null
                }
            })
        }

        return binding!!
    }

    override fun onDestroy(owner: LifecycleOwner) {
        binding = null
    }
}

/**
 * Returns a [FragmentViewBindingDelegate] that binds a [ViewBinding] to a [Fragment]'s view,
 * and automatically clears the binding when the view is destroyed.
 *
 * Use this function in Fragments where the layout is already inflated,
 * typically when using the `Fragment(R.layout.fragment_xyz)` constructor or inflating in `onCreateView()`.
 *
 * This avoids memory leaks and reduces boilerplate code when working with ViewBinding in Fragments.
 *
 * @param T The type of [ViewBinding]
 * @param factory A lambda function to create a [ViewBinding] instance from a [View], e.g., `MyBinding::bind`
 *
 * @return A delegated ViewBinding property tied to the Fragment's view lifecycle.
 *
 * Example usage:
 * ```
 * class MyFragment : Fragment(R.layout.fragment_my) {
 *     private val binding by viewBinding(MyFragmentBinding::bind)
 *
 *     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *         super.onViewCreated(view, savedInstanceState)
 *         binding.textView.text = "Hello"
 *     }
 * }
 * ```
 */
inline fun <reified T : ViewBinding> Fragment.viewBinding(
    noinline factory: (View) -> T
): FragmentViewBindingDelegate<T> = FragmentViewBindingDelegate(this, factory)

