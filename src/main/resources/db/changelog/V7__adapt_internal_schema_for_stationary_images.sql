-- noinspection SqlDeprecateTypeForFile
alter table stationary_image
    modify base64_encoded_image longtext not null;