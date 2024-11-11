package com.ingresso.filmes.di

import com.ingresso.filmes.IngressoRepository
import com.ingresso.filmes.MainViewModel
import com.ingresso.filmes.remote.IngressoClient
import org.koin.dsl.module

val appModule = module {
    single { IngressoRepository(IngressoClient.ingressoService) }
    single { MainViewModel(get()) }
}