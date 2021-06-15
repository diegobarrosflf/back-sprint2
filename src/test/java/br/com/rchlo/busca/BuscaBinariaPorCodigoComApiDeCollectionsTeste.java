package br.com.rchlo.busca;

import br.com.rchlo.domain.Cor;
import br.com.rchlo.domain.Produto;
import br.com.rchlo.main.busca.BuscaBinariaPorCodigoComApiDeCollections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BuscaBinariaPorCodigoComApiDeCollectionsTeste {

    List<Produto> produtos = new ArrayList<>(Arrays.asList(
            new Produto(7L),
            new Produto(11L),
            new Produto(2L),
            new Produto(9L),
            new Produto(1L)
    ));

    BuscaBinariaPorCodigoComApiDeCollections buscador = new BuscaBinariaPorCodigoComApiDeCollections();

    @Test
    public void deveEncontrarUmProdutoPorUmCodigoQueExisteNaLista(){
        var produtoEsperado = new Produto(7L);
        int indiceEncontrado = buscador.executa(produtos, new Produto(7L));
        assertEquals(0, produtoEsperado.compareTo(produtos.get(indiceEncontrado)));
    }

    @Test
    public void deveLancarNoSuchElementExceptionAoBuscarUmProdutoPorUmCodigoQueNaoExisteNaLista(){
        assertThrows(NoSuchElementException.class,
                () -> buscador.executa(produtos, new Produto(21L)));
    }

    @Test
    public void deveLancarNoSuchElementExceptionAoBuscarUmProdutoEmUmaListaVazia(){
        List<Produto> listaVazia = new ArrayList<>();
        assertThrows(NoSuchElementException.class,
                () -> buscador.executa(listaVazia, new Produto(2L)));
    }
}
