# LocaHouse

LocaHouse é uma aplicação web que tem como objetivo conectar proprietários de imóveis para moradia (casas e
apartamentos) e inquilinos. A plataforma permite que proprietários de imóveis publiquem suas propriedades disponíveis
para aluguel, enquanto inquilinos podem buscar essas propriedades de forma simples e eficaz. 🏡

## Tecnologias Utilizadas

### Back-End

O back-end do LocaHouse foi desenvolvido utilizando as seguintes tecnologias:

- **Java**: Linguagem de programação robusta e amplamente utilizada no desenvolvimento de aplicações web. ☕
- **Spring Framework**: Framework Java poderoso que facilita a construção de aplicações empresariais. Utilizamos o
  Spring Boot para agilizar a criação da aplicação, com configuração mínima e inicialização rápida. ⚙️
- **Hibernate**: Framework de mapeamento objeto-relacional (ORM) para Java, que simplifica a integração com o banco de
  dados, permitindo que os desenvolvedores trabalhem com objetos Java em vez de SQL bruto. 🔄

### Banco de Dados

A aplicação utiliza o **MySQL**, um dos sistemas de gerenciamento de banco de dados mais populares, para armazenar
informações sobre os imóveis e usuários. A escolha do MySQL permite que o sistema seja escalável, confiável e de fácil
manutenção. 🗃️

### Front-End

Para o front-end da aplicação, foram utilizadas as seguintes tecnologias:

- **HTML**: Estrutura básica do site, que define a marcação e o conteúdo da página. 🌐
- **CSS**: Estilização das páginas, garantindo uma interface agradável e moderna. 🎨
- **JavaScript**: Linguagem de programação que adiciona interatividade à aplicação, proporcionando uma experiência de
  usuário dinâmica e fluida. 💻
- **Bootstrap**: Framework de front-end que agiliza o design responsivo e a criação de interfaces de usuário modernas.
  📱
- **jQuery**: Biblioteca JavaScript que facilita a manipulação de elementos do DOM, eventos e requisições assíncronas.
  🧩

Link: [Repositório do Front-End](https://github.com/samuel-fm-coding/Projeto-Front)

## Principais Funcionalidades

- **Cadastro de usuário**. ✍️
- **Sistema de Login e Autenticação**. 🔐
- **Cadastro de imóveis**. 🏠
- **Busca de imóveis**. 🔍

## Como Executar o Projeto

### Pré-requisitos

- Java 21 ou superior. ☕
- MySQL. 🗄️
- Maven. ⚡
- IntelliJ IDEA. 💡
- VSCode com o plugin Live Server. 🖥️

### Instalação e Execução

1. Clone este repositório:
   ```bash
   git clone https://github.com/gabrielravanhan/locahouse-api.git

2. Clone o repositório com o front-end:
   ```bash
   git clone https://github.com/samuel-fm-coding/Projeto-Front.git

3. Abra o repositório com o front-end e execute o Live Server no arquivo **index.html**. 🌍

4. Abra este repositório no IntelliJ e crie as seguintes variáveis de ambiente:
    - USER_DB: informe o usuário do banco de dados.
    - PASSWORD_DB: informe a senha do usuário do banco de dados.
    - URL_FRONTEND: informe a URL em que o front-end do projeto é executado pelo Live Server (geralmente
      é http://127.0.0.1:5500).
    - CAMINHO_IMAGENS_IMOVEIS: caminho onde as imagens dos imóveis serão armazenadas.

5. Ainda no IntelliJ, execute o projeto. 🚀

6. Vá até a página aberta pelo Live Server no navegador e pronto, a aplicação está instalada e executando! 🎉
