import React from 'react'
import '../CSS/Footer.css'


class Footer extends React.Component{
  constructor(props) {
    super(props);
  }

  render(){
    return(
      <div id="in_footer" className="row" >
  					<div className="col-sm-1 footer_empty_area" > </div>
            <div id="footer_insta_area" className="col-sm-1 col-xs-12" align ="center" >
              <br/>
              <a href="http://www.instagram.com"><img className="social" id="Instagram" src="./img/Insta.png" /> </a>
            </div>
            <div  id="footer_tele_area"  className="col-sm-1 col-xs-12" align ="center" >
              <br/>
              <a href="http://www.telegram.me"><img className="social" id="Telegram" src="./img/Tele.png" /> </a>
            </div>
            <div id="footer_twitter_area" className="col-sm-1 col-xs-12" align ="center" >
              <br/>
              <a href="http://www.twitter.com"><img className="social" id="twitter" src="./img/Twitter.png" /> </a>
            </div>
            <div className="col-sm-1 footer_empty_area" ></div>

            <div id="copyright" className="col-sm-7 col-xs-12" align ="center">
              <br />
              <p align="center"> کلیه حقوق مادی و معنوی این وب‌سایت متعلق به کوثر پور احمدی و پارسا نورعلی نژاد می باشد </p>
            </div>

      </div>
    );
  }
}

export default Footer;
