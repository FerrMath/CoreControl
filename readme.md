# CoreControl

### Status do projeto

* Em desenvolvimento;

## Objetivo do software:

Simplificar o controle de inventário de produtos com acompanhamento de entrada, saída e relatórios detalhados e intuitivos.

## Funcionalidades básicas:

* Autenticação e Autorização:
    * Cadstro e login de usuários;
    * Controle de acesso com diferentes níveis de permissão (administrador, usuário).

* Gestão de Produtos:
    * Cadastro de produtos (nome, descrição, categoria, quantidade em estoque, preço, etc.);
    * Atualização e exclusão de produtos.

* Controle de Estoque:
    * Registro de entradas e saídas de produtos.
    * Atualização automática do estoque após cada movimentação.

* Alerta de Reposição:
    * Configuração de níveis mínimos de estoque para cada produto.
    * Notificações automáticas quando o estoque estiver abaixo do nível mínimo.

* Relatórios e Consultas:
    * Geração de relatórios detalhados do inventário.
    * Consultas por produto, categoria, data de movimentação, etc.

* Exportação de dados:
    * Exportação de relatórios em formatos CSV, PDF, etc.

## Tecnologias usadas

* **Front End**: Thymeleaf e JavaScript.
* **Back End**: Spring Boot e Hibernate.
* **Banco de dados**: MySQL.

### Time de desenvolvedores

* Matheus Alcantara
