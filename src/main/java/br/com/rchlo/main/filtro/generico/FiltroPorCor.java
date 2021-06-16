package br.com.rchlo.main.filtro.generico;

import br.com.rchlo.domain.Cor;
import br.com.rchlo.domain.Produto;

import java.util.List;
import java.util.stream.Collectors;

public class FiltroPorCor implements Filtravel{

    private Cor cor;

    public FiltroPorCor(Cor cor) {
        this.cor = cor;
    }

    @Override
    public List<Produto> filtrar(List<Produto> lista) {
        return lista.stream()
            .filter(p -> cor.equals(p.getCor())).collect(Collectors.toList());
    }
}
