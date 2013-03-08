
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

    create table geoservice (
        id  serial not null,
        description varchar(255),
        layers varchar(255),
        name varchar(255),
        type varchar(255),
        url varchar(255),
        gebruiker int4,
        primary key (id)
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

    alter table geoservice 
        add constraint FK49728A441F22C326 
        foreign key (gebruiker) 
        references gebruiker;
