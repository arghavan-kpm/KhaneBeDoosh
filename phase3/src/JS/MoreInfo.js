import React from 'react';
import '../CSS/normalize.css';
import '../CSS/fontawesome-all.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import "../CSS/MoreInfo.css"
import Navbar from './Navbar.js'
import Distance from './Distance.js'
import Footer from './Footer.js'


class Body extends React.Component{

  constructor(){
    super();
    this.state = { data : '{"result":"OK", "data" : {"area" : 0,"isPaid":0, "address":"", "expireTime":"", "phone":"", "price":{"sellPrice":0}, "imageURL":"", "description":"", "dealType":0, "buildingType":""}}' }
  }

  componentWillMount(){
    return(
        fetch("http://localhost:3000/cmoreInfo" + this.props.params , { method : 'GET'})
        .then((response) => {
          return response.json();
        } )
      .then((json) => {
        return this.setState({data : JSON.stringify(json)});
      })
    );
  }

  showHiddenPhone(){
    var credit = document.getElementById("navBarCredit").innerHTML;
    if( credit < 1000){
      document.getElementById("PayForPhone").innerHTML = 'اعتبار شما برای مشاهده شماره مالک/مشاور این ملک کافی نیست';
      document.getElementById("PayForPhone").style.color = 'black';
      document.getElementById("PayForPhone").style.backgroundColor = '#DEDE00';
    }
    else{

      fetch("http://localhost:3000/cpay" + this.props.params , { method : 'GET'})
      .then((response) => {
        return response.json();
      } )
    .then((json) => {
      document.getElementById("navBarCredit").innerHTML = json.newCredit;
    });
      document.getElementById("شماره مالک/مشاور").innerHTML = this.handlePhone({isPaid:1,phone:JSON.parse(this.state.data).data.phone});
      document.getElementById("PayForPhone").remove();

    }
  }

  handleButton(isPaid){
    if(isPaid == 0){
        return(
          <button id="PayForPhone" onClick={() => this.showHiddenPhone()}>
            مشاهده شماره مالک / مشاور
          </button>
        );
    }
    else{
      return(<div></div>);
    }
  }

  handleLabel(dealType){
    if(dealType == 0)
      return(
        <div className="buyLabel"><p>فروش</p></div>
      );
    else{
      return(
        <div className="rentLabel"><p>رهن و اجاره</p></div>
      );
    }
  }

  handleRow(label,value){
    return(
      <div >
        <span className="right">{label}</span>&emsp;&emsp;&emsp;&emsp;<span id={label} className="left">{value}</span>
        <hr/>
      </div>

    );
  }

  handlePhone(data){
    if(data.isPaid == 1){
      return(
        data.phone
      );
    }
    else{
      return( data.phone.substring(0,2) + '******' + data.phone.substring(8,11));
    }
  }

  handleTable(data){
    var phone = this.handlePhone(data);
    var rows = [];
    rows.push(  this.handleRow('شماره مالک/مشاور' , phone));
    rows.push( this.handleRow('نوع ساختمان' , data.buildingType));
    if(data.dealType == 0){
      rows.push( this.handleRow('قیمت' , data.price.sellPrice + ' تومان'));
    }
    else {
      rows.push( this.handleRow('رهن' , data.price.basePrice + ' تومان'));
      rows.push( this.handleRow('اجاره' , data.price.rentPrice + ' تومان'));
    }

    rows.push( this.handleRow( 'آدرس', data.address));
    rows.push( this.handleRow( 'متراژ', data.area + ' متر مربع'));
    if(data.description === null)
      rows.push( this.handleRow( 'توضیحات', '___'));
    else
      rows.push( this.handleRow( 'توضیحات', data.description));

    return(
      <div id="info" align="right">
        {rows}
      </div>
    );
  }

  render(){
    var obj = JSON.parse(this.state.data);
    return(
        <div className="row" id="Body">
          <div className="col-sm-1"></div>
          <div className="col-sm-6 col-xs-12" algin="center" >
            <br />
            <img id="image" src={obj.data.imageURL} />
            <br /><br />
            {this.handleButton(obj.data.isPaid)}
          </div>
          <div  className="col-sm-4 col-xs-12" >
            {this.handleLabel(obj.data.dealType)}

            {this.handleTable(obj.data)}

          </div>
          <div className="col-sm-1"></div>
      </div>
    );
  }
}

class MoreInfo extends React.Component{
  render(){
    return(
      <div className="container-fluid MoreInfo">
        <Navbar  />
        <Distance text={'مشخصات کامل ملک'} />
        <Body params={this.props.location.search}/>
        <Footer />
      </div>
    );
  }
}
 export default MoreInfo;
