/* server.js */

/*--------- Load Packages ----------*/
const express = require('express');
const bodyParser = require('body-parser');          // POST 데이터 처리 



/*--------- Define Variables ----------*/
var main = require('./routes/main');
var newPlayer = require('./routes/create_player');
var match = require('./routes/match');
var newMatch = require('./routes/create_match');
var record = require('./routes/record');

/*--------- Configure App ----------*/
var app = express();

app.set('port', process.env.PORT || 3000);
app.set('views', __dirname + '/view');
app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);

app.use('/', main);
app.use('/create_player', newPlayer);
app.use('/match', match);
app.use('/create_match', newMatch);
app.use('/record', record);

/*--------- Configure BodyParser ----------*/
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));


// /*--------- Configure Router ----------*/
// var router = require('./routes/main')(app, db);


/*--------- Run App ----------*/
app.listen(app.get('port'), function(){
    console.log('Express server listening on port' + app.get('port'));
});
