require('es6-promise').polyfill();
require('isomorphic-fetch');

var ORM = require('../ORM.js');
var orm = new ORM(); // CREATE TABLES
const express = require('express');
const Search = express.Router();

Search.get('/', function(req, res) {

    orm.SelectAllHouses({ minArea: req.query.minArea, maxPrice: req.query.maxPrice, dealType: req.query.dealType, buildingType: req.query.buildingType }).then(dum => {
        res.send(dum);
    });
});

module.exports = Search;