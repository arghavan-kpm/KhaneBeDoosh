import React from 'react'
import '../CSS/Search_form.css'

class Search_form extends React.Component {
  constructor(props) {
    super(props);

  }

  add(){
    return("salam");
  }

  render(){

    return(
      <div className="container-fluid search_form"  >
  				<div className="row " >
  					<div className="col-sm-2" ></div>

  					<div className="col-sm-8 col-xs-10 " >
  						<div id = "search_form_id" >
  						<div className="container-fluid" dir="rtl" >


  							<form action="/SearchResult" method="GET" >
  								<div className="row" >
  									<br />

  									<div className="col-sm-4 col-xs-12"><br/><p></p>
  										<select className="classic" id="HOUSE_TYPE" name="buildingType">
  											<option value=""  selected="selected" hidden hidden="hidden">نوع ملک</option>
  											<option value="آپارتمان">آپارتمان</option>
  											<option value="ویلایی">ویلایی</option>
  										</select>
  									</div>

  									<div className="col-sm-4 col-xs-12"><p className="labels" >تومان</p><input type="text" name="maxPrice" placeholder="حداکثر قیمت" /></div>
                    <div className="col-sm-4 col-xs-12"><p className="labels" >متر مربع</p><input type="text" name="minArea" placeholder="حداقل متراژ" /></div>

  								</div>

  								<br/>
  								<div className="row">

  									<div className="col-sm-7 col-xs-12 sec_row_searchBlock " align ="right">
  										<label className="radio-inline radio_button"><input type="radio" name="dealType" value="اجاره" /><span className="checkmark"></span>&emsp; رهن و اجاره</label>&emsp;
  										<label className="radio-inline radio_button"><input type="radio" name="dealType" value="خرید" /><span className="checkmark"></span> &emsp; خرید</label>
                      <input type="radio" hidden="hidden" checked="checked" name="dealType" value="" />
  									</div>

  									<div className="col-sm-5 col-xs-12 sec_row_searchBlock">
                        <button type="submit" id="submit_button">جستجو</button>
                    </div>


  								</div>

  								<br/>

  							</form>


  						</div>
  					</div>
  					</div>

  					<div className="col-sm-2 col-xs-1" ></div>
  				</div>

  				<br/>
  				<div className="row" >
  					<div className="col-sm-2 col-xs-1 " ></div>
  					<div className="col-sm-8 col-xs-10 ">
  					<a href={'/AddMelk'}>	<div id = "record_block" >
                    صاحب خانه هستید؟ خانه ی خود را ثبت کنید
              </div>
                </a>
            </div>

  					<div className="col-sm-2 col-xs-1 " ></div>

  				</div>
        </div>
    );
  }
}

export default Search_form;
