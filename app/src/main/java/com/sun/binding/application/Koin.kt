package com.sun.binding.application

import com.sun.binding.model.main.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * 网络请求 Module
 */
val netModule: Module = module {}

/**
 * 数据仓库 Module
 */
val repositoryModule: Module = module {  }

/**
 * 适配器 Module
 */
val adapterModule : Module = module {  }

/**
 * ViewModel Module
 */
val viewModelModule: Module = module {
    viewModel { SplashViewModel() }
}