/* record.js */

/*--------- Load Packages ----------*/
const mysql = require('mysql');
const express = require('express');
const dbconfig = require('../dbconfig.js');
const router = express.Router();
const db = mysql.createConnection(dbconfig);


/*--------- Conncet MySQL ----------*/
db.connect();

/*--------- 기록실 페이지 ----------*/
router.get('/', function(req, res){
    /*--------- 승률을 얻는 쿼리 ----------*/
    var sql1 = `SELECT ((SELECT COUNT(win) FROM \`match\`
                                WHERE win = 0)
                                / (SELECT COUNT(*) FROM \`match\`))
                        * 100 AS 'percent'
                    FROM \`match\` limit 1;`;
    /*--------- 평균 득점을 얻는 쿼리 ----------*/
    var sql2 = `SELECT AVG(score) AS avg_score FROM \`match\`
    ;`;
    /*--------- 총 득점을 얻는 쿼리 ----------*/
    var sql3 = `SELECT SUM(score) AS total_score FROM \`match\`;`;
    /*--------- 평균 실점을 얻는 쿼리 ----------*/
    var sql4 = `SELECT AVG(lose) AS avg_lose FROM \`match\`
    ;`;
    /*--------- 총 실점을 얻는 쿼리 ----------*/
    var sql5 = `SELECT SUM(lose) AS total_lose FROM \`match\`;`;
    /*--------- 득점 순위를 얻는 쿼리 ----------*/
    var sql6 = `SELECT DENSE_RANK() OVER w AS goal,
    id, name, matchNum, goal
    FROM player
    WINDOW w AS (ORDER BY goal DESC);`;
    /*--------- 도움 순위를 얻는 쿼리 ----------*/
    var sql7 = `SELECT DENSE_RANK() OVER w AS assist,
    id, name, matchNum, assist
    FROM player
    WINDOW w AS (ORDER BY assist DESC);`;
    /*--------- MOM 순위를 얻는 쿼리 ----------*/
    var sql8 = `SELECT DENSE_RANK() OVER w AS MOM,
                    id, name, matchNum, MOM
                FROM player
                WINDOW w AS (ORDER BY MOM DESC);`;

    /*--------- 쿼리문 실행 ----------*/
    db.query(sql1 + sql2 + sql3 + sql4 + sql5 + sql6 + sql7 + sql8, 
            function(err, rows){
                if(err){
                res.send("err: " + err);
				
            }
			console.log(rows)
        /*--------- 객체 맵핑 ----------*/
        res.render('record',{
            title: "기록실",
            percent: rows[0],
            avg_score: rows[1],
            total_score: rows[2],
            avg_lose: rows[3],
            total_lose: rows[4],
            rank_goal: rows[5],
            rank_assist: rows[6],
            rank_mom: rows[7],
			rows: rows
        });
    });
});

/*--------- Module Routing  ----------*/
module.exports = router;