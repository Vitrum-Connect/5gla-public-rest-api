-- noinspection SqlDeprecateTypeForFile
create table if not exists registered_device
(
    id          int primary key auto_increment,
    version     datetime       not null,
    oid         varchar(255)   not null,
    name        varchar(255)   not null,
    description varchar(255)   not null,
    longitude   numeric(10, 6) not null,
    latitude    numeric(10, 6) not null,
    group_id    int            not null,
    tenant_id   int            not null,
    foreign key (group_id) references group_for_tenant (id),
    foreign key (tenant_id) references tenant (id)
);