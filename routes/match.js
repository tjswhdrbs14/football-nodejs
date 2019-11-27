/* match.js */

/*--------- Load Packages ----------*/
const mysql = require('mysql');
const express = require('express');
const dbconfig = require('../dbconfig.js');
const router = express.Router();
const db = mysql.createConnection(dbconfig);
const moment = require('moment');


/*--------- Conncet MySQL ----------*/
db.connect();

/*--------- 경기페이지 ----------*/
router.get('/', function(req, res){
 
    db.query(`SELECT *
            FROM \`match\``, 
        function(err, rows){
        if(err){
            res.send("err: " + err);
        }
        
        res.render('matches',{
            title: "경기 정보",
            rows: rows,
            moment: moment
        });
    });
});

/*--------- Module Routing  ----------*/
module.exports = router;