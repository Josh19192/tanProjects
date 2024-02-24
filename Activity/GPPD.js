const express = require('express');
const bodyParser = require('body-parser');

const app = express();
const port = 3000;

let vaccineData = [
    { id: 1, name: 'Hepatitis B', vaccineBrand: "Unknown", dose: 1 },
    { id: 2, name: 'Diphtheria', vaccineBrand: "Unknown", dose: 3 },
    { id: 3, name: 'Pneumococcal disease', vaccineBrand: "Unknown", dose: 3 },
    { id: 4, name: 'Rotavirus', vaccineBrand: "Unknown", dose: 2 },
    { id: 5, name: 'Polio (IPV)', vaccineBrand: "Unknown", dose: 3 },
];

app.use(bodyParser.json());

// GET all vaccines
app.get('/vaccines', (req, res) => {
    res.json(vaccineData);
});

// GET a specific vaccine by ID
app.get('/vaccines/:id', (req, res) => {
    const id = parseInt(req.params.id);
    const vaccine = vaccineData.find(v => v.id === id);
    if (vaccine) {
        res.json(vaccine);
    } else {
        res.status(404).send('Vaccine not found');
    }
});

// POST a new vaccine
app.post('/vaccines', (req, res) => {
    const newVaccine = req.body;
    vaccineData.push(newVaccine);
    res.status(201).send('Vaccine added successfully');
});

// PUT/update a vaccine by ID
app.put('/vaccines/:id', (req, res) => {
    const id = parseInt(req.params.id);
    const updatedVaccine = req.body;
    const index = vaccineData.findIndex(v => v.id === id);
    if (index !== -1) {
        vaccineData[index] = { ...vaccineData[index], ...updatedVaccine };
        res.send('Vaccine updated successfully');
    } else {
        res.status(404).send('Vaccine not found');
    }
});

// DELETE a vaccine by ID
app.delete('/vaccines/:id', (req, res) => {
    const id = parseInt(req.params.id);
    vaccineData = vaccineData.filter(v => v.id !== id);
    res.send('Vaccine deleted successfully');
});

app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
