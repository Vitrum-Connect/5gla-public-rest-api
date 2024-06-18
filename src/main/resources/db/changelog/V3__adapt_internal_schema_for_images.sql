-- noinspection SqlDeprecateTypeForFile
alter table image
    modify base64_encoded_image longtext not null;