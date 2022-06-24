-- create contacts
    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'Georges', 'Chabab', 'Supervising Consultant', 'georges.chabab@gicome.com', 0, now(), 0, now() on conflict do nothing;

    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'Nabil', 'Nacouzi', 'Contractor', 'nabilnacouzi@hotmail.com', 0, now(), 0, now() on conflict do nothing;

	insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
	select 'Toni', 'Makhlouf', 'Supervising Consultant', 'toni.makhlouf@gicome.com', 0, now(), 0, now() on conflict do nothing;

	insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'Elie', 'Chehade', 'Contractor', 'eliechehade@yahoo.com', 0, now(), 0, now() on conflict do nothing;

	insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
	select 'Ali', 'Elmir', 'Supervising Consultant', 'ali.elmir@aceintl.net', 0, now(), 0, now() on conflict do nothing;

	insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'Riad', 'Hammoud', 'Contractor', 'riadhammoud@hotmail.com', 0, now(), 0, now() on conflict do nothing;

	insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
	select 'Jean', 'Samaha', 'Supervising Consultant', 'jeansamaha@hotmail.com', 0, now(), 0, now()  on conflict do nothing;

    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'Amine', 'Mansour', 'Contractor', 'amine.mansour@homangroup.com', 0, now(), 0, now() on conflict do nothing;

    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'R', 'N', 'Contractor', 'rn@makhloufgroup.com', 0, now(), 0, now()  on conflict do nothing;

    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
	select 'Saleh', 'Sabbidin', 'Supervising Consultant', 'Saleh.Sabbidin@dar.com', 0, now(), 0, now() on conflict do nothing;

	insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'fadoha', 'fadoha', 'Contractor', 'fadoha13@gmail.com', 0, now(), 0, now() on conflict do nothing;

    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'Gaelle', 'Samaha', 'WorldBank', 'gsamaha@worldbank.org', 0, now(), 0, now() on conflict do nothing;

    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'Mira', 'Morad', 'WorldBank', 'mmorad@worldbank.org', 0, now(), 0, now() on conflict do nothing;

    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'Grm', 'Rep', 'CDR', 'grm.rep@cdr.gov.lb', 0, now(), 0, now() on conflict do nothing;

    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'Marcel', 'Abou Rida', 'Project Manager', 'marcel@sirenassociates.com', 0, now(), 0, now() on conflict do nothing;

    insert into contact(first_name, last_name, role, email, update_user_id, update_timestamp, create_user_id, create_timestamp)
    select 'David', 'Barrs', 'Grunt', 'david@sirenassociates.com', 0, now(), 0, now() on conflict do nothing;


-- create contact_kadaas
--Kesrwane
	insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
	select id, 'kesr', 0, now() from contact where email = 'georges.chabab@gicome.com' on conflict do nothing;
	--
	insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'kesr', 0, now() from contact where email = 'nabilnacouzi@hotmail.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'kesr', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'kesr', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'kesr', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'kesr', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'kesr', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--El Meten
	insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
	select id, 'mete', 0, now() from contact where email = 'georges.chabab@gicome.com' on conflict do nothing;
	--
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'mete', 0, now() from contact where email = 'nabilnacouzi@hotmail.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'mete', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'mete', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'mete', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'mete', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'mete', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Jbeil
	insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
	select id, 'jbei', 0, now() from contact where email = 'toni.makhlouf@gicome.com' on conflict do nothing;
	--
	insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'jbei', 0, now() from contact where email = 'eliechehade@yahoo.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'jbei', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'jbei', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'jbei', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'jbei', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'jbei', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--El Nabatieh
	insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
	select id, 'naba', 0, now() from contact where email = 'ali.elmir@aceintl.net' on conflict do nothing;
	--
	insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'naba', 0, now() from contact where email = 'riadhammoud@hotmail.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'naba', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'naba', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'naba', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'naba', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'naba', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Marjaayoun
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
	select id, 'marj', 0, now() from contact where email = 'ali.elmir@aceintl.net' on conflict do nothing;
	--
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'marj', 0, now() from contact where email = 'riadhammoud@hotmail.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'marj', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'marj', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'marj', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'marj', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'marj', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Akkar
	insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
	select id, 'akka', 0, now() from contact where email = 'jeansamaha@hotmail.com' on conflict do nothing;
	--
	insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'akka', 0, now() from contact where email = 'amine.mansour@homangroup.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'akka', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'akka', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'akka', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'akka', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'akka', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--El Minieh-Dennie
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'mini', 0, now() from contact where email = 'jeansamaha@hotmail.com' on conflict do nothing;
    --
	insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'mini', 0, now() from contact where email = 'rn@makhloufgroup.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'mini', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'mini', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'mini', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'mini', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'mini', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Zgharta
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'zgha', 0, now() from contact where email = 'jeansamaha@hotmail.com' on conflict do nothing;
    --
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'zgha', 0, now() from contact where email = 'rn@makhloufgroup.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'zgha', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'zgha', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'zgha', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'zgha', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'zgha', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--El Batroun
	insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
	select id, 'batr', 0, now() from contact where email = 'Saleh.Sabbidin@dar.com' on conflict do nothing;
	--
	insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'batr', 0, now() from contact where email = 'fadoha13@gmail.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'batr', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'batr', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'batr', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'batr', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'batr', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Bcharre
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'bcha', 0, now() from contact where email = 'Saleh.Sabbidin@dar.com' on conflict do nothing;
    --
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'bcha', 0, now() from contact where email = 'fadoha13@gmail.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'bcha', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'bcha', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'bcha', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'bcha', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'bcha', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Aley
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'aley', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'aley', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'aley', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'aley', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'aley', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Chouf
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'chou', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'chou', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'chou', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'chou', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'chou', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Baabda
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'baab', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'baab', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'baab', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'baab', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'baab', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Zahle
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'zahl', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'zahl', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'zahl', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'zahl', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'zahl', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--West Bekaa
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'west', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'west', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'west', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'west', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'west', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Rachaya
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'rach', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'rach', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'rach', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'rach', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'rach', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Hasbaya
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'hasb', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'hasb', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'hasb', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'hasb', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'hasb', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Sour
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'sour', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'sour', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'sour', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'sour', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'sour', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Bent Jbail
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'bent', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'bent', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'bent', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'bent', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'bent', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Jezzine
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'jezz', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'jezz', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'jezz', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'jezz', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'jezz', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Saida
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'said', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'said', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'said', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'said', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'said', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Koura
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'kour', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'kour', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'kour', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'kour', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'kour', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Tripoli
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'trip', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'trip', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'trip', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'trip', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'trip', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Baalbek
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'baal', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'baal', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'baal', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'baal', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'baal', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;

--Hermel
    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'herm', 0, now() from contact where email = 'gsamaha@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'herm', 0, now() from contact where email = 'mmorad@worldbank.org' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'herm', 0, now() from contact where email = 'grm.rep@cdr.gov.lb' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'herm', 0, now() from contact where email = 'marcel@sirenassociates.com' on conflict do nothing;

    insert into contact_kadaa(contact_id, kadaa_key, create_user_id, create_timestamp)
    select id, 'herm', 0, now() from contact where email = 'david@sirenassociates.com' on conflict do nothing;




