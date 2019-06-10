import React from 'react';

import '../CSS/normalize.css';
import '../CSS/fontawesome-all.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import "../CSS/Account.css"
import Navbar from './Navbar.js'
import Distance from './Distance.js'
import Footer from './Footer.js'

class Adding_area extends React.Component {
  constructor(){
    super();
    this.state = { data : '{"result":"OK","user":"" , "credit" : 0}' }
  }

  componentWillMount(){
    return(
        fetch("http://localhost:3000/clogin" , { method : 'GET'})
        .then((response) => {
          return response.json();
        } )
      .then((json) => {
        return this.setState({data : JSON.stringify(json)});
      })
    );
  }
  chargeAccount(){
    fetch("http://localhost:3000/ccharge?user=" + document.getElementById("user_name").innerHTML +"&credit=" + document.getElementById("charge_amount").value , { method : 'GET'})
        .then((response) => {
          return response.json();
        } )
      .then((json) => {
        document.getElementById("credit").innerHTML = json.newCredit;
        document.getElementById("navBarCredit").innerHTML = json.newCredit;
        document.getElementById("charge_amount").value = "";
      });
  }

  render(){
    var obj = JSON.parse(this.state.data);
    return(
        <div id="adding_area" className="row">
            <div id="charge_in_account" className="col-sm-7 col-xs-12">
                <div align="center" id="charge_holder">
                  <p id="Toman" align="left">تومان</p>
                  <input align = "center" id="charge_amount" type="number" name="charge" placeholder="مبلغ مورد نظر"  />
                  <br /><br />
                  <button id = "press_button" type="button" onClick={() => this.chargeAccount() }>افزایش اعتبار</button><br />

                </div>

            </div>
    				<div className="col-sm-5 col-xs-12" id="cur_credit" >
    					<br/>
    						<div id="cur_credit_text" align="center">اعتبار کنونی &emsp; <span id="credit"> {obj.credit} </span> تومان</div>
    					<br/>

    				</div>

  			</div>


      );
  }
}

class Account extends React.Component {
  render(){
    return(
      <div className="Account container-fluid"  >
          <Navbar  />
          <Distance text={'افزایش موجودی'} />
          <Adding_area  params={this.props.location.search}/>
          <Footer />

      </div>
    );
  }
}

export default Account;
