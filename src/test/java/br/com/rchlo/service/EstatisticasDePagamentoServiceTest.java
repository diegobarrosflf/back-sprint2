package br.com.rchlo.service;

import br.com.rchlo.dao.PagamentoDao;
import br.com.rchlo.domain.DadosCartao;
import br.com.rchlo.domain.Pagamento;
import br.com.rchlo.domain.StatusPagamento;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.PathAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class EstatisticasDePagamentoServiceTest {

    List<Pagamento> pagamentos = populaPagamentos();
    PagamentoDao pagamentoDao = Mockito.mock(PagamentoDao.class);

    @Test
    void deveConsiderarMaiorPagamentoConfirmado() {

        Mockito.when(pagamentoDao.obtemTodos()).thenReturn(pagamentos);
        var estatisticasDePagamentoService = new EstatisticasDePagamentoService(pagamentoDao);
        EstatisticasDePagamento estatisticasDePagamento = estatisticasDePagamentoService.calcula();

        BigDecimal maiorPagamentoConfirmado = estatisticasDePagamento.getMaiorPagamentoConfirmado();
        Assertions.assertThat(maiorPagamentoConfirmado).isEqualTo(new BigDecimal("200.00"));
    }

    @Test
    void deveConsiderarQuantidadeDePagamentoPorStatus() {

        Mockito.when(pagamentoDao.obtemTodos()).thenReturn(pagamentos);
        var estatisticasDePagamentoService = new EstatisticasDePagamentoService(pagamentoDao);

        EstatisticasDePagamento estatisticasDePagamento = estatisticasDePagamentoService.calcula();

        Map<StatusPagamento, Long> quantidadeDePagamentoPorStatus = estatisticasDePagamento.getQuantidadeDePagamentoPorStatus();
        Assertions.assertThat(quantidadeDePagamentoPorStatus)
                .containsEntry(StatusPagamento.CRIADO, 2L)
                .containsEntry(StatusPagamento.CONFIRMADO, 1L)
                .containsEntry(StatusPagamento.CANCELADO, 1L);
    }


    private List<Pagamento> populaPagamentos(){

        //criar dados do cartão
        var dadosCartaoFulano = new DadosCartao(
                "Fulano",
                "3216-6541-9874-3215",
                YearMonth.of(2021,06),
                "565");

        var dadosCartaoCicrano = new DadosCartao(
                "Cicrano",
                "6548-6541-3574-3215",
                YearMonth.of(2021,04),
                "951");

        var dadosCartaoBeltrano = new DadosCartao(
                "Beltrano",
                "3578-9537-3587-6589",
                YearMonth.of(2021,01),
                "587");

        //Criando Pagamentos
        var pagamentoFulano = new Pagamento(
                1L,
                new BigDecimal("150.00"),
                dadosCartaoFulano,
                StatusPagamento.CRIADO);

        var pagamentoCicrano = new Pagamento(
                1L,
                new BigDecimal("200.00"),
                dadosCartaoCicrano,
                StatusPagamento.CONFIRMADO);

        var pagamentoBeltrano = new Pagamento(
                1L,
                new BigDecimal("180.00"),
                dadosCartaoBeltrano,
                StatusPagamento.CRIADO);

        var pagamentoBeltrano2 = new Pagamento(
                1L,
                new BigDecimal("189.00"),
                dadosCartaoBeltrano,
                StatusPagamento.CANCELADO);

        return new ArrayList<>(Arrays.asList(pagamentoFulano, pagamentoBeltrano, pagamentoCicrano, pagamentoBeltrano2));

    }

}