import React from 'react';
import "../CSS/Navbar.css"


class Navbar extends React.Component {
  constructor(){
    super();
    this.state = { data : '{"result":"OK","user":"" , "credit":0}' }
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

  render(){
    var obj = JSON.parse(this.state.data);
    return(
      <div className="row nav_bar" dir="rtl">

          <div className="col-sm-4 col-xs-12" align="right">
            <a id="logo_link" href="/App" align="center">
               <img src="./img/logo.png" valign="center" width="60" height="60" />&emsp;<h4 id="name">خانه به دوش </h4><br /><br />
            </a>
          </div>

          <div className="col-sm-4"  align="center"></div>

          <div className="col-sm-4 col-xs-12" align="left">
            <div id="user_area" align="center">
              <div id="user_area_text" align="center" ><br /><i className="far fa-smile"></i> ناحیه کاربری </div><br />
              <div id="user_info" align="center">
                <br />
                <h4 id="user_name" align="right">{obj.user}</h4>
                <br />
                <div id="cur_credit_text_user_area" align="right">اعتبار  &emsp; &emsp; &emsp; &emsp; <span id="navBarCredit"> {obj.credit} </span> تومان</div>
                <br />
                <a href="/Account"><button id="charge_button" align="center">افزایش اعتبار</button></a>
              </div>
            </div>

          </div>

      </div>
    );
  }
}

export default Navbar;
