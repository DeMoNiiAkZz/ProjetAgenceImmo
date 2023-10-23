-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 23 oct. 2023 à 13:34
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
-- Base de données : `agentimmo`
--

-- --------------------------------------------------------

--
-- Structure de la table `commercial`
--

CREATE TABLE `commercial` (
  `id_commercial` int(11) NOT NULL,
  `login_commercial` varchar(100) NOT NULL,
  `nom_commercial` varchar(200) NOT NULL,
  `prenom_commercial` varchar(200) NOT NULL,
  `salt` binary(16) NOT NULL,
  `hashed_password` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `commercial`
--

INSERT INTO `commercial` (`id_commercial`, `login_commercial`, `nom_commercial`, `prenom_commercial`, `salt`, `hashed_password`) VALUES
(7, 'Enzo', 'Cailac', 'Enzo', 0xac1ea7428fee50f8cb6c9186e2e16d1c, '56fd5da4971c598fe75cc355d3b4f8a70dbc89593edaead4ecfacf26a78ea9f7'),
(8, 'Alpha', 'Bah', 'Alpha', 0x8d5deb59be94f96a937472bc1420c65f, '38e5b5eb1f34b617c15ce9ea9c7a84040dd02b79b18111e6af2ba4e3b79f2311');

-- --------------------------------------------------------

--
-- Structure de la table `equipement`
--

CREATE TABLE `equipement` (
  `id_equipement` int(11) NOT NULL,
  `nom_equipement` varchar(100) NOT NULL,
  `id_piece` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `equipement`
--

INSERT INTO `equipement` (`id_equipement`, `nom_equipement`, `id_piece`) VALUES
(129, 'Lit', 111),
(130, 'Armoire', 111),
(131, 'Table de chevet', 111),
(132, 'Lampadaire', 111),
(133, 'Réfrigérateur', 112),
(134, 'Cuisinière', 112),
(135, 'Évier', 112),
(136, 'Table à manger', 112),
(137, 'Toilettes', 113),
(138, 'Lavabo', 113),
(139, 'Baignoire', 113),
(140, 'Miroir', 113),
(141, 'Canapé', 114),
(142, 'Table basse', 114),
(143, 'Télévision', 114),
(144, 'Étagère', 114),
(145, 'Lit', 115),
(146, 'Armoire', 115),
(147, 'Table de chevet', 115),
(148, 'Lampadaire', 115),
(149, 'Réfrigérateur', 116),
(150, 'Cuisinière', 116),
(151, 'Évier', 116),
(152, 'Table à manger', 116),
(153, 'Toilettes', 117),
(154, 'Lavabo', 117),
(155, 'Baignoire', 117),
(156, 'Miroir', 117),
(157, 'Canapé', 118),
(158, 'Table basse', 118),
(159, 'Télévision', 118),
(160, 'Étagère', 118),
(161, 'Lit', 119),
(162, 'Armoire', 119),
(163, 'Table de chevet', 119),
(164, 'Lampadaire', 119),
(165, 'Réfrigérateur', 120),
(166, 'Cuisinière', 120),
(167, 'Évier', 120),
(168, 'Table à manger', 120),
(169, 'Toilettes', 121),
(170, 'Lavabo', 121),
(171, 'Baignoire', 121),
(172, 'Miroir', 121),
(173, 'Canapé', 122),
(174, 'Table basse', 122),
(175, 'Télévision', 122),
(176, 'Étagère', 122),
(177, 'Lit', 123),
(178, 'Armoire', 123),
(179, 'Table de chevet', 123),
(180, 'Lampadaire', 123),
(181, 'Réfrigérateur', 124),
(182, 'Cuisinière', 124),
(183, 'Évier', 124),
(184, 'Table à manger', 124),
(185, 'Toilettes', 125),
(186, 'Lavabo', 125),
(187, 'Baignoire', 125),
(188, 'Miroir', 125),
(189, 'Canapé', 126),
(190, 'Table basse', 126),
(191, 'Télévision', 126),
(192, 'Étagère', 126);

-- --------------------------------------------------------

--
-- Structure de la table `logements`
--

CREATE TABLE `logements` (
  `id_logement` int(11) NOT NULL,
  `nom_logement` varchar(100) NOT NULL,
  `nb_pieces` int(11) NOT NULL,
  `rue_logement` varchar(100) NOT NULL,
  `cp_logement` int(11) NOT NULL,
  `ville_logement` varchar(200) NOT NULL,
  `id_commercial` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `logements`
--

INSERT INTO `logements` (`id_logement`, `nom_logement`, `nb_pieces`, `rue_logement`, `cp_logement`, `ville_logement`, `id_commercial`) VALUES
(16, 'Maison Verte', 7, '80 rue de la Colère', 20000, 'Ajaccio ', 7),
(17, 'Appartement Vert', 3, '456 Rue de la Paix', 69001, 'Lyon', 8),
(18, 'Studio Rouge', 1, '789 Rue de la Joie', 13001, 'Marseille', 7),
(19, 'Maison Bleue', 9, '80 rue du Bonheur', 33001, 'Bordeaux', 8);

-- --------------------------------------------------------

--
-- Structure de la table `photos`
--

CREATE TABLE `photos` (
  `id_photo` int(11) NOT NULL,
  `chemin_photo` varchar(200) NOT NULL,
  `id_equipement` int(11) DEFAULT NULL,
  `id_piece` int(11) DEFAULT NULL,
  `id_logement` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `photos`
--

INSERT INTO `photos` (`id_photo`, `chemin_photo`, `id_equipement`, `id_piece`, `id_logement`) VALUES
(17, 'Paris.jpg', NULL, NULL, 16),
(18, 'Chambre.jpg', NULL, 111, 16),
(19, 'Lyon.jpg', NULL, NULL, 17),
(20, 'Chambre2.jpg', NULL, 115, 17),
(21, 'Chambre.jpg', NULL, 115, 17),
(22, 'Chambre2.jpg', NULL, 123, 19),
(23, 'Chambre2.jpg', NULL, 119, 18),
(24, 'Cuisine.jpg', NULL, 112, 16),
(25, 'Cuisine.jpg', NULL, 116, 17),
(26, 'Cuisine.jpg', NULL, 120, 18),
(27, '', NULL, 124, 19),
(28, 'Salon.jpg', NULL, 114, 16),
(29, 'Salon.jpg', NULL, 118, 17),
(30, 'Salon.jpg', NULL, 126, 19);

-- --------------------------------------------------------

--
-- Structure de la table `pieces`
--

CREATE TABLE `pieces` (
  `id_piece` int(11) NOT NULL,
  `libelle_piece` varchar(200) NOT NULL,
  `surface` double(6,2) NOT NULL,
  `id_logement` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `pieces`
--

INSERT INTO `pieces` (`id_piece`, `libelle_piece`, `surface`, `id_logement`) VALUES
(111, 'Chambre 16-1', 10.50, 16),
(112, 'Cuisine 16-2', 15.20, 16),
(113, 'Salle de bain 16-3', 5.80, 16),
(114, 'Salon 16-4', 20.30, 16),
(115, 'Chambre 17-1', 12.60, 17),
(116, 'Cuisine 17-2', 14.70, 17),
(117, 'Salle de bain 17-3', 6.90, 17),
(118, 'Salon 17-4', 18.10, 17),
(119, 'Chambre 18-1', 11.20, 18),
(120, 'Cuisine 18-2', 13.40, 18),
(121, 'Salle de bain 18-3', 7.30, 18),
(122, 'Salon 18-4', 21.80, 18),
(123, 'Chambre 19-1', 9.70, 19),
(124, 'Cuisine 19-2', 16.20, 19),
(125, 'Salle de bain 19-3', 8.40, 19),
(126, 'Salon 19-4', 19.60, 19);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `commercial`
--
ALTER TABLE `commercial`
  ADD PRIMARY KEY (`id_commercial`),
  ADD KEY `hashed_password` (`hashed_password`);

--
-- Index pour la table `equipement`
--
ALTER TABLE `equipement`
  ADD PRIMARY KEY (`id_equipement`),
  ADD KEY `Equipement_Pieces_FK` (`id_piece`);

--
-- Index pour la table `logements`
--
ALTER TABLE `logements`
  ADD PRIMARY KEY (`id_logement`),
  ADD KEY `Logements_Commercial_FK` (`id_commercial`);

--
-- Index pour la table `photos`
--
ALTER TABLE `photos`
  ADD PRIMARY KEY (`id_photo`),
  ADD KEY `Photos_Equipement_FK` (`id_equipement`),
  ADD KEY `Photos_Pieces0_FK` (`id_piece`),
  ADD KEY `Photos_Logements1_FK` (`id_logement`);

--
-- Index pour la table `pieces`
--
ALTER TABLE `pieces`
  ADD PRIMARY KEY (`id_piece`),
  ADD KEY `Pieces_Logements_FK` (`id_logement`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `commercial`
--
ALTER TABLE `commercial`
  MODIFY `id_commercial` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `equipement`
--
ALTER TABLE `equipement`
  MODIFY `id_equipement` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=209;

--
-- AUTO_INCREMENT pour la table `logements`
--
ALTER TABLE `logements`
  MODIFY `id_logement` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT pour la table `photos`
--
ALTER TABLE `photos`
  MODIFY `id_photo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT pour la table `pieces`
--
ALTER TABLE `pieces`
  MODIFY `id_piece` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=139;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `equipement`
--
ALTER TABLE `equipement`
  ADD CONSTRAINT `Equipement_Pieces_FK` FOREIGN KEY (`id_piece`) REFERENCES `pieces` (`id_piece`);

--
-- Contraintes pour la table `logements`
--
ALTER TABLE `logements`
  ADD CONSTRAINT `Logements_Commercial_FK` FOREIGN KEY (`id_commercial`) REFERENCES `commercial` (`id_commercial`);

--
-- Contraintes pour la table `photos`
--
ALTER TABLE `photos`
  ADD CONSTRAINT `Photos_Equipement_FK` FOREIGN KEY (`id_equipement`) REFERENCES `equipement` (`id_equipement`),
  ADD CONSTRAINT `Photos_Logements1_FK` FOREIGN KEY (`id_logement`) REFERENCES `logements` (`id_logement`),
  ADD CONSTRAINT `Photos_Pieces0_FK` FOREIGN KEY (`id_piece`) REFERENCES `pieces` (`id_piece`);

--
-- Contraintes pour la table `pieces`
--
ALTER TABLE `pieces`
  ADD CONSTRAINT `Pieces_Logements_FK` FOREIGN KEY (`id_logement`) REFERENCES `logements` (`id_logement`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
