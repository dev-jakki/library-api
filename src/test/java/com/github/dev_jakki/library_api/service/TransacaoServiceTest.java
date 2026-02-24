package com.github.dev_jakki.library_api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransacaoServiceTest {

    @Autowired
    private TransacaoService transacaoService;

    @Test
    void transacaoSimples() {
        transacaoService.executar();
    }

    @Test
    void atualizacaoLivro() {
        transacaoService.atualizacaoSemAtualizar();
    }
}
