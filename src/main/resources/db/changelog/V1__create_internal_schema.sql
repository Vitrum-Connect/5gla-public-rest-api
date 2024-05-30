-- noinspection SqlDeprecateTypeForFile

create table if not exists tenant
(
    id           int primary key auto_increment,
    version      datetime     not null,
    tenant_id    varchar(16)  not null,
    name         varchar(255) not null,
    description  varchar(255) not null,
    access_token varchar(255) not null
);

create table if not exists group_for_tenant
(
    id                       int primary key auto_increment,
    version                  datetime     not null,
    oid                      varchar(255) not null,
    name                     varchar(255) not null,
    description              varchar(255) not null,
    default_group_for_tenant boolean      not null,
    tenant_id                int          not null,
    foreign key (tenant_id) references tenant (id)
);

create table if not exists image
(
    id                   int primary key auto_increment,
    version              datetime       not null,
    oid                  varchar(255)   not null,
    drone_id             varchar(255)   not null,
    transaction_id       varchar(255)   not null,
    image_channel        varchar(40)    not null,
    base64_encoded_image text           not null,
    measured_at          datetime       not null,
    longitude            numeric(10, 6) not null,
    latitude             numeric(10, 6) not null,
    group_id             int            not null,
    tenant_id            int            not null,
    foreign key (group_id) references group_for_tenant (id),
    foreign key (tenant_id) references tenant (id)
);

create table if not exists third_party_api_configuration
(
    id            int primary key auto_increment,
    version       datetime     not null,
    uuid          varchar(16)  not null,
    tenant_id     int          not null,
    manufacturer  varchar(255) not null,
    fiware_prefix varchar(255) not null,
    enabled       boolean      not null,
    url           varchar(255) not null,
    username      varchar(255) not null,
    password      varchar(255) not null,
    api_token     varchar(255) not null,
    last_run      datetime     not null,
    foreign key (tenant_id) references tenant (id)
);

create table if not exists group_sensor_ids_assigned_to_group
(
    group_id                     int  not null,
    sensor_ids_assigned_to_group text not null,
    foreign key (group_id) references group_for_tenant (id)
);
