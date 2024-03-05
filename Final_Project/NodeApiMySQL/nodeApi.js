const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');

const app = express();
const port = 3000;

// MySQL connection
const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'vaccine_db'
});

// Test MySQL connection
connection.connect((err) => {
  if (err) {
    console.error('Error connecting to MySQL database:', err);
    return;
  }
  console.log('Connected to MySQL database');
});

// Body parser middleware
app.use(bodyParser.json());

// API endpoint to fetch all data from MySQL database
app.get('/vaccines', (req, res) => {
  connection.query('SELECT * FROM vaccine_tbl', (err, results) => {
    if (err) {
      console.error('Error querying database:', err);
      res.status(500).send('Internal Server Error');
      return;
    }
    res.json(results);
  });
});

// API endpoint to fetch data by ID from MySQL database
app.get('/vaccine/:id', (req, res) => {
  const id = req.params.id;
  connection.query('SELECT * FROM vaccine_tbl WHERE id = ?', id, (err, results) => {
    if (err) {
      console.error('Error querying database:', err);
      res.status(500).send('Internal Server Error');
      return;
    }
    if (results.length === 0) {
      res.status(404).send('Data not found');
      return;
    }
    res.json(results[0]);
  });
});

// API endpoint to insert data into MySQL database
app.post('/vaccine', (req, res) => {
  const { vaccine_name, vaccine_brand, dose } = req.body;
  connection.query('INSERT INTO vaccine_tbl (vaccine_name, vaccine_brand, dose) VALUES (?, ?, ?)', [vaccine_name, vaccine_brand, dose], (err, result) => {
    if (err) {
      console.error('Error inserting into database:', err);
      res.status(500).send('Internal Server Error');
      return;
    }
    res.status(201).send('Data inserted successfully');
  });
});

// API endpoint to update data in MySQL database
app.put('/vaccine/:id', (req, res) => {
  const id = req.params.id;
  const { vaccine_name, vaccine_brand, dose } = req.body;
  connection.query('UPDATE vaccine_tbl SET vaccine_name = ?, vaccine_brand = ?, dose = ? WHERE id = ?', [vaccine_name, vaccine_brand, dose, id], (err, result) => {
    if (err) {
      console.error('Error updating database:', err);
      res.status(500).send('Internal Server Error');
      return;
    }
    res.status(200).send('Data updated successfully');
  });
});

// API endpoint to delete data from MySQL database
app.delete('/vaccine/:id', (req, res) => {
  const id = req.params.id;
  connection.query('DELETE FROM vaccine_tbl WHERE id = ?', id, (err, result) => {
    if (err) {
      console.error('Error deleting from database:', err);
      res.status(500).send('Internal Server Error');
      return;
    }
    res.status(200).send('Data deleted successfully');
  });
});

// Start the server
app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
