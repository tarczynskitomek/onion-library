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

-- readers
create table readers
(
    id varchar not null primary key,
    age integer not null
);
