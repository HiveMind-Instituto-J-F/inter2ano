package com.api_rest.controller;

import com.api_rest.model.Produto;
import com.api_rest.service.ServiceProduto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produtos")
public class Controller {

    public final ServiceProduto serviceProduto;

    public Controller(ServiceProduto serviceProduto) {
        this.serviceProduto = serviceProduto;
    }

    @GetMapping("/selecionar")
    public List<Produto> selecionar(){
        return serviceProduto.listarProdutos();
    }

    @GetMapping("/selecionar/{id}")
    public Produto selecionar(@PathVariable Long id){
        return serviceProduto.buscarProduto(id);
    }

    @PostMapping("/inserir")
    public ResponseEntity<String> inserirProduto(@Valid @RequestBody Produto produto){
        return serviceProduto.inserirProduto(produto);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarProduto(
            @PathVariable Long id,
            @Valid @RequestBody Produto produto)
    {
        return serviceProduto.atualizarProduto(id, produto);
    }

    @PatchMapping("/atualizarParcial/{id}")
    public ResponseEntity<String> atualizarParcialmenteProduto(
            @PathVariable Long id,
            @Valid @RequestBody Map<String, Object> produto)
    {
        return serviceProduto.atualizarProdutoParcialmente(id, produto);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable Long id){
        return serviceProduto.excluirProduto(id);
    }
}
