-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 24, 2024 at 02:58 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vaccine_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `childparent_tbl`
--

CREATE TABLE `childparent_tbl` (
  `id` int(10) NOT NULL,
  `parent_id` int(10) NOT NULL,
  `child_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `child_info`
--

CREATE TABLE `child_info` (
  `id` int(10) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `middle_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `birthdate` date NOT NULL,
  `blood_type` varchar(5) NOT NULL,
  `brgy` varchar(50) NOT NULL,
  `purok` varchar(50) NOT NULL,
  `parent_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `parent_tbl`
--

CREATE TABLE `parent_tbl` (
  `id` int(10) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `contact_no` int(15) NOT NULL,
  `address` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `shot_records`
--

CREATE TABLE `shot_records` (
  `id` int(10) NOT NULL,
  `child_id` int(10) NOT NULL,
  `vaccine_id` int(10) NOT NULL,
  `dose_no` int(10) NOT NULL,
  `shot_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `vaccine_tbl`
--

CREATE TABLE `vaccine_tbl` (
  `id` int(11) NOT NULL,
  `vaccine_name` varchar(50) NOT NULL,
  `vaccine_brand` varchar(50) NOT NULL,
  `dose` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vaccine_tbl`
--

INSERT INTO `vaccine_tbl` (`id`, `vaccine_name`, `vaccine_brand`, `dose`) VALUES
(2, 'Joshua', 'Tan', 3),
(3, 'COVID-19', 'Johnson & Johnson', 1),
(4, 'Flu', 'FluMist', 1),
(5, 'Flu', 'Fluzone', 2),
(6, 'Polio', 'IPOL', 3),
(7, 'MMR', 'MMR-II', 2),
(8, 'Hepatitis B', 'Engerix-B', 3),
(9, 'Hepatitis A', 'Havrix', 2),
(10, 'Chickenpox', 'Varivax', 2),
(11, 'Moderna', 'Jacques', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `childparent_tbl`
--
ALTER TABLE `childparent_tbl`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `child_info`
--
ALTER TABLE `child_info`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `parent_tbl`
--
ALTER TABLE `parent_tbl`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `shot_records`
--
ALTER TABLE `shot_records`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vaccine_tbl`
--
ALTER TABLE `vaccine_tbl`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `childparent_tbl`
--
ALTER TABLE `childparent_tbl`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `child_info`
--
ALTER TABLE `child_info`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `parent_tbl`
--
ALTER TABLE `parent_tbl`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `shot_records`
--
ALTER TABLE `shot_records`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vaccine_tbl`
--
ALTER TABLE `vaccine_tbl`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
