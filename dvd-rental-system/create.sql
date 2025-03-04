
    create table actors (
        id uuid not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table categories (
        id uuid not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table customers (
        late_fees float(53),
        id uuid not null,
        email varchar(255) not null unique,
        name varchar(255) not null,
        phone varchar(255),
        primary key (id)
    );

    create table film_actors (
        actor_id uuid not null,
        film_id uuid not null
    );

    create table films (
        is_available boolean not null,
        rental_duration_days integer,
        category_id uuid,
        id uuid not null,
        description varchar(1000),
        title varchar(255) not null,
        primary key (id)
    );

    create table rentals (
        late_fee float(53),
        due_date timestamp(6),
        rental_date timestamp(6) not null,
        return_date timestamp(6),
        customer_id uuid not null,
        film_id uuid not null,
        id uuid not null,
        primary key (id)
    );

    alter table if exists film_actors 
       add constraint FKrs472oyyff3hfwq10pyo94k1d 
       foreign key (actor_id) 
       references actors;

    alter table if exists film_actors 
       add constraint FK6tb1ng95xuq2rm8xgu3d09t6x 
       foreign key (film_id) 
       references films;

    alter table if exists films 
       add constraint FKf5rs4ila5dtovsrf1crhilxwh 
       foreign key (category_id) 
       references categories;

    alter table if exists rentals 
       add constraint FKcxn0lr4sjtxi7u4nxshbaj83u 
       foreign key (customer_id) 
       references customers;

    alter table if exists rentals 
       add constraint FKnqvfecjj3llf7yvv0oc85pn17 
       foreign key (film_id) 
       references films;
