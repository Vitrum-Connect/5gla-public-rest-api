create table if not exists stationary_image
(
    id                   int primary key auto_increment,
    version              datetime       not null,
    oid                  varchar(255)   not null,
    camera_id            varchar(255)   not null,
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

alter table image rename column drone_id to camera_id;