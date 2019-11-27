/* create_match.js */

/*--------- Load Packages ----------*/
const mysql = require('mysql');
const express = require('express');
const dbconfig = require('../dbconfig.js');
const router = express.Router();
const db = mysql.createConnection(dbconfig);
const bodyParser = require('body-parser');       // POST 데이터 처리 
const moment = require('moment');


/*--------- Conncet MySQL ----------*/
db.connect();

/*--------- Configure BodyParser ----------*/
router.use(bodyParser.json());
router.use(bodyParser.urlencoded({extended: true}));

/*--------- GET Method ----------*/
router.get('/', function(req, res){
    res.render('create_match',{
        title: "경기추가"
    });
});

/*--------- POST Method ----------*/
router.post('/write', function(req, res, next){
    var userName = req.body.userName;
    var userPlace = req.body.userPlace;
    var userTime = req.body.userDate + " " + req.body.userTime;
    var userWin = req.body.userWin;

    /*--------- win 데이터를 스트링으로 변환 ----------
    *     승=0, 무=1, 패=2, 미정=3 으로 저장되어있음
    */
    if(userWin == "승"){
        userWin = 0;
    }else if(userWin == "무"){
        userWin = 1;
    }else if(userWin == "패"){
        userWin = 2;
    }else{
        userWin = 3;
    }
    
    db.query(`INSERT INTO \`match\` (name, place, date, win) VALUES(?, ?, ?, ?)`,
    [userName, userPlace, userTime, userWin]),
    function(err, result, fields){
        if(err){
            res.send('err: ' + err);
        }
        
        res.send('경기가 추가되었습니다.');
        next();
    };
});
   
/*--------- Module Routing  ----------*/
module.exports = router;