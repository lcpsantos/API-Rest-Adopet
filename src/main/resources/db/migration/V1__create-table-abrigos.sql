create table abrigos(
    id SERIAL PRIMARY KEY,
    nome varchar(100) not null unique,
    telefone varchar(20) not null,
    email varchar(100) not null
);