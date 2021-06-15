package br.com.rchlo.filtros;

import br.com.rchlo.domain.Cor;
import br.com.rchlo.domain.Produto;
import br.com.rchlo.main.filtro.FiltroDeProdutosPorCor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FiltroDeProdutosPorCorTeste {

    List<Produto> produtos = new ArrayList<>(Arrays.asList(
            new Produto(Cor.BRANCA),
            new Produto(Cor.AZUL),
            new Produto(Cor.VERMELHA),
            new Produto(Cor.BRANCA),
            new Produto(Cor.BRANCA)
    ));

    @Test
    public void deveFiltrarProdutoPorCorComApenasUmElementoDaCorNaLista(){
        var cor = Cor.VERMELHA;
        var filtro = new FiltroDeProdutosPorCor();
        var resultado = filtro.filtrarProdutoPorCor(produtos, cor);

        assertEquals(1, resultado.size());
        assertEquals(new Produto(Cor.VERMELHA).getCor(), resultado.get(0).getCor());
    }

    @Test
    public void deveFiltrarProdutoPorCorComMaisDeUmElementoDaCorNaLista(){
        var cor = Cor.BRANCA;
        var filtro = new FiltroDeProdutosPorCor();
        var resultado = filtro.filtrarProdutoPorCor(produtos, cor);

        assertEquals(3, resultado.size());
        assertEquals(new ArrayList<>(Arrays.asList(
                produtos.get(0),
                produtos.get(3),
                produtos.get(4)
        )),resultado);

    }

    @Test
    public void deveFiltrarProdutoPorCorQueNaoExisteNaLista(){
        var cor = Cor.CINZA;
        var filtro = new FiltroDeProdutosPorCor();
        var resultado = filtro.filtrarProdutoPorCor(produtos, cor);

        assertEquals(0, resultado.size());
    }

    @Test
    public void deveLancarUmExceptionParaUmaListaVazia(){

        var cor = Cor.CINZA;
        var filtro = new FiltroDeProdutosPorCor();

        assertThrows(IllegalArgumentException.class,
                () -> filtro.filtrarProdutoPorCor(null, cor));
    }

    @Test
    public void deveLancarUmExceptionParaUmaCorNula(){

        var filtro = new FiltroDeProdutosPorCor();

        assertThrows(IllegalArgumentException.class,
                () -> filtro.filtrarProdutoPorCor(produtos, null));
    }

}
