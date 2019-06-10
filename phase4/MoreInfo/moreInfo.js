require('es6-promise').polyfill();
require('isomorphic-fetch');

var ORM = require('../ORM.js');
var orm = new ORM(); // CREATE TABLES
const express = require('express');
const MoreInfo = express.Router();

MoreInfo.get('/', function(req, res) {

    orm.SelectHouse( req.query.house_id ).then(house => {
        orm.SelectPaidRel(req.query.user,req.query.house_id).then( paid => {

            house.Data.isPaid = paid;

            var response = { result : house.result , data : house.Data};
            console.log(response);
            res.send(response);

        }).catch( error => {
            console.log(error);
            res.send( { result : ' moreInfo : something went wrong' });
        });

    });
});

module.exports = MoreInfo;