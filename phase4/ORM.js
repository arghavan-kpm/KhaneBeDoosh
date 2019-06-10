require('es6-promise').polyfill();
require('isomorphic-fetch');

function ORM(){
    const Sequelize = require('sequelize');

    this.op = Sequelize.Op;

    sequelize = new Sequelize('database', 'username', 'password', {
        host: 'localhost',
        dialect: 'sqlite',
        operatorsAliases: false,

        pool: {
            max: 5,
            min: 0,
            acquire: 30000,
            idle: 10000
        },

        // SQLite only
        storage: '/home/kosar/Downloads/apache-tomcat-9.0.4/bin/phase6.db'



    });

    this.USER = sequelize.define('USER', {
        USERNAME: {
            type: Sequelize.STRING,
            primaryKey : true,
            allowNull: false
        },
        NAME: {
            type: Sequelize.STRING,
            allowNull: false
        },
        PASSWORD: {
            type: Sequelize.STRING,
            allowNull: false
        },
        PHONE: {
            type: Sequelize.STRING,
            allowNull: false
        },
        CREDIT: {
            type: Sequelize.INTEGER,
            allowNull: false
        },
        IS_ADMIN: {
            type: Sequelize.INTEGER,
            allowNull: false
        }
    });

    this.REALE = sequelize.define('REALE', {
        NAME: {
            type: Sequelize.STRING,
            primaryKey : true,
            allowNull: false
        },
        PHONE: {
            type: Sequelize.STRING,
            allowNull: false
        }

    });


    this.HOUSE = sequelize.define('HOUSE', {
        HOUSEID: {
            type: Sequelize.STRING,
            primaryKey : true,
            allowNull: false
        },
        AREA: {
            type: Sequelize.INTEGER,
            allowNull: false
        },
        BUILDINGTYPE: {
            type: Sequelize.STRING,
            allowNull: false
        },
        ADDRESS: {
            type: Sequelize.STRING,
            allowNull: false
        },
        PHONE: {
            type: Sequelize.STRING,
            allowNull: false
        },
        IMGURL: {
            type: Sequelize.STRING,
            allowNull: false
        },
        DESCRIPTION: {
            type: Sequelize.STRING,
            allowNull: false
        },
        DEALTYPE: {
            type: Sequelize.INTEGER,
            allowNull: false
        },
        SELLP: {
            type: Sequelize.INTEGER,
            allowNull: false
        },
        RENTP: {
            type: Sequelize.INTEGER,
            allowNull: false
        },
        BASEP: {
            type: Sequelize.INTEGER,
            allowNull: false
        },
        EXPIRETIME: {
            type: Sequelize.STRING,
            allowNull: false
        }

    });

    this.PAID = sequelize.define('PAID', {
        USERNAME : {
            type: Sequelize.STRING,
            primaryKey : true,
            allowNull: false
        },
        HOUSEID: {
            type: Sequelize.STRING,
            primaryKey: true,
            allowNull: false
        }
    }); // TODO : foreign key


    this.USEROWN = sequelize.define('USEROWN', {
        HOUSEID : {
            type: Sequelize.STRING,
            primaryKey : true,
            allowNull: false
        },
        USERNAME: {
            type: Sequelize.STRING,
            primaryKey : true,
            allowNull: false
        }

    }); // TODO : foreign key


    this.REALEOWN = sequelize.define('REALEOWN', {
        HOUSEID : {
            type: Sequelize.STRING,
            primaryKey : true,
            allowNull: false
        },
        NAME: {
            type: Sequelize.STRING,
            primaryKey : true,
            allowNull: false
        }

    }); // TODO : foreign key
}


ORM.prototype.InsertUser = function( usrObj ) {
    this.USER.sync({force : false}).then(() => {
        return this.USER.create(usrObj);
    })
}


ORM.prototype.InsertRealE = function( realEObj ) {
    this.REALE.sync({force : false}).then(() => {
        return this.REALE.create(realEObj);
    })
}


ORM.prototype.InsertHouse = function( houseObj ) {
    this.HOUSE.sync({force : false}).then(() => {
        return this.HOUSE.create(houseObj);
    })
}


ORM.prototype.InsertPaidRel = function( paidObj ) {
    this.PAID.sync({force : false}).then(() => {
        return this.PAID.create(paidObj);
    })
}


ORM.prototype.InsertUserOwnRel = function( ownObj ) {
    this.USEROWN.sync({force : false}).then(() => {
        return this.USEROWN.create(ownObj);
    })
}


