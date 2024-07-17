insert into files(name, path) values('За_гранью_(2017).jpg', '/Users/kemochka/Desktop/projects/job4j_cinema/file/800px-За_гранью_(2017).jpg');
insert into files(name, path) values('Воображаемые друзья.jpeg', '/Users/kemochka/Desktop/projects/job4j_cinema/file/Воображаемые друзья.jpeg');
insert into files(name, path) values('Геошторм_2017.jpg', '/Users/kemochka/Desktop/projects/job4j_cinema/file/Геошторм_2017.jpg');
insert into files(name, path) values('Тихое место.jpg', '/Users/kemochka/Desktop/projects/job4j_cinema/file/Тихое место.jpg');

insert into genres(name) values('Action');
insert into genres(name) values('Horror');
insert into genres(name) values('Comedy');
insert into genres(name) values('Fantasy');
insert into genres(name) values('Detective');
insert into genres(name) values('Melodrama');

insert into films(name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) values('За гранью реальности',
'Майкл, талантливый аферист, собирает команду людей с паранормальными способностями,
чтобы ограбить казино. Однако все идет не по плану, и герои оказываются в смертельной опасности.', 2018, 4, 12, 107, 1);
insert into films(name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) values('Воображаемые друзья',
'Беа заводит знакомства с воображаемыми друзьями, которых бросили их хозяева.', 2024, 3, 0, 104, 2);
insert into films(name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) values('Геошторм',
'После серии стихийных бедствий лидеры мировых держав объединили усилия,
чтобы создать разветвленную сеть орбитальных спутников по контролю климата для предотвращения природных катаклизмов.', 2017, 1, 18, 109, 3);
insert into films(name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) values('Тихое место', 'Девушка Сэм (Самира), неизлечимо больная раком, вместе с другими пациентами хосписа приезжает на Манхэттен на кукольный спектакль.
После спектакля она надеется купить в городе пиццу — может быть, последний раз в жизни.', 2024, 2, 18, 100, 4);

insert into halls(name, row_count, place_count, description) values ('Small', 10, 20, 'Небольшое количество посадочных мест.');
insert into halls(name, row_count, place_count, description) values ('Middle', 10, 30, 'Достаточно мест, чтобы придти компанией.');
insert into halls(name, row_count, place_count, description) values ('Big', 10, 40, 'Мест хватит всем.');
insert into halls(name, row_count, place_count, description) values ('VIP', 5, 15, 'Установлены диваны и работает бар, чтобы просмотр фильма был комфортным.');

insert into film_sessions(film_id, halls_id, start_time, end_time, price) values (1, 1, '2024-07-18 11:30:00', '2024-07-18 13:30:00', 350);
insert into film_sessions(film_id, halls_id, start_time, end_time, price) values (2, 2, '2024-07-18 12:30:00', '2024-07-18 14:30:00', 300);
insert into film_sessions(film_id, halls_id, start_time, end_time, price) values (3, 3, '2024-07-18 13:30:00', '2024-07-18 15:30:00', 350);
insert into film_sessions(film_id, halls_id, start_time, end_time, price) values (4, 4, '2024-07-18 14:30:00', '2024-07-18 16:30:00', 500);

