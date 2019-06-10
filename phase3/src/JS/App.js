import React from 'react';

import '../CSS/normalize.css';
import '../CSS/fontawesome-all.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import "../CSS/Home.css"
import Search_form from './Search_form.js'
import Footer from './Footer.js'

class Dropdown extends React.Component {

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
    render(){
      var obj = JSON.parse(this.state.data);
      return (
        <div className="dropdown">
    			<div id="header_button_holder"><button type="button" id="header_button" >ناحیه کاربری <i className="far fa-smile"></i></button></div>
    			<div id="dropdown_content" align="center">
    				<br/>
    				<h4 id="DD_firstline">بهنام همایون</h4><br/>
    				<div align="right">اعتبار  &emsp; &emsp; <span > {obj.credit} </span>تومان</div><br/><br/>
    				<a href="/Account"><button type="button" id="charge" >افزایش اعتبار</button></a>

  			 </div>
  		 </div>
      );
    }
}

class Slideshow extends React.Component{
    render(){
      return(
         <div className="slideshow">
  	 		   <div className="slide" id="element0" ></div>
  		  	 <div className="slide" id="element1"></div>
  			   <div className="slide" id="element2"></div>
  			   <div className="slide" id="element3"></div>
  			  </div>
      );
    }
}

class Logo extends React.Component {
  render(){
    return(
      <div >
        <div id="logo_holder"><img id = "logo"  src="./img/logo.png" alt="Home logo" /></div>
        <br /><br />
        <h2  id = "logo_name"> خانه به دوش </h2>
        <br />
      </div>
    );
  }
}

class Ad_card extends React.Component{
  constructor(props) {
    super(props);
  }

  render(){
    return(
      <div className="row" id="cards_all">
						<div className="col-sm-2 col-xs-1 " ></div>
						<div className="col-sm-8 col-xs-10 cards" >


								<div className="row">
									<div className="col-sm-4 col-xs-12 " align="center" >
										<div id="card_1" align="center"> <img className="box_pic_in_app" src="./img/726499.svg" align="center"/>
											<p className="box_text box_text_part1">گسترده</p><br/><p className="box_text box_text_part2">در منطقه مورد علاقه خود صاحب خانه شوید</p><br/>
										</div>
										<br/>
									</div>

									<div className="col-sm-4 col-xs-12 " align="center" >
										<div id="card_2" align="center"> <img className="box_pic_in_app" src="./img/726488.svg" align="center"/>
											<p className="box_text box_text_part1">مطمئن</p><br/><p className="box_text box_text_part2">با خیال راحت به دنبال خانه بگردید</p><br/>
										</div>
										<br/>
									</div>

									<div className="col-sm-4 col-xs-12 " align="center" >
								   		<div id="card_3" align="center"> <img className="box_pic_in_app" src="./img/726446.svg" align="center"/>
								   			<p className="box_text box_text_part1">آسان</p><br/><p className="box_text box_text_part2">به سادگی صاحب خانه شوید</p><br/>
								   		</div>

								    </div>
								</div>

						</div>
						<div className="col-sm-2 col-xs-1 " ></div>
			</div>

    );
  }
}

class Why_khane extends React.Component {
  constructor(props) {
    super(props);
  }

  render(){
    return(
      <div id="why-khane-all" className="row">
						<div className="col-sm-2 col-xs-1 " ></div>
						<div className="col-sm-8 col-xs-10 ">
							<div className="row">

								<div className="col-sm-5 col-xs-12" align="center">
									<div><img id="why-khanebedoosh_pic" src="./img/why-khanebedoosh.jpg"/></div>
								</div>
								<div className="col-sm-7 col-xs-12 " align="right" dir="rtl">
										<h1 id="why-khane_title">چرا خانه به دوش؟</h1><br/><br/>
										<ul>
											<li><i className="fas fa-check-circle why-khane"></i> &nbsp;اطلاعات کامل و صحیح از املاک قابل معامله</li><br/>
											<li><i className="fas fa-check-circle why-khane"></i> &nbsp;بدون محدودیت، 24 ساعته و در تمام ایام هفته</li><br/>
											<li><i className="fas fa-check-circle why-khane"></i> &nbsp;جستجوی هوشمند ملک، صرفه جویی در زمان</li><br/>
											<li><i className="fas fa-check-circle why-khane"></i> &nbsp;تنوع در املاک، افزایش قدرت انتخاب مشتریان</li><br/>
											<li><i className="fas fa-check-circle why-khane"></i> &nbsp;بانکی جامع از اطلاعات هزاران آگهی به روز</li><br/>
											<li><i className="fas fa-check-circle why-khane"></i> &nbsp;دستیابی به نتیجه مطلوب در کمترین زمان ممکن</li><br/>
											<li><i className="fas fa-check-circle why-khane"></i> &nbsp;همکاری با مشاوران متخصص در حوزه املاک</li><br/>
										</ul>
								</div>

							</div>
						</div>
						<div className="col-sm-2 col-xs-1 " ></div>
			</div>
    );
  }
}



class App extends React.Component {
  render() {
    return (
      <div className="container-fluid App">
        <Dropdown />
        <Slideshow />
        <Logo />
        <Search_form />
        <Ad_card />
        <Why_khane />
        <Footer />
      </div>
    );
  }
}


export default App;
