-- books
create table authors
(
    id varchar not null primary key
);

create table books
(
    id     varchar not null primary key,
    title  varchar not null,
    version integer not null,
    author varchar not null, -- should references authors (id)
    status varchar not null,
    created_at timestamp with time zone not null,
    approved_at timestamp with time zone,
    rejected_at timestamp with time zone,
    archived_at timestamp with time zone
);

-- patrons
create table patrons
(
    id varchar not null primary key,
    name varchar(512) not null,
    type varchar(16) not null,
    affiliation varchar
);

-- holds
create table holds (
    id varchar not null primary key,
    book_id varchar not null references books(id),
    patron_id varchar not null references patrons(id)
);
