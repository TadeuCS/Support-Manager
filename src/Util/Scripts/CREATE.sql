CREATE TABLE STATUS_PESSOA (
  CODSTATUSPESSOA int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO       varchar(20) NOT NULL UNIQUE, 
  PRIMARY KEY (CODSTATUSPESSOA));
CREATE TABLE UF (
  CODUF     int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO varchar(100) NOT NULL, 
  SIGLA     char(2) NOT NULL UNIQUE, 
  PRIMARY KEY (CODUF));
CREATE TABLE CIDADE (
  CODCIDADE        int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO        varchar(150) NOT NULL, 
  CODIBGEMUNICIPIO int(10) NOT NULL UNIQUE, 
  CODUF            int(10) NOT NULL, 
  PRIMARY KEY (CODCIDADE));
CREATE TABLE TIPO_PESSOA (
  CODTIPOPESSOA int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO     varchar(100) NOT NULL UNIQUE, 
  PRIMARY KEY (CODTIPOPESSOA));
CREATE TABLE LINK_CLIENTE (
  LINKCODLINK       int(10) NOT NULL, 
  CLIENTECODCLIENTE int(10) NOT NULL, 
  QUANTIDADE        int(3) NOT NULL, 
  PRIMARY KEY (LINKCODLINK, 
  CLIENTECODCLIENTE));
CREATE TABLE GRUPO (
  CODGRUPO  int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO varchar(150) NOT NULL UNIQUE, 
  PRIMARY KEY (CODGRUPO));
CREATE TABLE SEGMENTO (
  CODSEGMENTO int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO   varchar(255) NOT NULL UNIQUE, 
  PRIMARY KEY (CODSEGMENTO));
CREATE TABLE TELEFONE (
  CODTELEFONE int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO   varchar(100) NOT NULL, 
  TELEFONE    varchar(20) NOT NULL UNIQUE, 
  CODCLIENTE  int(10), 
  CODGRUPO    int(10) NOT NULL, 
  CODUSUARIO  int(10), 
  PRIMARY KEY (CODTELEFONE));
CREATE TABLE TIPO_INFORMACAO (
  CODTIPOINFORMACAO int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO         varchar(255) NOT NULL UNIQUE, 
  PRIMARY KEY (CODTIPOINFORMACAO));
CREATE TABLE INFORMACAO (
  CODINFORMACAO     int(10) NOT NULL AUTO_INCREMENT, 
  OBSERVACAO        varchar(255) NOT NULL, 
  DATA              date NOT NULL, 
  CODTIPOINFORMACAO int(10) NOT NULL, 
  CODUSUARIO        int(10) NOT NULL, 
  PRIMARY KEY (CODINFORMACAO));
CREATE TABLE LINK (
  CODLINK       int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO     varchar(255) NOT NULL UNIQUE, 
  CODAPLICATIVO int(10) NOT NULL, 
  PRIMARY KEY (CODLINK));
CREATE TABLE ENDERECO (
  CODENDERECO int(10) NOT NULL AUTO_INCREMENT, 
  NUMERO      int(10) NOT NULL, 
  COMPLEMENTO varchar(255), 
  BAIRRO      varchar(255) NOT NULL, 
  RUA         varchar(255) NOT NULL, 
  CEP         varchar(9) NOT NULL, 
  CODCLIENTE  int(10) NOT NULL, 
  CODCIDADE   int(10) NOT NULL, 
  PRIMARY KEY (CODENDERECO));
CREATE TABLE EMAIL (
  CODEMAIL int(10) NOT NULL AUTO_INCREMENT, 
  EMAIL    varchar(255) NOT NULL, 
  SMTP     varchar(255) NOT NULL, 
  PORTA    int(10) NOT NULL, 
  NOME     varchar(255) NOT NULL, 
  SENHA    varchar(255) NOT NULL, 
  PRIMARY KEY (CODEMAIL));
