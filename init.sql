-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 01 jan. 2024 à 11:35
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `smartwateringv2`
--

-- --------------------------------------------------------

--
-- Structure de la table `app_user`
--

CREATE TABLE `app_user` (
                            `id` bigint(20) NOT NULL,
                            `address` varchar(255) DEFAULT NULL,
                            `password` varchar(255) DEFAULT NULL,
                            `phone` varchar(255) DEFAULT NULL,
                            `role` varchar(255) DEFAULT NULL,
                            `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `app_user`
--

INSERT INTO `app_user` (`id`, `address`, `password`, `phone`, `role`, `username`) VALUES
                                                                                      (1, 'Hay riyad', '$2a$10$TEq0LzQoJbpCVLmvFbwOfuaNyzYlfs/1jqG8uDXh2agq9YIStnKd2', '076637736', 'ADMIN', 'admin@admin'),
                                                                                      (15, 'hay andalouss', '$2a$10$D1pyZ8RS9LVOZmjhWwiG0eH.Gx1jKMm30eqb2gYVXHw/MCRq2wLVm', '0666623887', 'USER', 'lachgar');

-- --------------------------------------------------------

--
-- Structure de la table `arrosage`
--

CREATE TABLE `arrosage` (
                            `id` int(11) NOT NULL,
                            `date` datetime(6) DEFAULT NULL,
                            `litres_eau` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `arrosage`
--

INSERT INTO `arrosage` (`id`, `date`, `litres_eau`) VALUES
                                                        (28, '2023-12-31 00:00:00.000000', 988),
                                                        (29, '2023-12-29 00:00:00.000000', 10),
                                                        (33, '2023-12-22 00:00:00.000000', 988);

-- --------------------------------------------------------

--
-- Structure de la table `boitier`
--

CREATE TABLE `boitier` (
                           `id` bigint(20) NOT NULL,
                           `code` varchar(255) DEFAULT NULL,
                           `image` varchar(255) DEFAULT NULL,
                           `ref` varchar(255) DEFAULT NULL,
                           `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `boitier`
--

INSERT INTO `boitier` (`id`, `code`, `image`, `ref`, `type`) VALUES
                                                                 (22, 'BTX7689', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTf2sVgp6cJETDbkHA7kIJ3wGPhO4YyG4UFTA&usqp=CAU', '1', ' XL-ATX'),
                                                                 (23, 'BTX235', 'https://ae01.alicdn.com/kf/S62f4ed4ae0e0488f86a2fa16cdc970f85/GTBL-6-Pcs-Arduino-Pro-Mini-Nano-V3-0-Atmega328p-5V-16M-Microcontroller-Kit-Without-USB.jpg', '2', 'BTX');

-- --------------------------------------------------------

--
-- Structure de la table `capteur`
--

CREATE TABLE `capteur` (
                           `id` bigint(20) NOT NULL,
                           `freq` float NOT NULL,
                           `image` varchar(255) DEFAULT NULL,
                           `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `capteur`
--

INSERT INTO `capteur` (`id`, `freq`, `image`, `type`) VALUES
                                                          (24, 0, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSNKCF3fu4cIAX6vTv3PESbIMudzvqRaMhr7DAFPEvdWQ&s', 'temperature sensor'),
                                                          (25, 0, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSIBzvPwqpnnKiXLZFqobOR_w6YbYXt8DA06BClcqoEA&s', 'ESP8266'),
                                                          (26, 0, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSjW4zHOjp6eRJ1SwDC_h5fNTQLsP2RMGU7vBLrdFLBOQ&s', 'humidity sensor');

-- --------------------------------------------------------

--
-- Structure de la table `connection`
--

CREATE TABLE `connection` (
                              `id` bigint(20) NOT NULL,
                              `branche` varchar(255) DEFAULT NULL,
                              `fonctionnel` bit(1) NOT NULL,
                              `capteur_id` bigint(20) DEFAULT NULL,
                              `boitier_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `connection`
--

INSERT INTO `connection` (`id`, `branche`, `fonctionnel`, `capteur_id`, `boitier_id`) VALUES
                                                                                          (42, 'mmm', b'1', 24, 23),
                                                                                          (43, 'A9', b'1', 24, 23),
                                                                                          (44, 'mm', b'1', 24, 23),
                                                                                          (45, 'ee', b'1', 25, 23),
                                                                                          (46, 'n', b'1', 24, 23),
                                                                                          (47, 'n', b'1', 24, 22),
                                                                                          (48, 'd', b'1', 24, 22),
                                                                                          (49, 'dhytfff', b'1', 24, 23),
                                                                                          (50, 'A25', b'1', 24, 23),
                                                                                          (51, '', b'1', 24, 23),
                                                                                          (52, 'mn', b'1', 24, 22),
                                                                                          (53, 'A9', b'1', 24, 22);

-- --------------------------------------------------------

--
-- Structure de la table `espace_vert`
--

CREATE TABLE `espace_vert` (
                               `id` bigint(20) NOT NULL,
                               `photo` varchar(255) DEFAULT NULL,
                               `libelle` varchar(255) DEFAULT NULL,
                               `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `espace_vert`
--

INSERT INTO `espace_vert` (`id`, `photo`, `libelle`, `user_id`) VALUES
                                                                    (16, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSli26XTmUas1E758hhzcNoPJKXHbikczENFg&usqp=CAU', 'vegetable garden', 1),
                                                                    (21, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvuPPDC5Zj6z17vSv90Va39zGbSvqaMjyl9g&usqp=CAU', 'green space3', 15);

-- --------------------------------------------------------

--
-- Structure de la table `grandeur`
--

CREATE TABLE `grandeur` (
                            `id` int(11) NOT NULL,
                            `date_time` datetime(6) DEFAULT NULL,
                            `humidity` double DEFAULT NULL,
                            `temperature` double DEFAULT NULL,
                            `zone_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

INSERT INTO `grandeur` (`id`, `date_time`, `humidity`, `temperature`, `zone_id`) VALUES
(62, '2023-12-30 02:01:04.000000', 62, 18, 31),
(63, '2023-12-30 02:01:09.000000', 62, 18, 31),
(64, '2023-12-30 02:01:11.000000', 62, 18, 31),
(65, '2023-12-30 02:01:14.000000', 62, 18, 31),
(66, '2023-12-30 02:01:16.000000', 62, 18, 31),
(67, '2023-12-30 02:01:18.000000', 62, 18, 31),
(68, '2023-12-30 02:01:20.000000', 62, 18, 31),
(69, '2023-12-30 02:01:22.000000', 62, 18, 31),
(70, '2023-12-30 02:01:25.000000', 62, 18, 31),
(71, '2023-12-30 02:01:27.000000', 62, 18, 31),
(72, '2023-12-30 02:01:29.000000', 62, 18, 31),
(73, '2023-12-30 02:01:31.000000', 62, 18, 31),
(74, '2023-12-30 02:01:33.000000', 61, 18, 31),
(75, '2023-12-30 02:01:36.000000', 61, 18, 31),
(76, '2023-12-30 02:01:38.000000', 61, 18, 31),
(77, '2023-12-30 02:01:40.000000', 61, 18, 31),
(78, '2023-12-30 02:01:43.000000', 61, 18, 31),
(79, '2023-12-30 02:01:45.000000', 61, 18, 31);

--
-- Structure de la table `grandeurss`
--

CREATE TABLE `grandeurss` (
                              `id` int(200) NOT NULL,
                              `date` date NOT NULL,
                              `temperature` int(20) NOT NULL,
                              `humidity` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `grandeurss`
--

INSERT INTO `grandeurss` (`id`, `date`, `temperature`, `humidity`) VALUES
                                                                       (1, '2023-12-27', 123, 2444),
                                                                       (2, '2023-12-13', 45, 45);

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
    `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
    (54);

-- --------------------------------------------------------

--
-- Structure de la table `installation`
--

CREATE TABLE `installation` (
                                `id` bigint(20) NOT NULL,
                                `date_debut` datetime(6) DEFAULT NULL,
                                `date_fin` datetime(6) DEFAULT NULL,
                                `boitier_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `installation`
--

INSERT INTO `installation` (`id`, `date_debut`, `date_fin`, `boitier_id`) VALUES
                                                                              (34, '2023-12-31 00:00:00.000000', '2023-12-31 21:59:26.000000', 22),
                                                                              (35, '2023-12-21 00:00:00.000000', NULL, 23);

-- --------------------------------------------------------

--
-- Structure de la table `mesure`
--

CREATE TABLE `mesure` (
                          `id` bigint(20) NOT NULL,
                          `date` datetime(6) DEFAULT NULL,
                          `humidite` double NOT NULL,
                          `temperature` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notification`
--

CREATE TABLE `notification` (
                                `id` int(11) NOT NULL,
                                `content` varchar(255) DEFAULT NULL,
                                `readed` bit(1) NOT NULL,
                                `type` varchar(255) DEFAULT NULL,
                                `zone_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Structure de la table `plantage`
--

CREATE TABLE `plantage` (
                            `id` bigint(20) NOT NULL,
                            `date` datetime(6) DEFAULT NULL,
                            `nombre` int(11) NOT NULL,
                            `plante_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `plantage`
--

INSERT INTO `plantage` (`id`, `date`, `nombre`, `plante_id`) VALUES
                                                                 (27, '2023-12-27 00:00:00.000000', 90, 5),
                                                                 (32, '2023-12-21 00:00:00.000000', 12, 3);

-- --------------------------------------------------------

--
-- Structure de la table `plante`
--

CREATE TABLE `plante` (
                          `id` bigint(20) NOT NULL,
                          `image` varchar(255) DEFAULT NULL,
                          `libelle` varchar(255) DEFAULT NULL,
                          `racine` varchar(255) DEFAULT NULL,
                          `type_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `plante`
--

INSERT INTO `plante` (`id`, `image`, `libelle`, `racine`, `type_id`) VALUES
                                                                         (3, 'https://media.ooreka.fr/public/image/plant/265/mainImage-source-10006168.jpg', 'tomato', 'herbaceous', 13),
                                                                         (5, 'https://static.aujardin.info/cache/th/img9/solanum-tuberosum-600x450.webp', 'garlic', 'Amaryllidaceae', 12),
                                                                         (7, 'https://static.cotemaison.fr/medias_11308/w_2048,h_1146,c_crop,x_0,y_79/w_640,h_360,c_fill,g_north/v1485610474/pomme-de-terre-4_5790003.jpg', 'potato', 'Andes', 9);

-- --------------------------------------------------------

--
-- Structure de la table `sol_type`
--

CREATE TABLE `sol_type` (
                            `id` bigint(20) NOT NULL,
                            `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `sol_type`
--

INSERT INTO `sol_type` (`id`, `name`) VALUES
                                          (17, 'limestone'),
                                          (19, 'argillaceous'),
                                          (30, 'SAND');

-- --------------------------------------------------------

--
-- Structure de la table `type_plante`
--

CREATE TABLE `type_plante` (
                               `id` int(11) NOT NULL,
                               `humidite_max` float NOT NULL,
                               `humidite_min` float NOT NULL,
                               `luminosite` float NOT NULL,
                               `name` varchar(255) DEFAULT NULL,
                               `temperature` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `type_plante`
--

INSERT INTO `type_plante` (`id`, `humidite_max`, `humidite_min`, `luminosite`, `name`, `temperature`) VALUES
                                                                                                          (2, 120, 90, 134, 'Solanacées', 40),
                                                                                                          (4, 33, 12, 66, 'légume', 88),
                                                                                                          (6, 90, 67, 79, 'légume', 99),
                                                                                                          (8, 90, 67, 79, 'légume', 99),
                                                                                                          (9, 90, 67, 79, 'vegetable', 99),
                                                                                                          (10, 33, 12, 66, 'vegetable', 88),
                                                                                                          (11, 120, 90, 134, 'Solanacées', 40),
                                                                                                          (12, 33, 12, 66, 'vegetable', 88),
                                                                                                          (13, 120, 90, 134, 'Solanaceae', 40);

-- --------------------------------------------------------

--
-- Structure de la table `zone`
--

CREATE TABLE `zone` (
                        `id` bigint(20) NOT NULL,
                        `photo` longtext DEFAULT NULL,
                        `libelle` varchar(255) DEFAULT NULL,
                        `superficie` float DEFAULT NULL,
                        `type_id` bigint(20) DEFAULT NULL,
                        `espace_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `zone`
--

INSERT INTO `zone` (`id`, `photo`, `libelle`, `superficie`, `type_id`, `espace_id`) VALUES
                                                                                        (18, 'https://thumbs.dreamstime.com/b/zone-de-salet%C3%A9-d-argile-avec-le-fond-vert-en-toscane-13119779.jpg', 'zone1', 190, 17, 16),
                                                                                        (20, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcGJ_B0XnFHyC-2DE2TFSkqXLYLIJ65yKjXZYNJXLhPw&s', 'zone2', 1230, 19, 16),
                                                                                        (31, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcGJ_B0XnFHyC-2DE2TFSkqXLYLIJ65yKjXZYNJXLhPw&s', 'zone4', 6600, 30, 21);

-- --------------------------------------------------------

--
-- Structure de la table `zone_arrosages`
--

CREATE TABLE `zone_arrosages` (
                                  `zone_id` bigint(20) NOT NULL,
                                  `arrosages_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `zone_arrosages`
--

INSERT INTO `zone_arrosages` (`zone_id`, `arrosages_id`) VALUES
                                                             (18, 28),
                                                             (18, 29),
                                                             (31, 33);

-- --------------------------------------------------------

--
-- Structure de la table `zone_installations`
--

CREATE TABLE `zone_installations` (
                                      `zone_id` bigint(20) NOT NULL,
                                      `installations_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `zone_installations`
--

INSERT INTO `zone_installations` (`zone_id`, `installations_id`) VALUES
                                                                     (18, 34),
                                                                     (18, 35);

-- --------------------------------------------------------

--
-- Structure de la table `zone_plantages`
--

CREATE TABLE `zone_plantages` (
                                  `zone_id` bigint(20) NOT NULL,
                                  `plantages_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `zone_plantages`
--

INSERT INTO `zone_plantages` (`zone_id`, `plantages_id`) VALUES
                                                             (18, 27),
                                                             (31, 32);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `app_user`
--
ALTER TABLE `app_user`
    ADD PRIMARY KEY (`id`);

--
-- Index pour la table `arrosage`
--
ALTER TABLE `arrosage`
    ADD PRIMARY KEY (`id`);

--
-- Index pour la table `boitier`
--
ALTER TABLE `boitier`
    ADD PRIMARY KEY (`id`);

--
-- Index pour la table `capteur`
--
ALTER TABLE `capteur`
    ADD PRIMARY KEY (`id`);

--
-- Index pour la table `connection`
--
ALTER TABLE `connection`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FKa4onesxdnpvyfv6n36lf5hnx4` (`capteur_id`),
  ADD KEY `FKbf4j7pnquv36xu9ptv5kos8wl` (`boitier_id`);

--
-- Index pour la table `espace_vert`
--
ALTER TABLE `espace_vert`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FKqfp74ekquw1r6oc2hh5ub2cw2` (`user_id`);

--
-- Index pour la table `grandeur`
--
ALTER TABLE `grandeur`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FKese27t9uhekblpdk92mpxqdwk` (`zone_id`);

--
-- Index pour la table `grandeurss`
--
ALTER TABLE `grandeurss`
    ADD PRIMARY KEY (`id`);

--
-- Index pour la table `installation`
--
ALTER TABLE `installation`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FK6wghoerv9n160ou7ge0stur65` (`boitier_id`);

--
-- Index pour la table `mesure`
--
ALTER TABLE `mesure`
    ADD PRIMARY KEY (`id`);

--
-- Index pour la table `notification`
--
ALTER TABLE `notification`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FKlv57owiojjhsmu5u6m4tfqa2j` (`zone_id`);

--
-- Index pour la table `plantage`
--
ALTER TABLE `plantage`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FKodihn7crutkhm0m21byfoipks` (`plante_id`);

--
-- Index pour la table `plante`
--
ALTER TABLE `plante`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FKs2mqamm7bfr2ruuvwmmfup440` (`type_id`);

--
-- Index pour la table `sol_type`
--
ALTER TABLE `sol_type`
    ADD PRIMARY KEY (`id`);

--
-- Index pour la table `type_plante`
--
ALTER TABLE `type_plante`
    ADD PRIMARY KEY (`id`);

--
-- Index pour la table `zone`
--
ALTER TABLE `zone`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FK5e2wrlyom20d8rkfoedeqxbw9` (`espace_id`),
  ADD KEY `FKpupiqgygqg96l8cm60vsgeujo` (`type_id`);

--
-- Index pour la table `zone_arrosages`
--
ALTER TABLE `zone_arrosages`
    ADD UNIQUE KEY `UK_iv2sgxchjd59e6ggs6vn21nv2` (`arrosages_id`),
    ADD KEY `FKt6r2gqhyq0igm3as5r5qs2r07` (`zone_id`);

--
-- Index pour la table `zone_installations`
--
ALTER TABLE `zone_installations`
    ADD UNIQUE KEY `UK_jvx4q3fx5y3an88sivhircg0q` (`installations_id`),
    ADD KEY `FK2i7f9mu25im8csn7vaqx79yfx` (`zone_id`);

--
-- Index pour la table `zone_plantages`
--
ALTER TABLE `zone_plantages`
    ADD UNIQUE KEY `UK_9sylon15eiutksjhhr79fsxcu` (`plantages_id`),
    ADD KEY `FKq8518jdkd035qjn5lrfpyw25p` (`zone_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `grandeurss`
--
ALTER TABLE `grandeurss`
    MODIFY `id` int(200) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `mesure`
--
ALTER TABLE `mesure`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `connection`
--
ALTER TABLE `connection`
    ADD CONSTRAINT `FKa4onesxdnpvyfv6n36lf5hnx4` FOREIGN KEY (`capteur_id`) REFERENCES `capteur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FKbf4j7pnquv36xu9ptv5kos8wl` FOREIGN KEY (`boitier_id`) REFERENCES `boitier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `espace_vert`
--
ALTER TABLE `espace_vert`
    ADD CONSTRAINT `FKqfp74ekquw1r6oc2hh5ub2cw2` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `grandeur`
--
ALTER TABLE `grandeur`
    ADD CONSTRAINT `FKese27t9uhekblpdk92mpxqdwk` FOREIGN KEY (`zone_id`) REFERENCES `zone` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `installation`
--
ALTER TABLE `installation`
    ADD CONSTRAINT `FK6wghoerv9n160ou7ge0stur65` FOREIGN KEY (`boitier_id`) REFERENCES `boitier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `notification`
--
ALTER TABLE `notification`
    ADD CONSTRAINT `FKlv57owiojjhsmu5u6m4tfqa2j` FOREIGN KEY (`zone_id`) REFERENCES `zone` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `plantage`
--
ALTER TABLE `plantage`
    ADD CONSTRAINT `FKodihn7crutkhm0m21byfoipks` FOREIGN KEY (`plante_id`) REFERENCES `plante` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `plante`
--
ALTER TABLE `plante`
    ADD CONSTRAINT `FKs2mqamm7bfr2ruuvwmmfup440` FOREIGN KEY (`type_id`) REFERENCES `type_plante` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `zone`
--
ALTER TABLE `zone`
    ADD CONSTRAINT `FK5e2wrlyom20d8rkfoedeqxbw9` FOREIGN KEY (`espace_id`) REFERENCES `espace_vert` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FKpupiqgygqg96l8cm60vsgeujo` FOREIGN KEY (`type_id`) REFERENCES `sol_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `zone_arrosages`
--
ALTER TABLE `zone_arrosages`
    ADD CONSTRAINT `FK9g0cv20wddlojkvo80hab11q` FOREIGN KEY (`arrosages_id`) REFERENCES `arrosage` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FKt6r2gqhyq0igm3as5r5qs2r07` FOREIGN KEY (`zone_id`) REFERENCES `zone` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `zone_installations`
--
ALTER TABLE `zone_installations`
    ADD CONSTRAINT `FK2i7f9mu25im8csn7vaqx79yfx` FOREIGN KEY (`zone_id`) REFERENCES `zone` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK63vwb25aj5axgkfwsprv8oun9` FOREIGN KEY (`installations_id`) REFERENCES `installation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `zone_plantages`
--
ALTER TABLE `zone_plantages`
    ADD CONSTRAINT `FKf26wg3ynanlbfbmt12occij3t` FOREIGN KEY (`plantages_id`) REFERENCES `plantage` (`id`),
  ADD CONSTRAINT `FKq8518jdkd035qjn5lrfpyw25p` FOREIGN KEY (`zone_id`) REFERENCES `zone` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
