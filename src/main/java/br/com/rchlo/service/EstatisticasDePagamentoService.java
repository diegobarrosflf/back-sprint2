package br.com.rchlo.service;

import br.com.rchlo.dao.PagamentoDao;
import br.com.rchlo.domain.Pagamento;
import br.com.rchlo.domain.StatusPagamento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EstatisticasDePagamentoService {

    private final PagamentoDao pagamentoDao;

    public EstatisticasDePagamentoService(PagamentoDao pagamentoDao) {
        this.pagamentoDao = pagamentoDao;
    }

    public EstatisticasDePagamento calcula() {
        List<Pagamento> todosOsPagamentos = pagamentoDao.obtemTodos(); // Será que essa é a melhor maneira de fazer isso?

        BigDecimal maiorPagamentoConfirmado = todosOsPagamentos.stream()
                .filter(pagamento -> StatusPagamento.CONFIRMADO.equals(pagamento.getStatus()))
                .map(Pagamento::getValor)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);

        Map<StatusPagamento, Long> quantidadeDePagamentoPorStatus = todosOsPagamentos.stream()
                .collect(Collectors.groupingBy(Pagamento::getStatus, Collectors.counting()));

        return new EstatisticasDePagamento(maiorPagamentoConfirmado ,quantidadeDePagamentoPorStatus);
    }
}
