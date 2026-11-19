# Sistema de Gestão de Reservas e Recursos

Aplicação desktop desenvolvida em **Java (Swing)** utilizando arquitetura **MVC + DAO** e banco de dados **SQL Server** para controle de usuários, acessos, recursos e reservas com auditoria.

## Funcionalidades
- Cadastro e gerenciamento de usuários e níveis de acesso
- Controle de recursos disponíveis e status de reserva
- Registro e consulta de reservas
- Log de auditoria via triggers e procedures no banco de dados

## Estrutura do Projeto
- `MVC/src/br/com/empresa/model`: Classes de entidade e DTOs
- `MVC/src/br/com/empresa/view`: Interfaces gráficas (Java Swing)
- `MVC/src/br/com/empresa/controller`: Regras de negócio e intermediação
- `MVC/src/br/com/empresa/dao`: Conexão e persistência no SQL Server
- `database/`: Scripts SQL de Views, Procedures e Triggers

## Como Executar o Projeto

### 1. Pré-requisitos
- **Java JDK 11** ou superior
- **SQL Server** (local ou remoto)
- Driver JDBC incluído em `MVC/lib/mssql-jdbc-12.6.5.jre11.jar`

### 2. Configurar o Banco de Dados
1. Crie um banco de dados no SQL Server com as tabelas do sistema (`Usuario`, `Acesso`, `Recurso`, `StatusReserva`, `Reserva` e tabelas de auditoria).
2. Execute o script localizado em `database/pj6.vw_proc_trig.sql` para criar as Views, Stored Procedures e Triggers.

### 3. Configurar a Conexão
1. Na pasta `MVC/`, copie o arquivo `config.properties.example` e renomeie a cópia para `config.properties`.
2. Abra o `config.properties` e preencha com os dados do seu servidor SQL Server:
   ```properties
   db.url=jdbc:sqlserver://SEU_SERVIDOR:1433;databaseName=SEU_BANCO;integratedSecurity=false;encrypt=optional;trustServerCertificate=true
   db.user=SEU_USUARIO
   db.password=SUA_SENHA
   ```

### 4. Rodar a Aplicação
Abra a pasta `MVC` na sua IDE (VS Code, Eclipse ou IntelliJ), certifique-se de que o `.jar` da pasta `lib/` está no Classpath e execute a classe `br.com.empresa.Main`.

