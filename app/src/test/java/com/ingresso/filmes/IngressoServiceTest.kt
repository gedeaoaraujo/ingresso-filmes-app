package com.ingresso.filmes

import com.ingresso.filmes.remote.IngressoClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test


class IngressoServiceTest {

    @Test
    fun `Deve retornar a contagem de filmes maior que 0`() = runBlocking {
        val response = IngressoClient.ingressoService.getMovies()
        assertTrue(response.count > 0)
    }

    @Test
    fun `Deve retornar a listagem de filmes nao vazia`() = runBlocking {
        val response = IngressoClient.ingressoService.getMovies()
        assertTrue(response.items.isNotEmpty())
    }
}