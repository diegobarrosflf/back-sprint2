package br.com.rchlo.main.filtro;

import br.com.rchlo.domain.ListaDeProdutos;
import br.com.rchlo.domain.Produto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FiltroDeProdutosPorFaixaDePreco {

    public static void main(String[] args) {
        List<Produto> produtos = ListaDeProdutos.lista();

        BigDecimal valorMinimo = new BigDecimal("30.00");
        BigDecimal valorMaximo = new BigDecimal("50.00");

        List<Produto> produtosPorFaixaDePreco = new ArrayList<>();
        for (Produto produto : produtos) {
            if ((produto.getPrecoEfetivo()).compareTo(valorMinimo) >= 0 &&
                    (produto.getPrecoEfetivo()).compareTo(valorMaximo) <= 0) {
                produtosPorFaixaDePreco.add(produto);
            }
        }

        for (Produto produto : produtosPorFaixaDePreco) {
            System.out.println(produto.getNome() + " (cod. " + produto.getCodigo() + " - " + produto.getPesoEmGramas() + " g) custa R$ " + produto.getPrecoEfetivo());
        }

    }

}
