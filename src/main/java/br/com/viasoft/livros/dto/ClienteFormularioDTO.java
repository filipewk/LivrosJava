package br.com.viasoft.livros.dto;

import br.com.viasoft.livros.model.Cliente;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class ClienteFormularioDTO {
    private Long id;
    @NotEmpty(message = "Coloca um nome ai.")
    private String nome;
    @NotEmpty(message = "Coloca um email ai.")
    private String email;
    private String cpf;
    private String endereco;

    public Cliente toCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(this.id);
        cliente.setNome(this.nome);
        cliente.setEmail(this.email);
        cliente.setCpf(this.cpf);
        cliente.setEndereco(this.endereco);
        return cliente;
    }

    public ClienteFormularioDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.cpf = cliente.getCpf();
        this.endereco = cliente.getEndereco();
    }
}
