package br.com.rchlo.main.filtro.generico;

import br.com.rchlo.domain.Produto;

import java.util.List;
import java.util.stream.Collectors;

public class FiltroPorCodigo implements Filtravel{

    private Long codigo;

    public FiltroPorCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @Override
    public List<Produto> filtrar(List<Produto> lista) {
        return lista.stream()
            .filter(p -> codigo.equals(p.getCodigo())).collect(Collectors.toList());
    }
}
