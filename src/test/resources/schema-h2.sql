create table if not exists style
(
    id_style int         not null generated by default as identity,
    st_name  varchar(90) not null,
    primary key (id_style),
    unique (st_name)
);

create table if not exists author
(
    id_author   int         not null generated by default as identity,
    author_name varchar(90) not null,
    primary key (id_author),
    unique (author_name)
);

create table if not exists book
(
    id_book   int         not null generated by default as identity,
    b_name    varchar(90) not null,
    id_author int         not null,
    id_style  int         not null,
    primary key (id_book),
    unique (b_name, id_author, id_style),
    FOREIGN KEY (id_author) REFERENCES author (id_author),
    FOREIGN KEY (id_style) REFERENCES public.style (id_style)
);