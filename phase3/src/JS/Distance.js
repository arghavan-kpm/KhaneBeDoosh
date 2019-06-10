import React from 'react';
import "../CSS/Distance.css"

class Distance extends React.Component {
  constructor(props) {
    super(props);
  }

  render(){
    return(
      <div id="distance" className="row" >
				<div className="col-sm-7"></div>
				<div className="col-sm-5 col-xs-12" align="right" >
					<br /><br /><br /><br /> <br /><br /><br />
					<h1 id="title" align="center" valign="middle">{this.props.text} </h1>
				</div>
			</div>
    );
  }
}

export default Distance;
