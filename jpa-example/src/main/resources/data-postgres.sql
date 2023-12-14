insert into registered_user(id, company_information, city, email, name, password, profession, role, state, surname, telephone_number, category, points) values (-1, 'Najbolja kompanija', 'Novi Sad', 'kuzminacn@gmail.com', 'Nina', 'cicika', 'Proizvodjac parfema', 0, 'Srbija', 'Kuzmijanac', '0659443197', 0, 0);

insert into company(id, address, average_grade, description, latitude, longitude, name) values (-1, '4, Mihala Babinke, Novi Sad, Srbija', 4.75, 'Jako dobra', 10.45, 45.67, 'Ninina kompanija');
insert into company(id, address, average_grade, description, latitude, longitude, name) values (-2, '54, Ulica i broj, Beograd, Srbija', 2.5, 'Onako', 11.45, 42.67, 'Neka kompanija');
insert into company(id, address, average_grade, description, latitude, longitude, name) values (-3, '15, Neka adresa, Novi Sad, Srbija', 3, 'Solidno', 10.45, 32.67, 'Naziv kompanije');
insert into company(id, address, average_grade, description, latitude, longitude, name) values (-4, '27, Alekse Santica, Novi Sad, Srbija', 4, 'Tu i tamo', 16.45, 48.67, 'Medical doo');

insert into company_administrator(id, company_information, city, email, name, password, profession, role, state, surname, telephone_number, company_id) values (-1, 'Iz dobre kompanije', 'Novi Sad', 'legenda@gmail.com', 'Vukasin', 'legenda11', 'Profesionalan sef', 1, 'Srbija', 'Dokmanovski', '0659443197', -1);

insert into registered_user(id, company_information, city, email, name, password, profession, role, state, surname, telephone_number, points, category) values (-2, 'Iz dobre kompanije', 'Novi Sad', 'legenda@gmail.com', 'Vukasin', 'legenda11', 'Profesionalan sef', 1, 'Srbija', 'Dokmanovski', '0659443197', 0, 0);

insert into equipment(id, description, grade, name, type, quantity, company_id) values (-1, 'Kvalitetan stalak', 4, 'Stalak za infuziju', 'Stalak', 10, -1);
insert into equipment(id, description, grade, name, type, quantity, company_id) values (-2, 'Ostra igla', 5, 'Igla za vadjenje krvi', 'Igla', 20, -1);
insert into equipment(id, description, grade, name, type, quantity, company_id) values (-3, 'Solidno udoban krevet', 3, 'Krevet za pacijenta', 'Krevet', 30, -1);

insert into appointment(id, duration, pickup_time, administrator_id, company_id, registered_user_id, status, type) values (-1, 3, '2023-12-06T12:30:00', -1, -1, -2, 0, 0);