import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import Login from './Login.js';
import Search from './Search.js';
import Buttons from './Buttons.js';
import registerServiceWorker from './registerServiceWorker';

ReactDOM.render(<Buttons />, document.getElementById('navbar'));
ReactDOM.render(<Search />, document.getElementById('root'));
registerServiceWorker();
