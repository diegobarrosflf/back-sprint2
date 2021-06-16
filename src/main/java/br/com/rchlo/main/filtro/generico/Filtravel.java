package br.com.rchlo.main.filtro.generico;

import br.com.rchlo.domain.Produto;

import java.util.List;

public interface Filtravel {

    public List<Produto> filtrar(List<Produto> lista);
}
