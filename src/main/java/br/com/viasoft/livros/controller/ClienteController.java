package br.com.viasoft.livros.controller;

import br.com.viasoft.livros.dto.ClienteFormularioDTO;
import br.com.viasoft.livros.model.Cliente;
import br.com.viasoft.livros.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @GetMapping("/cliente")
    public String getHome(Model model) {
        List<Cliente> lista = clienteService.findAll();
        model.addAttribute("lista", lista);
        return "cliente/cliente";
    }

    @GetMapping("/cliente/novo")
    public String getCliente(ClienteFormularioDTO clienteFormularioDTO){
        return "cliente/formulariocliente";
    }

    @PostMapping("/cliente/salvar")
    public String formCliente(@Valid ClienteFormularioDTO clienteDTO, BindingResult result) {
        Cliente cliente = clienteDTO.toCliente();
        clienteService.save(cliente);
        return "redirect:/cliente/" + cliente.getId();
    }

    @GetMapping("/cliente/edit/{id}")
    public String editaCliente(@PathVariable("id") Long id, ClienteFormularioDTO clienteFormularioDTO, Model model) {
        var c1 = clienteService.findById(id).orElse(null);
        clienteFormularioDTO = new ClienteFormularioDTO(c1);
        model.addAttribute("cliente", c1);
        model.addAttribute("dto", clienteFormularioDTO);
        return "cliente/editcliente";
    }

    @GetMapping("/cliente/{id}")
    public String listaClienteById(@PathVariable("id") Long id, Model model) {
        Cliente c1 = clienteService.findById(id).orElse(null);
        if (c1 == null) {
            return "redirect:/produto/";
        }
        model.addAttribute("cliente", c1);
        return "cliente/clientedetail";
    }

    @PostMapping("/cliente/salvar/{id}")
    public String saveClienteExistente(@PathVariable("id") Long id, @Valid ClienteFormularioDTO clienteDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "cliente/editcliente";
        }

        Cliente cliente = clienteDTO.toCliente();
        cliente.setId(id);
        clienteService.save(cliente);
        return "redirect:/cliente/" + cliente.getId();
    }

    @GetMapping("cliente/delete/{id}")
    public String removeCliente(@PathVariable("id") Long id, Principal principal) {
        var roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<String> cargos = roles.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        if (cargos.get(0).equals("ROLE_ADM")){
            clienteService.delete(id);
            return "redirect:/cliente/";
        }
        return "redirect:/cliente/edit/" + id;
    }
}
