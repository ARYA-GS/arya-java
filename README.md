# Arya - Plataforma de Gestão de Desastres

# Aluno	RM

#### Keven Ike Pereira da Silva	553215

#### Vitor Cruz dos Santos  553621

#### José Ribeiro dos Santos Neto 553844


Link do Video [video](https://youtu.be/8YhITEJctlA)

## 🔗 Collection Postman

Você pode acessar e testar os endpoints da API utilizando a collection do Postman abaixo:

[Clique aqui para abrir a Collection ARYA no Postman](https://prevdent.postman.co/workspace/arya~d8c7cb5c-99e2-4d00-99b7-050f91256ee3/collection/36751202-176f84ca-ad47-4776-ac05-08a9d0f33f69?action=share&source=collection_link&creator=36751202)

Caso prefira importar manualmente, o arquivo `ARYA.postman_collection.json` também está disponível neste repositório.


**Arya** é uma plataforma robusta, alimentada por IA, projetada para o gerenciamento eficiente de desastres naturais e outros incidentes de grande escala. Ela utiliza uma frota de drones, análise inteligente de dados e comunicação em tempo real para fornecer suporte crítico às equipes operacionais.

A plataforma permite o registro de hubs operacionais, drones e usuários. Ela pode rastrear incidentes, sugerir o drone mais adequado para uma missão específica usando IA e fornecer previsões de risco com base em dados históricos e nas condições climáticas atuais.

## Principais características

- **Gerenciamento de usuários**: Registro seguro e autenticação de usuários com controle de acesso baseado em funções.
- **Centros Operacionais**: Registre e gerencie centros centrais de onde as operações de drones são coordenadas.
- **Gerenciamento de frota de drones**: Adicione novos drones à frota, especificando suas capacidades, como alcance, carga útil e funções.
- **Relatório de incidentes**: Os usuários podem relatar incidentes, detalhando o tipo, a gravidade e o local.
- **Sugestões de drones com tecnologia de IA**: Para um determinado incidente, a IA do sistema pode analisar os drones disponíveis e sugerir o mais adequado para a missão.
- **Perguntas e respostas inteligentes**: Os usuários podem fazer perguntas em linguagem natural sobre os incidentes registrados para obter respostas rápidas e consolidadas.
- **Previsão de risco**: A plataforma usa dados históricos de incidentes e informações meteorológicas em tempo real para prever áreas de alto risco.
- **Despacho automatizado de drones**: Os drones são despachados automaticamente para incidentes de alta gravidade com base em um agendador.
- **API RESTful**: Uma API abrangente e bem estruturada para fácil integração com outros sistemas.

## Pilha de tecnologia

- **Backend**: Java 21, Spring Boot 3.2.5
- **Banco de dados**: Spring Data JPA com suporte para H2 (Teste), Oracle (Dev) e Azure SQL.
- **Segurança**: Spring Security com JWT para autenticação baseada em token.
- **Inteligência Artificial**: Spring AI conectado ao Azure OpenAI para recursos inteligentes.
- **Mensagens**: Spring AMQP com RabbitMQ para comunicação assíncrona e notificações.
- **Clientes de API**: Spring Cloud OpenFeign para comunicação de API REST declarativa com serviços externos (OpenWeather e OpenCage).
- **Gerenciamento de construção e dependências**: Apache Maven
- **Conteinerização**: Docker, Docker Compose
- **CI/CD**: Pipelines do Azure

## Como executar o aplicativo

### Pré-requisitos

- Java 21
- Apache Maven
- Docker e Docker Compose (para execução com dependências)

### Usando o Docker Compose (recomendado)

A maneira mais fácil de executar o aplicativo e suas dependências é usando o Docker Compose.

1. Clone o repositório.
2. Navegue até o diretório raiz do projeto.
3. Execute o seguinte comando:

```bash
docker-compose up -d
````
 #Configuração

O aplicativo usa um sistema de configuração baseado em perfil:

- **`application-dev.yml`**: Para desenvolvimento, conecta-se a um banco de dados Oracle e a uma instância local do RabbitMQ.
- **`application-test.yml`**: Para testes, usa um banco de dados H2 na memória.
- **`application-az.yml`**: Para produção no Azure, conecta-se a um banco de dados SQL do Azure.

> O perfil ativo pode ser alterado no arquivo **`application.yml`**.


## Segurança

Os endpoints do aplicativo são protegidos usando **Spring Security** e **JWT**.

### Autenticação

A autenticação é gerenciada pelo endpoint:

- `POST /auth/login`: Retorna um JWT após autenticação bem-sucedida.

### Autorização

- O acesso **público** é concedido aos seguintes endpoints:
  - `POST /usuarios`
  - `POST /auth/login`
- A função **`ADMIN`** é necessária para gerenciar usuários:
  - `GET`, `DELETE` ou `PATCH` em `/usuarios/**`
- **Todos os outros endpoints** exigem um JWT válido de um usuário autenticado.

A classe `TokenService` é responsável por **gerar** e **validar** os tokens JWT.

## Pontos de Extremidade da API

### Autenticação

- `POST /auth/login`: Autentica um usuário e retorna um JWT.

### Usuários

- `POST /usuarios`: Registra um novo usuário.
- `GET /usuarios`: Lista todos os usuários registrados (**somente administrador**).
- `GET /usuarios/{id}`: Recupera um usuário pelo seu ID (**somente administrador**).
- `DELETE /usuarios/{id}`: Exclui um usuário pelo seu ID (**somente administrador**).
- `PATCH /usuarios/{id}/senha`: Permite que um usuário altere sua senha.
- `PATCH /usuarios/resetar-senha`: Redefine a senha de um usuário usando seu e-mail.

### Drones

- `POST /drones`: Registra um novo drone.
- `GET /drones`: Lista todos os drones disponíveis.
- `GET /drones/{id}`: Recupera um drone pelo seu ID.
- `PUT /drones/{id}`: Atualiza as informações de um drone.
- `DELETE /drones/{id}`: Exclui um drone.

### Centros Operacionais

- `POST /hubs`: Registra um novo hub operacional.
- `GET /hubs`: Lista todos os hubs operacionais.
- `GET /hubs/{id}`: Recupera um hub pelo seu ID.
- `PUT /hubs/{id}`: Atualiza as informações de um hub.
- `DELETE /hubs/{id}`: Exclui um hub.

### Ocorrências (Incidents)

- `POST /ocorrencias`: Relata um novo incidente.
- `GET /ocorrencias`: Lista todos os incidentes relatados.
- `GET /ocorrencias/{id}`: Recupera um incidente pelo seu ID.
- `DELETE /ocorrencias/{id}`: Exclui um incidente.
- `GET /ocorrencias/{id}/resumo`: Gera um resumo do incidente usando IA.

### Inteligência Artificial

- `POST /ia/perguntar`: Faça uma pergunta em linguagem natural sobre os incidentes.
- `GET /ia/sugerir-drone/{idOcorrencia}`: Obtenha uma sugestão com tecnologia de IA para o melhor drone para lidar com um incidente específico.
- `GET /ia/previsao-risco`: Obtenha uma previsão de risco com base em dados históricos e clima em tempo real.


## Pipeline de CI/CD

O projeto inclui um arquivo **`azure-pipelines.yml`** que define um pipeline de **CI/CD** para o **Azure DevOps**.
