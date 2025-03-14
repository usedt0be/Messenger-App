package com.example.messengerapp.core.viewmodel.factory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import javax.inject.Provider



class StateHandleViewModelFactoryImpl @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModelAssistedFactory<*>>>
): StateHandleViewModelFactory {

    override fun <VM : ViewModel> create(modelClass: Class<VM>, handle: SavedStateHandle): VM {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get().create(handle = handle) as VM
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}













//@AssistedFactory
//interface MyAssistedFactory{
//    fun create(owner: SavedStateRegistryOwner): MyViewModelFactory
//}
//
//class MyViewModelFactory @AssistedInject constructor (
//    @Assisted owner: SavedStateRegistryOwner,
//    defaultArgument: Bundle? = null
//): AbstractSavedStateViewModelFactory(owner, defaultArgument) {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(
//        key: String,
//        modelClass: Class<T>,
//        handle: SavedStateHandle
//    ): T {
//        return ChatViewModel() as T
//    }
//}