ORM.prototype.InsertRealEOwnRel = function( Obj ) {
    this.REALEOWN.sync({force : false}).then(() => {
        return this.REALEOWN.create(Obj);
    })
}


ORM.prototype.SelectUser = function( username ) {
    return this.USER.findAll({
        where:{
            USERNAME:username
        }
    });
}


ORM.prototype.SelectRealE  =function( name ) {
    return this.REALE.findAll({
        where:{
            NAME:name
        }
    });
}


ORM.prototype.SelectPaidRel = function( username , houseId) {

    return new Promise((resolve,reject) => {
        this.PAID.count({
            where: {
                [this.op.and]: [{USERNAME: username}, {HOUSEID: houseId}]
            }
        }).then( T => {
            if(T == 0)
                resolve(0);
            else
                resolve(1);
        });

    });
}


ORM.prototype.SelectUserOwnRel = function( username , houseId) {
    return this.USEROWN.findAll({
        where:{
            [op.and] : [ {USERNAME:username} , {HOUSEID : houseId} ]
        }
    });
}


ORM.prototype.SelectRealEOwnRel = function( name , houseId) {
    return this.REALEOWN.findAll({
        where:{
            [op.and] : [ {NAME:name} , {HOUSEID : houseId} ]
        }
    });
}


ORM.prototype.SelectHouse = function(  houseId ) {

    return new Promise( ( resolve, reject ) => {
        this.HOUSE.findAll({
            where:{
                HOUSEID: houseId
            }
        }).then( house => {
           if(house[0].dataValues.DESCRIPTION == ''){
               fetch("http://139.59.151.5:6664/khaneBeDoosh/v2/house/" + houseId  , {method : 'GET'}).then((response) => {
                   return response.json();
               }).then((json) => {
                   console.log(json);
                    this.HOUSE.update({ PHONE: json.data.phone , DESCRIPTION: json.data.description} , { where : { HOUSEID : houseId}}).then( () => {
                        json.Data = json.data;
                        delete json.data;
                        resolve(json);
                    });

               });
           }
           else{
                var h = house[0].dataValues;
               var P = null;
               if(h.DEALTYPE == 1){
                   P = { rentPrice:h.RENTP , basePrice:h.BASEP};
               }
               else{
                   P = {sellPrice:h.SELLP};
               }

                resolve({result:"OK", Data:{
                        id: h.HOUSEID,
                        area: h.AREA,
                        price : P,
                        dealType: h.DEALTYPE,
                        buildingType:h.BUILDINGTYPE,
                        description:h.DESCRIPTION,
                        phone:h.PHONE,
                        imageURL:h.IMGURL,
                        address:h.ADDRESS
                    }});
           }
        })
            .catch(() =>{
               resolve({ result : 'house id not found'});
            });

    });

}


ORM.prototype.UpdateUserCredit = function( username,newCredit) {
    return this.USER.update({ CREDIT: newCredit} , { where : { USERNAME : username}});
}


