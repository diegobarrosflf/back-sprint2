package br.com.rchlo.service;

import br.com.rchlo.dao.PagamentoDao;
import br.com.rchlo.domain.DadosCartao;
import br.com.rchlo.domain.Pagamento;
import br.com.rchlo.domain.StatusPagamento;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    //não há nenhum pagamento
    @Test
    void deveConsiderarMaiorPagamentoIgualAZeroQuandoNaoHouverNenhumPagamento() {
        List<Pagamento> pagamentosVazio = new ArrayList<>();
        Mockito.when(pagamentoDao.obtemTodos()).thenReturn(pagamentosVazio);

        var estatisticasDePagamentoService = new EstatisticasDePagamentoService(pagamentoDao);
        EstatisticasDePagamento estatisticasDePagamento = estatisticasDePagamentoService.calcula();

        BigDecimal maiorPagamentoConfirmado = estatisticasDePagamento.getMaiorPagamentoConfirmado();
        Assertions.assertThat(maiorPagamentoConfirmado).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void deveConsiderarQuantidadeDePagamentoPorStatusVazioQuandoNaoHouverNenhumPagamento() {
        List<Pagamento> pagamentosVazio = new ArrayList<>();
        Mockito.when(pagamentoDao.obtemTodos()).thenReturn(pagamentosVazio);

        var estatisticasDePagamentoService = new EstatisticasDePagamentoService(pagamentoDao);
        EstatisticasDePagamento estatisticasDePagamento = estatisticasDePagamentoService.calcula();
        Map<StatusPagamento, Long> quantidadeDePagamentoPorStatus = estatisticasDePagamento.getQuantidadeDePagamentoPorStatus();

        Assertions.assertThat(quantidadeDePagamentoPorStatus).isEmpty();
    }

    //há pagamentos, mas nenhum com o status CRIADO
    @Test
    void deveConsiderarMaiorPagamentoQuandoNaoHouverNenhumPagamentoComStatusCRIADO() {
        List<Pagamento> pagamentos = removerPagamentoPorStatus(StatusPagamento.CRIADO);

        Mockito.when(pagamentoDao.obtemTodos()).thenReturn(pagamentos);

        var estatisticasDePagamentoService = new EstatisticasDePagamentoService(pagamentoDao);
        EstatisticasDePagamento estatisticasDePagamento = estatisticasDePagamentoService.calcula();

        BigDecimal maiorPagamentoConfirmado = estatisticasDePagamento.getMaiorPagamentoConfirmado();
        Assertions.assertThat(maiorPagamentoConfirmado).isEqualTo(new BigDecimal("200.00"));
    }

    @Test
    void deveConsiderarQuantidadeDePagamentoQuandoNaoHouverNenhumPagamentoComStatusCRIADO() {
        List<Pagamento> pagamentos = removerPagamentoPorStatus(StatusPagamento.CRIADO);

        Mockito.when(pagamentoDao.obtemTodos()).thenReturn(pagamentos);

        var estatisticasDePagamentoService = new EstatisticasDePagamentoService(pagamentoDao);
        EstatisticasDePagamento estatisticasDePagamento = estatisticasDePagamentoService.calcula();
        Map<StatusPagamento, Long> quantidadeDePagamentoPorStatus = estatisticasDePagamento.getQuantidadeDePagamentoPorStatus();

        Assertions.assertThat(quantidadeDePagamentoPorStatus)
                .containsEntry(StatusPagamento.CONFIRMADO, 1L)
                .containsEntry(StatusPagamento.CANCELADO, 1L);
    }

    //há pagamentos, mas nenhum com o status CONFIRMADO
    @Test
    void deveConsiderarMaiorPagamentoIgualAZeroQuandoNaoHouverNenhumPagamentoComStatusCONFIRMADO() {
        List<Pagamento> pagamentos = removerPagamentoPorStatus(StatusPagamento.CONFIRMADO);

        Mockito.when(pagamentoDao.obtemTodos()).thenReturn(pagamentos);

        var estatisticasDePagamentoService = new EstatisticasDePagamentoService(pagamentoDao);
        EstatisticasDePagamento estatisticasDePagamento = estatisticasDePagamentoService.calcula();

        BigDecimal maiorPagamentoConfirmado = estatisticasDePagamento.getMaiorPagamentoConfirmado();
        Assertions.assertThat(maiorPagamentoConfirmado).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void deveConsiderarQuantidadeDePagamentoQuandoNaoHouverNenhumPagamentoComStatusCONFIRMADO() {
        List<Pagamento> pagamentos = removerPagamentoPorStatus(StatusPagamento.CONFIRMADO);

        Mockito.when(pagamentoDao.obtemTodos()).thenReturn(pagamentos);

        var estatisticasDePagamentoService = new EstatisticasDePagamentoService(pagamentoDao);
        EstatisticasDePagamento estatisticasDePagamento = estatisticasDePagamentoService.calcula();
        Map<StatusPagamento, Long> quantidadeDePagamentoPorStatus = estatisticasDePagamento.getQuantidadeDePagamentoPorStatus();

        Assertions.assertThat(quantidadeDePagamentoPorStatus)
                .containsEntry(StatusPagamento.CRIADO, 2L)
                .containsEntry(StatusPagamento.CANCELADO, 1L);
    }

    //há pagamentos, mas nenhum com o status CANCELADO
    @Test
    void deveConsiderarMaiorPagamentoQuandoNaoHouverNenhumPagamentoComStatusCANCELADO() {
        List<Pagamento> pagamentos = removerPagamentoPorStatus(StatusPagamento.CANCELADO);

        Mockito.when(pagamentoDao.obtemTodos()).thenReturn(pagamentos);

        var estatisticasDePagamentoService = new EstatisticasDePagamentoService(pagamentoDao);
        EstatisticasDePagamento estatisticasDePagamento = estatisticasDePagamentoService.calcula();

        BigDecimal maiorPagamentoConfirmado = estatisticasDePagamento.getMaiorPagamentoConfirmado();
        Assertions.assertThat(maiorPagamentoConfirmado).isEqualTo(new BigDecimal("200.00"));
    }

    @Test
    void deveConsiderarQuantidadeDePagamentoQuandoNaoHouverNenhumPagamentoComStatusCANCELADO() {
        List<Pagamento> pagamentos = removerPagamentoPorStatus(StatusPagamento.CANCELADO);

        Mockito.when(pagamentoDao.obtemTodos()).thenReturn(pagamentos);

        var estatisticasDePagamentoService = new EstatisticasDePagamentoService(pagamentoDao);
        EstatisticasDePagamento estatisticasDePagamento = estatisticasDePagamentoService.calcula();
        Map<StatusPagamento, Long> quantidadeDePagamentoPorStatus = estatisticasDePagamento.getQuantidadeDePagamentoPorStatus();

        Assertions.assertThat(quantidadeDePagamentoPorStatus)
                .containsEntry(StatusPagamento.CRIADO, 2L)
                .containsEntry(StatusPagamento.CONFIRMADO, 1L);
    }


    private List<Pagamento> removerPagamentoPorStatus(StatusPagamento status) {
        return pagamentos.stream()
                .filter(pagamento -> !status.equals(pagamento.getStatus()))
                .collect(Collectors.toList());
    }


    private List<Pagamento> populaPagamentos(){

        //criar dados do cartão
        var dadosCartaoFulano = new DadosCartao(
                "Fulano",
                "3216-6541-9874-3215",
                YearMonth.of(2021, 6),
                "565");

        var dadosCartaoCicrano = new DadosCartao(
                "Cicrano",
                "6548-6541-3574-3215",
                YearMonth.of(2021,4),
                "951");

        var dadosCartaoBeltrano = new DadosCartao(
                "Beltrano",
                "3578-9537-3587-6589",
                YearMonth.of(2021,1),
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