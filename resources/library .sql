-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : jeu. 18 août 2022 à 10:31
-- Version du serveur : 5.7.36
-- Version de PHP : 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `library`
--

-- --------------------------------------------------------
DROP DATABASE IF EXISTS library;
CREATE DATABASE library;
USE library;

--
-- Structure de la table `author`
--

DROP TABLE IF EXISTS `author`;
CREATE TABLE IF NOT EXISTS `author` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lastname` varchar(225) NOT NULL,
  `firstname` varchar(225) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `author`
--

INSERT INTO `author` (`id`, `lastname`, `firstname`) VALUES
(3, 'Mcsween', 'Pierre-Yves'),
(5, 'Niang', 'Thione'),
(9, 'Djomo', 'Wilfried'),
(16, 'Hill', 'Napoleon');

-- --------------------------------------------------------

--
-- Structure de la table `textbook`
--

DROP TABLE IF EXISTS `textbook`;
CREATE TABLE IF NOT EXISTS `textbook` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(225) NOT NULL,
  `author_id` int(11) NOT NULL,
  `isbn` int(11) NOT NULL,
  `editor` varchar(225) NOT NULL,
  `publication_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `author_id` (`author_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `textbook`
--

INSERT INTO `textbook` (`id`, `title`, `author_id`, `isbn`, `editor`, `publication_date`) VALUES
(3, 'En as-tu vraiment besoin ?', 3, 1234567890, 'paris', '2022-08-21'),
(5, 'Demain tu gouvernes le monde', 5, 1234567890, 'paris', '2022-08-02'),
(8, 'Social Entrepreneurship', 5, 1234567890, 'USA', '2022-08-18'),
(9, 'L\'effet papillon', 9, 1234567890, 'france', '2022-08-23'),
(16, 'Liberté 45', 3, 1234567890, 'canada', '2022-08-12'),
(21, 'Think and grow rich', 16, 1234567890, 'baham', '2022-08-19');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `textbook`
--
ALTER TABLE `textbook`
  ADD CONSTRAINT `textbook_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
