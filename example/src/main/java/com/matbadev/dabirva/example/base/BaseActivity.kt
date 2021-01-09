package com.matbadev.dabirva.example.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.matbadev.dabirva.example.App
import com.matbadev.dabirva.example.AppRepositories
import com.matbadev.dabirva.example.BR
import kotlin.reflect.KClass

abstract class BaseActivity<E, A : BaseScreenArguments, VM : BaseScreenViewModel<E, A>>(
    private val viewModelClass: KClass<VM>,
    @LayoutRes private val layoutId: Int,
) : AppCompatActivity(), ViewModelProvider.Factory, UiEventHandler<E> {

    protected lateinit var viewModel: VM

    private lateinit var binding: ViewDataBinding

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelProvider = ViewModelProvider(this, this)
        viewModel = viewModelProvider.get(viewModelClass.java)
        viewModel.registerUi(intent?.extras, savedInstanceState, this)
        binding = DataBindingUtil.setContentView(this, layoutId, null)
        binding.lifecycleOwner = this
        if (!binding.setVariable(BR.viewModel, viewModel)) throw AssertionError()
        binding.executePendingBindings()
    }

    @Suppress("UNCHECKED_CAST")
    final override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val app = App.of(this)
        return buildViewModel(app.repositories) as T
    }

    protected abstract fun buildViewModel(repositories: AppRepositories): VM

    final override fun handleCommonUiEvent(event: CommonUiEvent) = when (event) {
        is StartActivityEvent -> {
            val intent = Intent(this, event.activityClass.java)
            startActivity(intent, event.options)
        }
        is StartAppActivityEvent<*> -> {
            val intent = Intent(this, event.activityClass.java)
            event.arguments?.let { arguments: BaseScreenArguments ->
                intent.putExtra(BaseScreenViewModel.SCREEN_ARGUMENTS_KEY, arguments)
            }
            startActivity(intent, event.options)
        }
        is ShowToastEvent -> {
            Toast.makeText(this, event.messageProvider(this), event.duration.androidValue).show()
        }
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveViewModelState(outState)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
        viewModel.unregisterUi()
    }

}
