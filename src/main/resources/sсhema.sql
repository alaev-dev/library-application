CREATE TABLE IF NOT EXISTS public.book
(
    id_book   serial,
    b_name    character varying(90) NOT NULL,
    id_author integer               NOT NULL,
    id_style  integer               NOT NULL,
    PRIMARY KEY (id_book),
    UNIQUE (b_name, id_author, id_style)
);

ALTER TABLE public.book
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.style
(
    id_style serial,
    st_name  character varying(90) NOT NULL,
    PRIMARY KEY (id_style),
    UNIQUE (st_name)
);

ALTER TABLE public.style
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.author
(
    id_author   serial,
    author_name character varying(90) NOT NULL,
    PRIMARY KEY (id_author),
    UNIQUE (author_name)
);

ALTER TABLE public.author
    OWNER to postgres;

ALTER TABLE public.book
    ADD FOREIGN KEY (id_author)
        REFERENCES public.author (id_author)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID;

ALTER TABLE public.book
    ADD FOREIGN KEY (id_style)
        REFERENCES public.style (id_style)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID;