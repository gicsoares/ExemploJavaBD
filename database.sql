create database Contato;

create table contato(
    id SERIAL NOT NULL PRIMARY KEY,
    nome VARCHAR(60),
    email VARCHAR(30),
    telefone VARCHAR(15) 
);