var ORM = require('./ORM.js');
var orm = new ORM(); // CREATE TABLES
const express = require('express');
const Login = express.Router();

//orm.InsertUser({USERNAME : 'بهنام همایون' , PASSWORD:'1234',PHONE:'12345678' , CREDIT : 0 ,NAME: 'بهنام همایون' , IS_ADMIN : 0});

//orm.InsertRealE( {NAME : 'بنگاه اول', PHONE:'98765432'});

//orm.FillHouses();


Login.get('/', function(req, res) {
    orm.SelectUser('بهنام همایون').then(T => {
        res.send({result: 'OK', user: 'بهنام همایون', credit:T[0].dataValues.CREDIT});
    });
});

module.exports = Login;