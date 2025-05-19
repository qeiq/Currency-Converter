package com.imam.currencyconverter.di

import com.imam.currencyconverter.data.remote.ApiInterface
import com.imam.currencyconverter.data.repository.AppRepositoryImp
import com.imam.currencyconverter.domain.repository.AppRepository
import com.imam.currencyconverter.domain.usecase.GetRatesUseCase
import com.imam.currencyconverter.presentation.AppViewModel
import com.imam.currencyconverter.presentation.home_screen.HomeViewModel
import com.imam.currencyconverter.utils.Constant
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    single<AppRepository> {
        AppRepositoryImp(get())
    }


    single {
        GetRatesUseCase(get())
    }


    viewModel {
        HomeViewModel()
    }
    viewModel {
        AppViewModel(get())
    }

}