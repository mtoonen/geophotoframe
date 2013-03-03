
    create table role (
        id int4 not null,
        role varchar(255),
        primary key (id)
    );

    create table user (
        id  serial not null,
        email varchar(255),
        fullname varchar(255),
        passwordhash varchar(255),
        passwordsalt varchar(255),
        username varchar(255) not null unique,
        primary key (id)
    );

    create table user_roles (
        user int4 not null,
        role int4 not null,
        primary key (user, role)
    );

    alter table user_roles 
        add constraint FK73429949B42CCE32 
        foreign key (role) 
        references role;

    alter table user_roles 
        add constraint FK73429949B42FA4DC 
        foreign key (user) 
        references user;
