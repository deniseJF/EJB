
    create table CLIENTES (
        id int4 not null,
        dtNasc date,
        bairro varchar(255),
        cep varchar(255),
        cidade varchar(255),
        endereco varchar(255),
        uf varchar(255),
        nome varchar(50) not null,
        primary key (id)
    );

    create table Telefone (
        id int4 not null,
        tipo int4 not null,
        ddd varchar(3),
        operadora varchar(255),
        telefone varchar(10),
        primary key (id, tipo)
    );

    create table USUARIOS (
        id int4 not null,
        e_mail varchar(30) not null,
        senha varchar(255) not null,
        primary key (id)
    );

    alter table Telefone 
        add constraint FKB2C2CF0AB5A7CE9D 
        foreign key (id) 
        references CLIENTES;
