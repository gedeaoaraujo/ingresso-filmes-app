package com.ingresso.filmes.di

import com.ingresso.filmes.IngressoRepository
import com.ingresso.filmes.MainViewModel
import com.ingresso.filmes.local.AppDatabase
import com.ingresso.filmes.local.MovieDao
import com.ingresso.filmes.remote.IngressoClient
import com.ingresso.filmes.remote.IngressoService
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<MovieDao> { AppDatabase.getDatabase(get()).movieDao() }
    single<IngressoService> {
        IngressoClient.createService(IngressoService::class.java)
    }
    single { IngressoRepository(get(), get()) }
    viewModel { MainViewModel(get()) }
}