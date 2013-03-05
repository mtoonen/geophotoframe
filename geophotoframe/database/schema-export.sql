
    create table gebruiker (
        id  serial not null,
        email varchar(255),
        fullname varchar(255),
        passwordhash varchar(255),
        passwordsalt varchar(255),
        username varchar(255) not null unique,
        primary key (id)
    );

    create table gebruiker_roles (
        gebruiker int4 not null,
        role int4 not null,
        primary key (gebruiker, role)
    );

    create table role (
        id int4 not null,
        role varchar(255),
        primary key (id)
    );

    alter table gebruiker_roles 
        add constraint FK693FF3941F22C326 
        foreign key (gebruiker) 
        references gebruiker;

    alter table gebruiker_roles 
        add constraint FK693FF394B42CCE32 
        foreign key (role) 
        references role;
