> _Preencha as seções abaixo para facilitar a revisão e a integração do seu código._

---

## 📝 Descrição do PR

<!-- Descreva brevemente o que este PR faz e por que ele é necessário. -->

<!-- Exemplo:  Este PR implementa uma nova estratégia de sincronização para o averaging de gradientes no DHT, reduzindo latência e melhorando a estabilidade em conexões instáveis. -->

---

## 📌 Tópicos relacionados

- [ ] Correção de bug
- [ ] Nova funcionalidade
- [ ] Refatoração
- [ ] Melhorias de desempenho
- [ ] Documentação
- [ ] Testes
- [ ] Outros: ____________________

---

## 🔍 Mudanças principais

- [x] Adiciona `AdaptiveAverager` ao módulo de sincronização.
- [x] Modifica a interface `run_trainer.py` para aceitar novos parâmetros.
- [ ] Atualiza documentação em `README.md`.
- [ ] Adiciona testes unitários para o novo componente.

---

## 🧪 Como testar

```bash
# Ambiente virtual
python3 -m venv venv && source venv/bin/activate
pip install -r requirements.txt

# Executar testes relevantes
pytest tests/sync/
python run_trainer.py --config configs/test_config.yaml
