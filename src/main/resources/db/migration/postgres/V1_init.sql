create table endereco (
       id int4 not null,
        bairro varchar(255) not null,
        cidade varchar(255) not null,
        complemento varchar(255),
        estudo varchar(255) not null,
        numero int4 not null,
        rua varchar(255) not null,
        primary key (id)
    )
   
    create table equipe (
       id int4 not null,
        nome varchar(255) not null,
        primary key (id)
    )

    
    create table funcionario (
       id int4 not null,
        data_contratacao timestamp not null,
        data_nascimento timestamp not null,
        matricula int4 not null,
        nome varchar(255) not null,
        endereco_id int4,
        equipe_id int4,
        primary key (id)
    )
	create sequence hibernate_sequence start 1 increment 1

    
    alter table if exists funcionario 
       add constraint FKld57l49lwv8ik9ottu7yo5d6k 
       foreign key (endereco_id) 
       references endereco

    
    alter table if exists funcionario 
       add constraint FKg1mnrsnylt6tn82ki5gkvkyb2 
       foreign key (equipe_id) 
       references equipe