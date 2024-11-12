package com.ingresso.filmes.di

import com.ingresso.filmes.IngressoRepository
import com.ingresso.filmes.MainViewModel
import com.ingresso.filmes.remote.IngressoClient
import com.ingresso.filmes.remote.IngressoService
import org.koin.dsl.module

val appModule = module {
    single<IngressoService> {
        IngressoClient.createService(IngressoService::class.java)
    }
    single { IngressoRepository(get()) }
    single { MainViewModel(get()) }
}