CREATE TABLE EMPRESA (
  CODEMPRESA    int(10) NOT NULL AUTO_INCREMENT, 
  NOME_FANTASIA varchar(255) NOT NULL UNIQUE, 
  CNPJ_CPF      varchar(20) NOT NULL UNIQUE, 
  TELEFONE      varchar(20) NOT NULL, 
  CODEMAIL      int(10) NOT NULL, 
  CODCONTATO    int(10) NOT NULL, 
  CODTELEFONE   int(10) NOT NULL, 
  PRIMARY KEY (CODEMPRESA));
CREATE TABLE STATUS_ATENDIMENTO (
  CODSTATUSATENDIMENTO int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO            varchar(100) NOT NULL UNIQUE, 
  PRIMARY KEY (CODSTATUSATENDIMENTO));
CREATE TABLE TIPO_USUARIO (
  CODTIPOUSUARIO int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO      varchar(100) NOT NULL UNIQUE, 
  PRIMARY KEY (CODTIPOUSUARIO));
CREATE TABLE USUARIO (
  CODUSUARIO      int(10) NOT NULL AUTO_INCREMENT, 
  NOME            varchar(255) NOT NULL, 
  CPF             varchar(14) NOT NULL UNIQUE, 
  SEXO            char(1) NOT NULL, 
  EMAIL           varchar(255) NOT NULL UNIQUE, 
  USUARIO         varchar(255) NOT NULL UNIQUE, 
  SENHA           varchar(255) NOT NULL, 
  CODTIPOUSUARIO  int(10) NOT NULL, 
  CODSTATUSPESSOA int(10) NOT NULL, 
  PRIMARY KEY (CODUSUARIO));
CREATE TABLE PRIORIDADE (
  CODPRIORIDADE int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO     varchar(100) NOT NULL UNIQUE, 
  PRIMARY KEY (CODPRIORIDADE));
CREATE TABLE ORIGEM (
  CODORIGEM int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO varchar(100) NOT NULL UNIQUE, 
  PRIMARY KEY (CODORIGEM));
CREATE TABLE TIPO_ATENDIMENTO (
  CODTIPOATENDIMENTO int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO          varchar(100) NOT NULL UNIQUE, 
  PRIMARY KEY (CODTIPOATENDIMENTO));
CREATE TABLE ATENDIMENTO (
  CODATENDIMENTO       int(10) NOT NULL AUTO_INCREMENT, 
  DATA_ABERTURA        datetime NOT NULL, 
  DATA_AGENDAMENTO     datetime NULL, 
  DATA_FECHAMENTO      datetime NULL, 
  DATA_CANCELAMENTO    datetime NULL, 
  DATA_INICIO          datetime NULL, 
  DATA_FIM             datetime NULL, 
  SOLICITANTE          varchar(100), 
  PROBLEMA_INFORMADO   varchar(255) NOT NULL, 
  PROBLEMA_DETECTADO   varchar(255), 
  PROBLEMA_PENDENCIA   varchar(255), 
  PROBLEMA_SOLUCAO     varchar(255), 
  PENDENCIA            char(1) NOT NULL, 
  DATA_SOLUCAO         datetime NULL, 
  CODTIPOATENDIMENTO   int(10) NOT NULL, 
  CODORIGEM            int(10) NOT NULL, 
  CODPRIORIDADE        int(10) NOT NULL, 
  CODUSUARIO           int(10) NOT NULL, 
  CODCLIENTE           int(10) NOT NULL, 
  CODSTATUSATENDIMENTO int(10) NOT NULL, 
  PRIMARY KEY (CODATENDIMENTO));
CREATE TABLE APLICATIVO (
  CODAPLICATIVO int(10) NOT NULL AUTO_INCREMENT, 
  DESCRICAO     varchar(100) NOT NULL UNIQUE, 
  PRIMARY KEY (CODAPLICATIVO));
