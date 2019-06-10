import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router,Route } from 'react-router-dom'
import './CSS/index.css';
import Account from './JS/Account.js';
import App from './JS/App.js';
import SearchResult from './JS/SearchResult.js';
import MoreInfo from './JS/MoreInfo.js';
import AddMelk from './JS/AddMelk.js';
import registerServiceWorker from './JS/registerServiceWorker.js';


ReactDOM.render((
  <Router>
    <div>
      <Route exact path="/" component={App} />
      <Route path="/App" component={App} />
      <Route path="/AddMelk" component={AddMelk} />
      <Route path="/Account" component={Account} />
      <Route path="/SearchResult" component={SearchResult} />
      <Route path="/MoreInfo" component={MoreInfo} />
    </div>
  </Router>
  ),
  document.getElementById('root')
);
registerServiceWorker();
