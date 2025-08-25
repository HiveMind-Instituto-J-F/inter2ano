package com.api_rest.service;
import com.api_rest.model.Produto;
import com.api_rest.repository.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class ServiceProduto {

    private final ProdutoRepository produtoRepository;

    public ServiceProduto(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ResponseEntity<String> inserirProduto(Produto produto) {
        produtoRepository.save(produto);
        return ResponseEntity.ok("Produto inserido com sucesso");
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto buscarProduto(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public ResponseEntity<String> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Produto produto1 = buscarProduto(id);

        produto1.setNome(produto.getNome());
        produto1.setDescricao(produto.getDescricao());
        produto1.setPreco(produto.getPreco());
        produto1.setQuantidadedeestoque(produto.getQuantidadedeestoque());

        produtoRepository.save(produto1);
        return ResponseEntity.ok("Produto atualizado com sucesso!");
    }

    public ResponseEntity<String> atualizarProdutoParcialmente(@PathVariable Long id,
                                                       @RequestBody Map<String, Object> updates) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);

        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();

            if (updates.containsKey("nome")) {
                produto.setNome( String.valueOf( updates.get("nome") ) );
            }

            if (updates.containsKey("descricao")) {
                produto.setDescricao( String.valueOf( updates.get("descricao") ));
            }

            if (updates.containsKey("preco")) {
                produto.setPreco( Double.parseDouble(String.valueOf(updates.get("preco"))) );
            }

            if (updates.containsKey("quantidadedeestoque")) {
                produto.setQuantidadedeestoque( Integer.parseInt( String.valueOf( updates.get("quantidadedeestoque") ) ) );
            }

            produtoRepository.save(produto);
            return ResponseEntity.ok("Produto atualizado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<String> excluirProduto(@PathVariable Long id) {
        produtoRepository.deleteById(id);
        return ResponseEntity.ok("Produto removido com sucesso!");
    }
}
