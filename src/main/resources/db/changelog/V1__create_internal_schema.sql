-- noinspection SqlDeprecateTypeForFile

-- This is a SQL script to create the table for the tenants within the system.
-- The table is created in the public schema of the database, which is defined in the datasource url.
create table if not exists tenant
(
    id           int primary key,
    version      datetime     not null,
    tenant_id    varchar(16)  not null,
    name         varchar(255) not null,
    description  varchar(255) not null,
    access_token varchar(255) not null
);

-- This is a SQL script to create the table for the groups within the system.
-- The table is created in the public schema of the database, which is defined in the datasource url.
create table if not exists group_for_tenant
(
    id                       int primary key,
    version                  datetime     not null,
    oid                      varchar(16)  not null,
    name                     varchar(255) not null,
    description              varchar(255) not null,
    default_group_for_tenant boolean      not null,
    tenant_id                int          not null,
    foreign key (tenant_id) references tenant (id)
);

-- This is a SQL script to create the table for the image data within the system.
-- The table is created in the public schema of the database, which is defined in the datasource url.
create table if not exists image
(
    id                   int primary key,
    version              datetime       not null,
    oid                  varchar(16)    not null,
    drone_id             varchar(255)   not null,
    transaction_id       varchar(255)   not null,
    image_channel        varchar(40)    not null,
    base64_encoded_image clob           not null,
    measured_at          timestamp      not null,
    longitude            numeric(10, 6) not null,
    latitude             numeric(10, 6) not null,
    group_id             int            not null,
    tenant_id            int            not null,
    foreign key (group_id) references group_for_tenant (id),
    foreign key (tenant_id) references tenant (id)
)