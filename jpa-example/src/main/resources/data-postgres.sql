
INSERT INTO public.role(id, name)
VALUES (1, 'ROLE_REGISTERED_USER');
INSERT INTO public.role(id, name)
VALUES (2, 'ROLE_COMPANY_ADMINISTRATOR');
INSERT INTO public.role(id, name)
VALUES (3, 'ROLE_SYSTEM_ADMINISTRATOR');

-- sifra je cicika
--insert into registered_user(id, company_information, city, username,enabled, last_password_reset_date, name, password, profession, state, surname, telephone_number, category, points,role_id) values (1, 'Najbolja kompanija', 'Novi Sad', 'kuzminacn@gmail.com',TRUE,'2023-09-12', 'Nina', '$2a$10$A6O.ZfJmoUmfj0oL70ud6O6ztHfiRprKB8bqbgJeO5sZdYArkmA4e', 'Proizvodjac parfema', 'Srbija', 'Kuzmijanac', '0659443197', 0, 0,1);
INSERT INTO public.users(id, company_information, city, enabled, last_password_reset_date, name, password, profession, state, surname, telephone_number, username, role_id)
VALUES (-1, 'Najbolja kompanija', 'Novi Sad',TRUE,'2023-09-12', 'Nina', '$2a$10$09DhnmM3jY0I6YggLsdbZObeEG2/vF7o/PwPaUwV84OVMCFhOB.0m', 'Proizvodjac parfema', 'Srbija', 'Kuzmijanac', '0659443197','kuzminacn@gmail.com',1);
INSERT INTO public.registered_user(category, points, id)
VALUES (0, 0, -1);

-- legenda11
INSERT INTO public.users(id, company_information, city, enabled, last_password_reset_date, name, password, profession, state, surname, telephone_number, username, role_id)
VALUES (-3, 'Iz dobre kompanije', 'Novi Sad', TRUE,'2023-09-12', 'Vukasin', '$2a$10$nWbxkfSzQQgBLU0t5OcbTOUV6MDXSeJoaRMfBld.NrpC1a8VDRNzS', 'Profesionalan sef',  'Srbija', 'Dokmanovski', '0659443197','vule.dok@gmail.com',1);

INSERT INTO public.registered_user(category, points, id)
VALUES (0, 0, -3);

INSERT INTO company(id, address, average_grade, description, latitude, longitude, name, workingHoursStart, workingHoursEnd)
VALUES (-1, '4, Mihala Babinke, Novi Sad, Srbija', 4.75, 'Jako dobra', 45.270335, 19.807542, 'Ninina kompanija', '08:00:00', '21:00:00');
INSERT INTO company(id, address, average_grade, description, latitude, longitude, name, workingHoursStart, workingHoursEnd)
VALUES (-2, '54, Ulica i broj, Beograd, Srbija', 2.5, 'Onako', 11.45, 19.844811, 'Neka kompanija', '08:00:00', '21:00:00');
INSERT INTO company(id, address, average_grade, description, latitude, longitude, name, workingHoursStart, workingHoursEnd)
VALUES (-3, '2, Narodnog fronta 2, Novi Sad, Srbija', 3, 'Solidno', 45.242465, 19.851342, 'Naziv kompanije', '08:00:00', '21:00:00');
INSERT INTO company(id, address, average_grade, description, latitude, longitude, name, workingHoursStart, workingHoursEnd)
VALUES (-4, '27, Alekse Santica, Novi Sad, Srbija', 4, 'Tu i tamo', 45.243548, 19.835220, 'Medical doo', '08:00:00', '21:00:00');

-- legenda11
INSERT INTO public.users(id, company_information, city, enabled, last_password_reset_date, name, password, profession, state, surname, telephone_number, username, role_id)
VALUES (-2, 'Iz dobre kompanije', 'Novi Sad', TRUE,'2023-09-12', 'Vukasin', '$2a$10$nWbxkfSzQQgBLU0t5OcbTOUV6MDXSeJoaRMfBld.NrpC1a8VDRNzS', 'Profesionalan sef',  'Srbija', 'Dokmanovski', '0659443197', 'legenda@gmail.com', 2);
INSERT INTO public.company_administrator(id, company_id, verified)
VALUES (-2, -1, TRUE);

