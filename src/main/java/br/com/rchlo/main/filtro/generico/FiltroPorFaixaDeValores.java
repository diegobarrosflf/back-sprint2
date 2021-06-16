package br.com.rchlo.main.filtro.generico;

import br.com.rchlo.domain.Produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroPorFaixaDeValores implements Filtravel{

    private BigDecimal valorMin;
    private BigDecimal valorMax;

    public FiltroPorFaixaDeValores(BigDecimal valorMin, BigDecimal valorMax) {
        this.valorMin = valorMin;
        this.valorMax = valorMax;
    }

    @Override
    public List<Produto> filtrar(List<Produto> lista) {
        return lista.stream()
            .filter(p -> p.getPrecoEfetivo().compareTo(valorMin) >= 0 )
                .filter(p -> p.getPrecoEfetivo().compareTo(valorMax) <= 0 )
                .collect(Collectors.toList());
    }
}
