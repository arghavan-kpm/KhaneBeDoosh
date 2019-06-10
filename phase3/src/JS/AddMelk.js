import React from 'react';

import '../CSS/normalize.css';
import '../CSS/fontawesome-all.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import "../CSS/AddMelk.css"
import Navbar from './Navbar.js'
import Distance from './Distance.js'
import Footer from './Footer.js'

class Recording_area extends React.Component {

  constructor(){
    super();
    this.state = { data : '{"result":"OK"}' }
  }

  componentWillMount(){
    if(this.props.params != ""){
      console.log("add melk '" + this.props.params + "'");
      return(
          fetch("http://localhost:3000/crecord" + this.props.params , { method : 'GET'})
          .then((response) => {
            return response.json();
          } )
        .then((json) => {
          return this.setState({data : JSON.stringify(json)});
        })
      );
    }
  }

  change2Rent(){
    document.getElementById("AddMelk_basePrice").placeholder = "قیمت رهن";
    document.getElementById("AddMelk_basePrice").setAttribute("name", "basePrice");
    document.getElementById("AddMelk_rentPrice").style.display = "block";
    document.getElementById("AddMelk_Toman").style.display = "block";
  }

  change2Buy(){
    document.getElementById("AddMelk_basePrice").placeholder = "قیمت ";
    document.getElementById("AddMelk_basePrice").setAttribute("name", "sellPrice");
    document.getElementById("AddMelk_rentPrice").style.display = "none";
    document.getElementById("AddMelk_Toman").style.display = "none";
  }

  handleMessage(){
    var obj = JSON.parse(this.state.data);
    if(this.props.params != ""){
      if(obj.result == "OK"){
        return("خانه با موفقیت ثبت گردید");
      }
      if(obj.result == "empty params"){
        return("لطفا همه قسمت ها را پر کنید");
      }
    }
  }

  render(){
    return(
      <div className="row" dir="rtl">
          <div className="col-sm-2 col-xs-2"></div>


          <div className="col-sm-8 col-xs-8" id="radio_buttons_AddMelk">
            <form action="/AddMelk" method="GET" >
              <div className="row">
                <label className="radio-inline radio_button_AddMelk"><input  type="radio" name="dealType" value="اجاره" checked="checked"/><span className="checkmark_AddMelk" onClick={() => this.change2Rent()}></span>&emsp; رهن و اجاره</label>&emsp;
                <label className="radio-inline radio_button_AddMelk"><input  type="radio" name="dealType" value="خرید" /><span className="checkmark_AddMelk" onClick={() => this.change2Buy()}></span> &emsp; خرید</label>

              </div>



                <div className="row" >
                  <br />

                  <div className="col-sm-6 col-xs-12"><br/><p></p>
                    <select className="classic" id="melk_type" name="buildingType">
                      <option value=""  selected="selected" hidden hidden="hidden">نوع ملک</option>
                      <option value="آپارتمان">آپارتمان</option>
                      <option value="ویلایی">ویلایی</option>
                    </select>
                  </div>

                  <div className="col-sm-6 col-xs-12  labels_AddMelk"><p className="" >متر مربع</p><input type="number" name="area" placeholder="متراژ" /></div>

                </div>

                <div className="row">
                    <div className="col-sm-6 col-xs-12  labels_AddMelk"><p className="" >&emsp;</p><input id="AddMelk_address" type="text" name="address" placeholder="آدرس" /></div>
                    <div className="col-sm-6 col-xs-12  labels_AddMelk"><p className="" > تومان</p><input id="AddMelk_basePrice" type="number" name="basePrice" placeholder="قیمت رهن" /></div>
                </div>

                <div className="row">
                    <div className="col-sm-6 col-xs-12  labels_AddMelk"><p className="" >&emsp;</p><input id="AddMelk_phone" type="text" name="phone" placeholder="شماره تماس" /></div>
                    <div className="col-sm-6 col-xs-12  labels_AddMelk"><p id="AddMelk_Toman" > تومان</p><input id="AddMelk_rentPrice" type="number" name="rentPrice" placeholder="قیمت اجاره" /></div>
                </div>

                <div className="row">


                    <div className="col-sm-12 col-xs-12">
                        <input type="hidden" name="user" value="بهنام همایون"/>
                        <p className="labels_AddMelk" >&emsp;</p><input id="AddMelk_description" className="LabelBorder" type="text" name="description" placeholder="توضیخات" />
                    </div>

                </div>
                <br/>
                <div className="row">
                    <div className="col-sm-4 col-xs-12"></div>
                    <div className="col-sm-4 col-xs-12"></div>
                    <div className="col-sm-4 col-xs-12"><button type="submit" id="record_button" >ثبت ملک</button></div>

                </div>

                <br/>

                <div className="row">

                    <div className="col-sm-12 col-xs-12"><p id="printResult">{this.handleMessage()}</p></div>

                </div>

              </form>
          </div>


          <div className="col-sm-2 col-xs-2"></div>
      </div>
    );
  }
}

class AddMelk extends React.Component{
  render(){
    return(

      <div className="AddMelk container-fluid">
        <Navbar  />
        <Distance text={'ثبت ملک جدید در خانه به دوش'} />
        <Recording_area params={this.props.location.search} />
        <Footer />
      </div>

    );
  }
}

export default AddMelk;