ORM.prototype.search = function(searchFormObj){
    var minArea = parseInt(searchFormObj.minArea);
    var maxPrice = parseInt(searchFormObj.maxPrice);
    var buildT = searchFormObj.buildingType;
    var dealT = searchFormObj.dealType;
    var dealFlag = -1; // empty
    var buildFlag = 0; // empty

    if(searchFormObj.minArea == "")
        minArea = 0;
    if(searchFormObj.maxPrice == "")
        maxPrice = 2000000000;
    if(searchFormObj.dealType == 'خرید')
        dealFlag = 0;
    if(searchFormObj.dealType == 'اجاره')
        dealFlag = 1;
    if(searchFormObj.buildingType != "")
        buildFlag = 1;


    if( buildFlag == 0 && dealFlag == -1){
        return this.HOUSE.findAll({
            where:{
            [this.op.and] : [

                {AREA:{ [this.op.gte] : minArea }} , { [this.op.or] : [ {SELLP:{ [this.op.lte]: maxPrice} }, {RENTP:{ [this.op.lte]: maxPrice } } ] }
                ]
            }
        });
    }

    if(buildFlag == 0 && dealFlag == 0){
        return this.HOUSE.findAll({
            where:{
                [this.op.and] : [

                    {AREA:{ [this.op.gte] : minArea }} ,  { [this.op.and] : [ {SELLP:{ [this.op.lte]: maxPrice}} , {DEALTYPE:dealFlag}  ]}  ]
            }
        });
    }

    if(buildFlag == 0 && dealFlag == 1){
        return this.HOUSE.findAll({
            where:{
                [this.op.and] : [

                    {AREA:{ [this.op.gte] : minArea }} ,  { [this.op.and] : [ {RENTP:{ [this.op.lte]: maxPrice}} , {DEALTYPE:dealFlag}  ]}  ]
            }
        });
    }

    if(buildFlag == 1 && dealFlag == -1){
        return this.HOUSE.findAll({
            where:{
                [this.op.and] : [

                    {AREA:{ [this.op.gte] : minArea }} , { [this.op.or] : [ {SELLP:{ [this.op.lte]: maxPrice} }, {RENTP:{ [this.op.lte]: maxPrice } } ] } , {BUILDINGTYPE:buildT}
                ]
            }
        });
    }

    if(buildFlag == 1 && dealFlag == 0){
        return this.HOUSE.findAll({
            where:{
                [this.op.and] : [

                    {AREA:{ [this.op.gte] : minArea }} ,  { [this.op.and] : [ {SELLP:{ [this.op.lte]: maxPrice}} , {DEALTYPE:dealFlag}  ]}  , {BUILDINGTYPE:buildT}]
            }
        });
    }

    if(buildFlag == 1 && dealFlag == 1){
        return this.HOUSE.findAll({
            where:{
                [this.op.and] : [

                    {AREA:{ [this.op.gte] : minArea }} ,  { [this.op.and] : [ {RENTP:{ [this.op.lte]: maxPrice}} , {DEALTYPE:dealFlag}  ]} , {BUILDINGTYPE:buildT}  ]
            }
        });
    }

}


ORM.prototype.FillHouses = function () {
    return new Promise((resolve , reject) => {
           this.HOUSE.destroy({
                where:{
                },
               truncate:true
            }).then(() => {
                console.log("DB is cleared");
                fetch("http://139.59.151.5:6664/khaneBeDoosh/v2/house" , {method : 'GET'}).then((response) => {
                   return response.json();
               }).then((json) => {
                   console.log("data is downloaded");
                   var list = [];
                   var data = json.data;
                   for( i=0; i<data.length; i++){
                       var obj = data[i];
                       var sellP = 0;
                       var rentP = 0;
                       var baseP = 0;
                       if(obj.dealType == 1) {
                           rentP = obj.price.rentPrice;
                           baseP = obj.price.basePrice;
                       }
                       else
                           sellP = obj.price.sellPrice;

                       list.push({HOUSEID: obj.id, AREA: obj.area, BUILDINGTYPE:obj.buildingType, ADDRESS: obj.address,
                           IMGURL: obj.imageURL, SELLP: sellP, RENTP: rentP, BASEP: baseP, DEALTYPE: obj.dealType, DESCRIPTION:"", PHONE: "", EXPIRETIME: json.expireTime.toString()});

                   }
                   this.HOUSE.bulkCreate(list).then(() => {
                       resolve();

                   });
               });

            });
    });

}


ORM.prototype.makeJSON = function(T){
    return new Promise((resolve,reject) => {
        var list = [];
        for(i = 0;i < T.length;i++){

            var P = null;
            if(T[i].dataValues.DEALTYPE == 1){
                P = { rentPrice:T[i].dataValues.RENTP , basePrice:T[i].dataValues.BASEP};
            }
            else{
                P = {sellPrice:T[i].dataValues.SELLP};
            }
            list.push({
                id:T[i].dataValues.HOUSEID,
                area:T[i].dataValues.AREA,
                price:P,
                dealType:T[i].dataValues.DEALTYPE,
                buildingType:T[i].dataValues.BUILDINGTYPE,
                imageURL:T[i].dataValues.IMGURL
            });
        }
        resolve({result:'OK',data:list});
    });

}


ORM.prototype.SelectAllHouses = function( searchFormObj ){

    return new Promise((resolve,reject) => {
        this.HOUSE.max('EXPIRETIME').then( max => {
           if( parseInt(max) < Date.now()){
               this.FillHouses().then( () => {

                   this.search(searchFormObj).then(T => {
                       resolve(this.makeJSON(T));
                   });
                });
           }
           else{
               this.search(searchFormObj).then(T => {
                   resolve(this.makeJSON(T));
               });
           }
        });
    });
}


module.exports = ORM;