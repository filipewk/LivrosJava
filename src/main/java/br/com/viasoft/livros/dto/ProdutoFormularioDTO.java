package br.com.viasoft.livros.dto;

import br.com.viasoft.livros.model.Produto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ProdutoFormularioDTO {
    @NotEmpty(message = "Coloca um nome ai.")
    private String nome;
    @NotEmpty(message = "Coloca um autor ai.")
    private String autor;
    private String imagem;

    public Produto toProduto() {
        Produto produto = new Produto();
        produto.setNome(this.nome);
        produto.setAutor(this.autor);
        produto.setImagem(this.imagem);
        return produto;
    }
}
