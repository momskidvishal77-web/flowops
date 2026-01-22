const express = require("express");
const app = express();

app.use(express.json());

//Health check
app.get("/health",(req,res) => {
    console.log("GET /health called", req.body);
    res.status(201).send("Notification service is running");
});

//Logs stored
app.post("/logs", (req,res) => {
    console.long("Log received:", req.body);
    res.status(201).send("Log stored");

});

const PORT = 3000;
app.listen(PORT ,() => {
    console.log(`Notification service running on port $(PORT)`);
});