CREATE TABLE CLIENTE (
  CODCLIENTE         int(10) NOT NULL AUTO_INCREMENT, 
  REFERENCIA         int(10), 
  RAZAO_SOCIAL       varchar(255) NOT NULL UNIQUE, 
  NOME_FANTASIA      varchar(255) NOT NULL, 
  RESPONSAVEL        varchar(100) NOT NULL, 
  CNPJ_CPF           varchar(255) NOT NULL UNIQUE, 
  INSCRICAO_ESTADUAL varchar(100) NOT NULL, 
  EMAIL              varchar(255) NOT NULL UNIQUE, 
  DATA_ATUALIZACAO   datetime NULL, 
  CODSEGMENTO        int(10) NOT NULL, 
  CODTIPOPESSOA      int(10) NOT NULL, 
  CODSTATUSPESSOA    int(10) NOT NULL, 
  PRIMARY KEY (CODCLIENTE));
ALTER TABLE ATENDIMENTO ADD INDEX FKATENDIMENT382556 (CODCLIENTE), ADD CONSTRAINT FKATENDIMENT382556 FOREIGN KEY (CODCLIENTE) REFERENCES CLIENTE (CODCLIENTE);
ALTER TABLE ATENDIMENTO ADD INDEX FKATENDIMENT289250 (CODUSUARIO), ADD CONSTRAINT FKATENDIMENT289250 FOREIGN KEY (CODUSUARIO) REFERENCES USUARIO (CODUSUARIO);
ALTER TABLE USUARIO ADD INDEX FKUSUARIO900738 (CODTIPOUSUARIO), ADD CONSTRAINT FKUSUARIO900738 FOREIGN KEY (CODTIPOUSUARIO) REFERENCES TIPO_USUARIO (CODTIPOUSUARIO);
ALTER TABLE ATENDIMENTO ADD INDEX FKATENDIMENT565725 (CODPRIORIDADE), ADD CONSTRAINT FKATENDIMENT565725 FOREIGN KEY (CODPRIORIDADE) REFERENCES PRIORIDADE (CODPRIORIDADE);
ALTER TABLE ATENDIMENTO ADD INDEX FKATENDIMENT252746 (CODORIGEM), ADD CONSTRAINT FKATENDIMENT252746 FOREIGN KEY (CODORIGEM) REFERENCES ORIGEM (CODORIGEM);
ALTER TABLE ATENDIMENTO ADD INDEX FKATENDIMENT799707 (CODTIPOATENDIMENTO), ADD CONSTRAINT FKATENDIMENT799707 FOREIGN KEY (CODTIPOATENDIMENTO) REFERENCES TIPO_ATENDIMENTO (CODTIPOATENDIMENTO);
ALTER TABLE EMPRESA ADD INDEX FKEMPRESA64388 (CODEMAIL), ADD CONSTRAINT FKEMPRESA64388 FOREIGN KEY (CODEMAIL) REFERENCES EMAIL (CODEMAIL);
ALTER TABLE ENDERECO ADD INDEX FKENDERECO434911 (CODCLIENTE), ADD CONSTRAINT FKENDERECO434911 FOREIGN KEY (CODCLIENTE) REFERENCES CLIENTE (CODCLIENTE);
ALTER TABLE INFORMACAO ADD INDEX FKINFORMACAO28001 (CODUSUARIO), ADD CONSTRAINT FKINFORMACAO28001 FOREIGN KEY (CODUSUARIO) REFERENCES USUARIO (CODUSUARIO);
ALTER TABLE INFORMACAO ADD INDEX FKINFORMACAO160939 (CODTIPOINFORMACAO), ADD CONSTRAINT FKINFORMACAO160939 FOREIGN KEY (CODTIPOINFORMACAO) REFERENCES TIPO_INFORMACAO (CODTIPOINFORMACAO);
ALTER TABLE LINK ADD INDEX FKLINK898337 (CODAPLICATIVO), ADD CONSTRAINT FKLINK898337 FOREIGN KEY (CODAPLICATIVO) REFERENCES APLICATIVO (CODAPLICATIVO);
ALTER TABLE TELEFONE ADD INDEX FKTELEFONE97107 (CODCLIENTE), ADD CONSTRAINT FKTELEFONE97107 FOREIGN KEY (CODCLIENTE) REFERENCES CLIENTE (CODCLIENTE);
ALTER TABLE CLIENTE ADD INDEX FKCLIENTE743034 (CODSEGMENTO), ADD CONSTRAINT FKCLIENTE743034 FOREIGN KEY (CODSEGMENTO) REFERENCES SEGMENTO (CODSEGMENTO);
ALTER TABLE TELEFONE ADD INDEX FKTELEFONE425300 (CODUSUARIO), ADD CONSTRAINT FKTELEFONE425300 FOREIGN KEY (CODUSUARIO) REFERENCES USUARIO (CODUSUARIO);
ALTER TABLE ATENDIMENTO ADD INDEX FKATENDIMENT370870 (CODSTATUSATENDIMENTO), ADD CONSTRAINT FKATENDIMENT370870 FOREIGN KEY (CODSTATUSATENDIMENTO) REFERENCES STATUS_ATENDIMENTO (CODSTATUSATENDIMENTO);
ALTER TABLE USUARIO ADD INDEX FKUSUARIO105239 (CODSTATUSPESSOA), ADD CONSTRAINT FKUSUARIO105239 FOREIGN KEY (CODSTATUSPESSOA) REFERENCES STATUS_PESSOA (CODSTATUSPESSOA);
ALTER TABLE CLIENTE ADD INDEX FKCLIENTE671977 (CODSTATUSPESSOA), ADD CONSTRAINT FKCLIENTE671977 FOREIGN KEY (CODSTATUSPESSOA) REFERENCES STATUS_PESSOA (CODSTATUSPESSOA);
ALTER TABLE ENDERECO ADD INDEX FKENDERECO485117 (CODCIDADE), ADD CONSTRAINT FKENDERECO485117 FOREIGN KEY (CODCIDADE) REFERENCES CIDADE (CODCIDADE);
ALTER TABLE CIDADE ADD INDEX FKCIDADE260299 (CODUF), ADD CONSTRAINT FKCIDADE260299 FOREIGN KEY (CODUF) REFERENCES UF (CODUF);
ALTER TABLE CLIENTE ADD INDEX FKCLIENTE863458 (CODTIPOPESSOA), ADD CONSTRAINT FKCLIENTE863458 FOREIGN KEY (CODTIPOPESSOA) REFERENCES TIPO_PESSOA (CODTIPOPESSOA);
ALTER TABLE LINK_CLIENTE ADD INDEX FKLINK_CLIEN906105 (CLIENTECODCLIENTE), ADD CONSTRAINT FKLINK_CLIEN906105 FOREIGN KEY (CLIENTECODCLIENTE) REFERENCES CLIENTE (CODCLIENTE);
ALTER TABLE LINK_CLIENTE ADD INDEX FKLINK_CLIEN782744 (LINKCODLINK), ADD CONSTRAINT FKLINK_CLIEN782744 FOREIGN KEY (LINKCODLINK) REFERENCES LINK (CODLINK);
ALTER TABLE TELEFONE ADD INDEX FKTELEFONE973581 (CODGRUPO), ADD CONSTRAINT FKTELEFONE973581 FOREIGN KEY (CODGRUPO) REFERENCES GRUPO (CODGRUPO);
ALTER TABLE EMPRESA ADD INDEX FKEMPRESA917507 (CODTELEFONE), ADD CONSTRAINT FKEMPRESA917507 FOREIGN KEY (CODTELEFONE) REFERENCES TELEFONE (CODTELEFONE);