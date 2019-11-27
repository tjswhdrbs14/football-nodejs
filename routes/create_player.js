/* create_player.js */

/*--------- Load Packages ----------*/
const mysql = require('mysql');
const express = require('express');
const dbconfig = require('../dbconfig.js');
const router = express.Router();
const db = mysql.createConnection(dbconfig);
const bodyParser = require('body-parser');          // POST 데이터 처리 


/*--------- Conncet MySQL ----------*/
db.connect();

/*--------- Configure BodyParser ----------*/
router.use(bodyParser.json());
router.use(bodyParser.urlencoded({extended: true}));

/*--------- GET Method ----------*/
router.get('/', function(req, res){
    res.render('create_player',{
        title: "선수추가"
    });
});

/*--------- POST Method ----------*/
router.post('/write', function(req, res, next){
    var userId = req.body.userId;
    var userName = req.body.userName;
    
    db.query(`INSERT INTO player (id, name) VALUES(?, ?)`,
    [userId, userName]),
    function(err, result, fields){
        if(err){
            res.send('err: ' + err);
        }else{
        res.render('/');
        }
    };
});
   
/*--------- Module Routing  ----------*/
module.exports = router;