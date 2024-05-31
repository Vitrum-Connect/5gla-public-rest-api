-- noinspection SqlDeprecateTypeForFile
alter table third_party_api_configuration
    modify last_run datetime null;
alter table third_party_api_configuration
    modify uuid varchar(255) not null;