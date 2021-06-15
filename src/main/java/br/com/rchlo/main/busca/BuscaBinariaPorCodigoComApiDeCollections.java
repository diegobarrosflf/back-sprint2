package br.com.rchlo.main.busca;

import br.com.rchlo.domain.ListaDeProdutos;
import br.com.rchlo.domain.Produto;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class BuscaBinariaPorCodigoComApiDeCollections {

    public static void main(String[] args) {
        var busca = new BuscaBinariaPorCodigoComApiDeCollections();

        List<Produto> produtos = ListaDeProdutos.lista();

        try {

            var produtoASerBuscado = new Produto(7L);

            int indiceEncontrado = busca.executa(produtos, produtoASerBuscado);

            Produto produtoEncontrado = produtos.get(indiceEncontrado);

            System.out.println("\nÍndice encontrado: " + indiceEncontrado);
            System.out.println(produtoEncontrado.getNome() + " (cod. " + produtoEncontrado.getCodigo() + " - " + produtoEncontrado.getPesoEmGramas() + " g) custa R$ " + produtoEncontrado.getPrecoEfetivo());

        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        }

        try {

            var outroProdutoASerBuscado = new Produto(12345L);

            int indiceEncontrado = busca.executa(produtos, outroProdutoASerBuscado);

            Produto produtoEncontrado = produtos.get(indiceEncontrado);

            System.out.println("\nÍndice encontrado: " + indiceEncontrado);
            System.out.println(produtoEncontrado.getNome() + " (cod. " + produtoEncontrado.getCodigo() + " - " + produtoEncontrado.getPesoEmGramas() + " g) custa R$ " + produtoEncontrado.getPrecoEfetivo());

        } catch (NoSuchElementException ex) {
            System.out.println("\n" + ex.getMessage());
        }

    }

    public int executa(List<Produto> produtos, Produto produtoASerBuscado) {
        Collections.sort(produtos);

        int indiceEncontrado = Collections.binarySearch(produtos, produtoASerBuscado);
        if (indiceEncontrado < 0) {
            throw  new NoSuchElementException("O produto buscado não foi encontrado: " + produtoASerBuscado);
        }
        return indiceEncontrado;
    }

}
