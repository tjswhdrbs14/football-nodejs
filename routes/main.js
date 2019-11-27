/* main.js */

/*--------- Load Packages ----------*/
const mysql = require('mysql');
const express = require('express');
const dbconfig = require('../dbconfig.js');
const router = express.Router();
const db = mysql.createConnection(dbconfig);


/*--------- Conncet MySQL ----------*/
db.connect();

/*--------- 초기페이지===선수단페이지 ----------*/
router.get('/', function(req, res){
    db.query(`SELECT * FROM player`, function(err, rows){
        if(err){
            res.send("err: " + err);
        }
        
        res.render('players',{
            title: "선수단",
            rows: rows
        });
    });
});

/*--------- Module Routing  ----------*/
module.exports = router;