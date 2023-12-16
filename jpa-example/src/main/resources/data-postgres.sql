
INSERT INTO public.role(id, name) VALUES (1, 'ROLE_REGISTERED_USER');
INSERT INTO public.role(id, name)VALUES (2, 'ROLE_COMPANY_ADMINISTRATOR');
INSERT INTO public.role(id, name)VALUES (3, 'ROLE_SYSTEM_ADMINISTRATOR');


-- sifra je cicika
--insert into registered_user(id, company_information, city, username,enabled, last_password_reset_date, name, password, profession, state, surname, telephone_number, category, points,role_id) values (1, 'Najbolja kompanija', 'Novi Sad', 'kuzminacn@gmail.com',TRUE,'2023-09-12', 'Nina', '$2a$10$A6O.ZfJmoUmfj0oL70ud6O6ztHfiRprKB8bqbgJeO5sZdYArkmA4e', 'Proizvodjac parfema', 'Srbija', 'Kuzmijanac', '0659443197', 0, 0,1);
INSERT INTO public.users(id, company_information, city, enabled, last_password_reset_date, name, password, profession, state, surname, telephone_number, username, role_id) VALUES (-1, 'Najbolja kompanija', 'Novi Sad',TRUE,'2023-09-12', 'Nina', '$2a$10$09DhnmM3jY0I6YggLsdbZObeEG2/vF7o/PwPaUwV84OVMCFhOB.0m', 'Proizvodjac parfema', 'Srbija', 'Kuzmijanac', '0659443197','kuzminacn@gmail.com',1);
INSERT INTO public.registered_user(category, points, id) VALUES (0, 0, -1);

-- legenda11
INSERT INTO public.users(id, company_information, city, enabled, last_password_reset_date, name, password, profession, state, surname, telephone_number, username, role_id) VALUES (-3, 'Iz dobre kompanije', 'Novi Sad', TRUE,'2023-09-12', 'Vukasin', '$2a$10$nWbxkfSzQQgBLU0t5OcbTOUV6MDXSeJoaRMfBld.NrpC1a8VDRNzS', 'Profesionalan sef',  'Srbija', 'Dokmanovski', '0659443197','legenda+4@gmail.com',1);

INSERT INTO public.registered_user(category, points, id) VALUES (0, 0, -3);

insert into company(id, address, average_grade, description, latitude, longitude, name) values (-1, '4, Mihala Babinke, Novi Sad, Srbija', 4.75, 'Jako dobra', 10.45, 45.67, 'Ninina kompanija');
insert into company(id, address, average_grade, description, latitude, longitude, name) values (-2, '54, Ulica i broj, Beograd, Srbija', 2.5, 'Onako', 11.45, 42.67, 'Neka kompanija');
insert into company(id, address, average_grade, description, latitude, longitude, name) values (-3, '15, Neka adresa, Novi Sad, Srbija', 3, 'Solidno', 10.45, 32.67, 'Naziv kompanije');
insert into company(id, address, average_grade, description, latitude, longitude, name) values (-4, '27, Alekse Santica, Novi Sad, Srbija', 4, 'Tu i tamo', 16.45, 48.67, 'Medical doo');

-- legenda11
INSERT INTO public.users(id, company_information, city, enabled, last_password_reset_date, name, password, profession, state, surname, telephone_number, username, role_id) VALUES (-2, 'Iz dobre kompanije', 'Novi Sad',TRUE,'2023-09-12', 'Vukasin', '$2a$10$nWbxkfSzQQgBLU0t5OcbTOUV6MDXSeJoaRMfBld.NrpC1a8VDRNzS', 'Profesionalan sef',  'Srbija', 'Dokmanovski', '0659443197', 'legenda@gmail.com',2);
INSERT INTO public.company_administrator(id, company_id) VALUES (-2, -1);

INSERT INTO public.users(id, company_information, city, enabled, last_password_reset_date, name, password, profession, state, surname, telephone_number, username, role_id) VALUES (-4, 'Iz dobre kompanije', 'Novi Sad',TRUE,'2023-09-12', 'Kukisa', '$2a$10$nWbxkfSzQQgBLU0t5OcbTOUV6MDXSeJoaRMfBld.NrpC1a8VDRNzS', 'Profesionalan sef',  'Srbija', 'Vukisa', '0659443197', 'legendaKOPIJA@gmail.com',2);
INSERT INTO public.company_administrator(id, company_id) VALUES (-4, -1);

insert into equipment(id, description, grade, name, type, quantity, company_id) values (-1, 'Kvalitetan stalak', 4, 'Stalak za infuziju', 'Stalak', 10, -1);
insert into equipment(id, description, grade, name, type, quantity, company_id) values (-2, 'Ostra igla', 5, 'Igla za vadjenje krvi', 'Igla', 20, -1);
insert into equipment(id, description, grade, name, type, quantity, company_id) values (-3, 'Solidno udoban krevet', 3, 'Krevet za pacijenta', 'Krevet', 30, -1);


insert into appointment(id, duration, pickup_time, administrator_id, company_id, registered_user_id, status, type) values (-1, 30, '2023-12-06T12:30:00', -2, -1, -3, 0, 0);
insert into appointment(id, duration, pickup_time, administrator_id, company_id, registered_user_id, status, type) values (-2, 30, '2023-12-06T14:30:00', -2, -1, null, 0, 0);

