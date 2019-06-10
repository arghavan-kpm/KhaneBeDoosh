import React from 'react';

import '../CSS/normalize.css';
import '../CSS/fontawesome-all.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import Navbar from './Navbar.js'
import Distance from './Distance.js'
import Search_form from './Search_form.js'
import Footer from './Footer.js'
import "../CSS/SearchResult.css"
import axios from 'axios'

class Tip extends React.Component {

  render(){
    return(
      <div className="row tip">
            <div className="col-sm-12 col-xs-12" ><div align="center">{this.props.text}</div></div>
      </div>
    );
  }

}
class Results extends React.Component {
  constructor(){
    super();
    this.state = { data : '{"result":"OK","data":[]}' }
  }

  componentWillMount(){
    return(
        fetch("http://localhost:3000/csearch" + this.props.params , { method : 'GET'})
        .then((response) => {
          return response.json();
        } )
      .then((json) => {
        return this.setState({data : JSON.stringify(json)});
      })
    );
  }



  handleLabel(label){
    if(label == 0)
      return(
        <div className="buy_label"><p>فروش</p></div>
      );
    else{
      return(
        <div className="rent_label"><p>رهن و اجاره</p></div>
      );
    }
  }

  handlePrice(dealType, price){
    if(dealType == 0){
      return(
        <div className="row">
          <div className="col-sm-6 col-xs-6"><p></p> </div>
          <div className="col-sm-6 col-xs-6 " ><span> قیمت {price.sellPrice} </span><span className="Toman">تومان</span></div>
        </div>
      );
    }
    else {
      return(
        <div className="row">
          <div className="col-sm-6 col-xs-6" align="left"><span>اجاره {price.rentPrice}  </span> <span className="Toman">تومان</span></div>
          <div className="col-sm-6 col-xs-6 " ><span>رهن {price.basePrice} </span><span className="Toman">تومان</span></div>
        </div>
      );
    }
  }

  addLeftCard(data){
    return (

      <div className="col-sm-6 col-xs-12" >
      <a href={'/MoreInfo?user=بهنام+همایون&house_id=' + data.id } >
        <div className="card left_card"  >
          <img className="box_pic" src={data.imageURL} align="center"/>
            {this.handleLabel(data.dealType)}
          <div className=" text_for_cards" dir="rtl">
            <div className="row">

              <div className="col-sm-4 col-xs-6 " ><br/><p><i className="fas fa-map-marker-alt buy_color"></i>&nbsp; گیلان </p></div>

              <div className="col-sm-4">	</div>

              <div className="col-sm-4 col-xs-6" align="right"><br/><p>{data.area} متر مربع </p> </div>
            </div>
            <hr/>
              {this.handlePrice(data.dealType, data.price)}

          </div>
        </div>

        <br/>
        </a>
        </div>

  );
  }

  addRightCard(data){
    return(

      <div className="col-sm-6 col-xs-12">
      <a href={'/MoreInfo?user=بهنام+همایون&house_id=' + data.id } >
        <div className="card right_card"  >
          <img className="box_pic" src={data.imageURL} align="center"/>
            {this.handleLabel(data.dealType)}
          <div className=" text_for_cards" dir="rtl">
            <div className="row">

              <div className="col-sm-4 col-xs-6 " ><br/><p><i className="fas fa-map-marker-alt rent_color"></i>&nbsp; گیلان </p></div>

              <div className="col-sm-4">	</div>

              <div className="col-sm-4 col-xs-6" align="right"><br/><p>{data.area} متر مربع </p> </div>
            </div>
            <hr/>

              {this.handlePrice(data.dealType, data.price)}

          </div>
        </div>


        <br/>
        </a>
      </div>

    );
  }

  handleCard( Obj ){
    var rows = [];
    for(var i=0; i< Obj.data.length; i+=2){
      if(Obj.data.length - i > 1){
        rows.push(
          <div className="row" >
            {this.addLeftCard(Obj.data[i])}
            {this.addRightCard(Obj.data[i+1])}
  				</div>
        );
      }
      else {
        rows.push(
          <div className="row" >
            <div className="col-sm-6"></div>
            {this.addLeftCard(Obj.data[i])}

  				</div>
        );
      }
    }
    return(rows);
  }

  render(){


    var obj = JSON.parse(this.state.data);

    return(
      <div className="row" id="search_result">
    		<div className="col-sm-2 col-xs-1"></div>

					<div className="col-sm-8 col-xs-12" id="search_result_rows">
            { this.handleCard(obj)}

					</div>

					<div className="col-sm-2 col-xs-1"></div>

			</div>
    );
  }
}

class SearchResult extends React.Component {
  render(){
    return(
      <div className="SearchResult container-fluid">
        <Navbar/>
        <Distance text={'نتایج جستجو'} />
        <Tip text={'برای مشاهده اطلاعات بیشتر درباره ی هر ملک روی آن کلیک کنید'} />
        <Results params={this.props.location.search}/>
        <Tip text={'جستجوی مجدد'}  />
        <Search_form />
        <Footer />

      </div>
    );
  }
}

export default SearchResult;
