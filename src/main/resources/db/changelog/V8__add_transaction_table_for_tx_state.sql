-- noinspection SqlDeprecateTypeForFile
create table if not exists transaction_state
(
    id                   int primary key auto_increment,
    version              datetime       not null,
    oid                  varchar(255)   not null,
    transaction_id       varchar(255)   not null,
    transaction_state    varchar(40)    not null,
    tenant_id            int            not null,
    foreign key (tenant_id) references tenant (id)
);