INSERT INTO public.users(id, company_information, city, enabled, last_password_reset_date, name, password, profession, state, surname, telephone_number, username, role_id)
VALUES (-4, 'Iz dobre kompanije', 'Novi Sad',TRUE,'2023-09-12', 'Kukisa', '$2a$10$nWbxkfSzQQgBLU0t5OcbTOUV6MDXSeJoaRMfBld.NrpC1a8VDRNzS', 'Profesionalan sef',  'Srbija', 'Vukisa', '0659443197', 'legendaKOPIJA@gmail.com', 2);
INSERT INTO public.company_administrator(id, company_id, verified)
VALUES (-4, -1, FALSE);

INSERT INTO equipment(id, description, grade, name, type, quantity, company_id,available_quantity, version)
VALUES (-1, 'Kvalitetan stalak', 4, 'Stalak za infuziju', 'Stalak', 10, -1,5, 0);
INSERT INTO equipment(id, description, grade, name, type, quantity, company_id,available_quantity, version)
VALUES (-2, 'Ostra igla', 5, 'Igla za vadjenje krvi', 'Igla', 20, -1,16, 0);
INSERT INTO equipment(id, description, grade, name, type, quantity, company_id, available_quantity, version)
VALUES (-3, 'Solidno udoban krevet', 3, 'Krevet za pacijenta', 'Krevet', 30, -1, 30, 0);

INSERT INTO appointment(id, duration, pickup_time, administrator_id, company_id, registered_user_id, status, type, version)
VALUES (-1, 30, '2023-12-31T12:30:00', -2, -1, -3, 0, 0, 0);
INSERT INTO appointment(id, duration, pickup_time, administrator_id, company_id, registered_user_id, status, type, version)
VALUES (-2, 30, '2023-12-31T14:30:00', -2, -1, null, 0, 0,0);
INSERT INTO appointment(id, duration, pickup_time, administrator_id, company_id, registered_user_id, status, type, version)
VALUES (-3, 30, '2023-12-31T17:30:00', -2, -1, -3, 0, 0,0);
INSERT INTO appointment(id, duration, pickup_time, administrator_id, company_id, registered_user_id, status, type, version)
VALUES (-4, 30, '2024-12-31T17:30:00', -2, -1, null, 0, 0, 0);

INSERT INTO public.equipment_quantity(id, equipment_id, quantity, appointment_id, version)
VALUES (-1, -1, 2, -1, 0);
INSERT INTO public.equipment_quantity(id, equipment_id, quantity, appointment_id, version)
VALUES (-2, -2, 2, -1, 0);
INSERT INTO public.equipment_quantity(id, equipment_id, quantity, appointment_id, version)
VALUES (-3, -1, 3, -3, 0);
INSERT INTO public.equipment_quantity(id, equipment_id, quantity, appointment_id, version)
VALUES (-4, -2, 2, -3, 0);

INSERT INTO public.qrcode(id, content, date, appointment_id, registered_user_id)
VALUES (-1, 'Appointment Details:
Date and time: 2023-12-31 12:30:00
Duration: 30
Company: Ninina kompanija
Company administrator that will give you the equipment: Kukisa
Equipment:
Name: Stalak za infuziju
 Quantity: 2
Name: Igla za vadjenje krvi
 Quantity: 2'

       , '2023-12-29T20:09:03.325', -1, -3);
INSERT INTO public.qrcode(id, content, date, appointment_id, registered_user_id)
VALUES (-2, 'Appointment Details:
Date and time: 2023-12-31 17:30:00
Duration: 30
Company: Ninina kompanija
Company administrator that will give you the equipment: Kukisa
Equipment:
Name: Stalak za infuziju
 Quantity: 3
Name: Igla za vadjenje krvi
 Quantity: 2'

       , '2023-12-29T20:09:03.325', -3, -3);
