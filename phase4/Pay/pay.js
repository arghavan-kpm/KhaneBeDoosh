require('es6-promise').polyfill();
require('isomorphic-fetch');

var ORM = require('../ORM.js');
var orm = new ORM(); // CREATE TABLES
const express = require('express');
const Pay = express.Router();

Pay.get('/', function(req, res) {
    var result = 'OK';
    orm.SelectUser(req.query.user).then(T => {

        if( T.length == 0 ) { // USER is null

            result = 'User Not Found';
            res.status(401);
        }
        else {
            if (T[0].dataValues.CREDIT >= 1000 ) {
                orm.InsertPaidRel({USERNAME: req.query.user, HOUSEID: req.query.house_id});
                return parseInt(T[0].dataValues.CREDIT) - 1000;
            }

            result = "insufficient credit";
            res.status(402);
        }

    }).then( newCredit => {

        orm.UpdateUserCredit(req.query.user,newCredit); // if credit == null -> throw ex
        console.log("credit is updated");
        return newCredit;

    }).then(newCredit => {
        res.send({ result : result,newCredit : newCredit});
    }).catch(() => {
        res.send({ result : result });
    });


});

module.exports = Pay;