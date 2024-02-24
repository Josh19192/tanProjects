const express = require('express');
const bodyParser = require('body-parser');
const jwt = require('jsonwebtoken');

const app = express();
const port = 3000;

let vaccineData = [
    { id: 1, name: 'Hepatitis B', vaccineBrand: "Unknown", dose: 1 },
    { id: 2, name: 'Diphtheria', vaccineBrand: "Unknown", dose: 3 },
    { id: 3, name: 'Pneumococcal disease', vaccineBrand: "Unknown", dose: 3 },
    { id: 4, name: 'Rotavirus', vaccineBrand: "Unknown", dose: 2 },
    { id: 5, name: 'Polio (IPV)', vaccineBrand: "Unknown", dose: 3 },
];

const adminUser = 'admin';
const adminPass = 'admin123';
const secretKey = 'labyu'; // Change this to a strong secret key

app.use(bodyParser.json());

function verifyToken(req, res, next) {
    const token = req.headers['authorization'];

    if (!token) {
        return res.status(403).json({ message: 'No token provided.' });
    }
    jwt.verify(token, secretKey, (err, decoded) => {
        if (err) {
            return res.status(401).json({ message: 'Failed to authenticate token.' });
        }
        req.user = decoded;
        next();
    });
}

// Get all vaccines
app.get('/vaccine', verifyToken, (req, res) => {
    res.json(vaccineData);
});

// Add a new vaccine
app.post('/vaccine', verifyToken, (req, res) => {
    const newVaccine = {
        id: vaccineData.length + 1,
        name: req.body.name,
        vaccineBrand: req.body.vaccineBrand,
        dose: req.body.dose
    };
    vaccineData.push(newVaccine);
    res.status(201).json({ message: 'Data Added Successfully.' });
});

// Update existing vaccine by ID
app.put('/vaccine/:id', verifyToken, (req, res) => {
    const { id } = req.params;
    const updatedVaccine = req.body;
    vaccineData = vaccineData.map(vaccine => {
        if (vaccine.id === parseInt(id)) {
            return { ...vaccine, ...updatedVaccine };
        }
        return vaccine;
    });
    res.json(vaccineData.find(vaccine => vaccine.id === parseInt(id)));
});

// Delete a vaccine by ID
app.delete('/vaccine/:id', verifyToken, (req, res) => {
    const { id } = req.params;
    vaccineData = vaccineData.filter(vaccine => vaccine.id !== parseInt(id));
    res.status(204).json({ message: 'Data Deleted Successfully.' });
});

// Admin login
app.post('/admin/login', (req, res) => {
    const { username, password } = req.body;
    if (username === adminUser && password === adminPass) {
        const token = jwt.sign({ username }, secretKey, { expiresIn: '1h' }); // Generate token
        res.json({ message: 'Login Successful', token });
    } else {
        res.status(401).json({ message: 'Invalid Credentials' });
    }
});

app.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});
