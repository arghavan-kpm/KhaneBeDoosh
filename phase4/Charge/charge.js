require('es6-promise').polyfill();
require('isomorphic-fetch');

var ORM = require('../ORM.js');
var orm = new ORM(); // CREATE TABLES
const express = require('express');
const Charge = express.Router();

//orm.InsertUser({USERNAME : 'بهنام همایون' , PASSWORD:'1234',PHONE:'12345678' , CREDIT : 0 ,NAME: 'بهنام همایون' , IS_ADMIN : 0});

//orm.InsertRealE( {NAME : 'بنگاه اول', PHONE:'98765432'});


Charge.get('/', function(req, res) {

    var result = 'OK';

    fetch("http://139.59.151.5:6664/bank/pay" , {
        method : 'POST',
        headers:{
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=utf-8',
            'apiKey' : 'c32ef950-17c2-11e8-87b4-496f79ef1988'
        },
        body:JSON.stringify({
            userId : req.query.user,
            value : req.query.credit
        })
    })
        .then((response) => {
            return response.json();
        } )
        .then( (json) => {
            console.log(" bank result : " + json.result);

            if( json.result == 'OK'){ // bank is ok

               orm.SelectUser(req.query.user).then(T => {

                   if( T.length == 0 ) { // USER is null

                       result = 'User Not Found';
                       res.status(401);
                   }
                   else {
                       if (Number.isInteger(parseInt(req.query.credit)))
                           return parseInt(T[0].dataValues.CREDIT) + parseInt(req.query.credit);

                       result = "Not acceptable credit";
                       res.status(406);
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

           }
        });
});


module.exports = Charge;