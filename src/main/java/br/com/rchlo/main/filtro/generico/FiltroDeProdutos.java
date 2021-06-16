package br.com.rchlo.main.filtro.generico;

import br.com.rchlo.domain.Cor;
import br.com.rchlo.domain.ListaDeProdutos;
import br.com.rchlo.domain.Produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroDeProdutos {

    public static void main(String[] args) {
        List<Produto> produtos = ListaDeProdutos.lista();

        var filtrador = new FiltroDeProdutos();
        filtrador.filtrar(produtos, new FiltroPorCor(Cor.BRANCA) )
                .forEach(p -> System.out.println(p.getCor()));

        filtrador.filtrar(produtos, new FiltroPorCodigo(1L) )
                .forEach(p -> System.out.println(p.getCodigo()));

        filtrador.filtrar(produtos, new FiltroPorFaixaDeValores(
                new BigDecimal("30.00"),
                new BigDecimal("50.00")
        ) ).forEach(p -> System.out.println(p.getPrecoEfetivo()));

    }

    public List<Produto> filtrarProdutosPorCor(List<Produto> produtos, Cor cor){
        if (produtos != null && cor != null) {
            return produtos.stream().filter(p -> cor.equals(p.getCor())).collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Argumento inválido");
        }
    }

    public List<Produto> filtrar(List<Produto> produtos, Filtravel termo){
        return termo.filtrar(produtos);
    }

}
