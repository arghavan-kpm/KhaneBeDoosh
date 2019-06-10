
var express = require('express');
var app = express();
/*
const Sequelize = require('sequelize');
const sequelize = new Sequelize('database', 'username', 'password', {
    host: 'localhost',
    dialect: 'sqlite' ,
    operatorsAliases: false,

    pool {
        max: 5,
        min: 0,
        acquire: 30000,
        idle: 10000
    },
    storage : './phase6.db'
});*/


var Login = require('./login.js');
var Charge = require('./Charge/charge.js');
var Search = require('./Search/search.js');
var MoreInfo = require('./MoreInfo/moreInfo.js');
var Pay = require('./Pay/pay.js');

app.use('/clogin',Login);
app.use('/ccharge',Charge);
app.use('/csearch',Search);
app.use('/cmoreInfo',MoreInfo);
app.use('/cpay',Pay);

app.listen(8080);
