
-- create contacts
    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'David', 'Barrs', 'Developer', 'davidbarrs@yahoo.com', 0, now(), 0, now() on conflict do nothing;

    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'Ghinwa', 'El Hasbani', 'Developer', 'ghinwa@sirenassociates.com', 0, now(), 0, now() on conflict do nothing;

    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'Karim', 'Daher', 'Developer', 'karim.d@sirenassociates.com', 0, now(), 0, now() on conflict do nothing;

    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'Wael', 'Komaty', 'QA', 'wael.komaty@gotocme.com', 0, now(), 0, now() on conflict do nothing;

    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'Rouba', 'Maamary', 'Developer', 'rouba@sirenassociates.com', 0, now(), 0, now() on conflict do nothing;

-- create contact_kadaas

    -- Temporarily removed contacts from the email notification while load tests are conducted.

    truncate table contact_kadaa;

--    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
--    select id, 'kesr', 0, now() from contact where email = 'davidbarrs@yahoo.com' on conflict do nothing;

--    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
--    select id, 'kesr', 0, now() from contact where email = 'ghinwa@sirenassociates.com' on conflict do nothing;

--    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
--    select id, 'kesr', 0, now() from contact where email = 'karim.d@sirenassociates.com' on conflict do nothing;

--    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
--    select id, 'kesr', 0, now() from contact where email = 'wael@sirenassociates.com' on conflict do nothing;

--    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
--    select id, 'kesr', 0, now() from contact where email = 'rouba@sirenassociates.com' on conflict do nothing;